# Filesystem with Unit Tests — Reconstructed Assessment Problem

> **Note:** This document reconstructs the assessment from the screenshots you uploaded and the code/interfaces visible during the four levels. Where the original wording was not fully visible, the text below preserves the observed behavior and requirements rather than claiming to be a verbatim transcript.

## Problem Overview

Implement a simple **in-memory cloud storage system**.

The storage maps file names/paths to file metadata such as file size. You do **not** interact with the real filesystem.

The task is divided into several levels. A subsequent level becomes available after the current level is solved. Every new level extends the same implementation, so functionality from all previous levels must continue to work.

You are **not required to provide the most efficient possible implementation**. Any implementation that satisfies the required behavior and passes the unit tests is acceptable.

### General Notes

- File names are full string keys, e.g. `/dir1/dir2/file.txt`.
- No directory tree is required.
- The system is entirely in memory.
- It is guaranteed that operations will not create collisions between a file name and a directory name.
- Unit tests are provided for each level.

## Starter Project Structure

```text
src/
  cloud_storage.hpp
  cloud_storage_impl.hpp
  cloud_storage_impl.cpp

tests/
  level_1_tests.cpp
  level_2_tests.cpp
  level_3_tests.cpp
  level_4_tests.cpp
  sandbox_tests.cpp

utility.cpp
utility.hpp
main.sh
Makefile
run_single_test.sh
```

The implementation initially looks approximately like:

```cpp
#ifndef CLOUD_STORAGE_IMPL_HPP_
#define CLOUD_STORAGE_IMPL_HPP_

#include "cloud_storage.hpp"

class CloudStorageImpl : public CloudStorage {
  // TODO: implement interface methods here
};

#endif  // CLOUD_STORAGE_IMPL_HPP_
```

# Level 1 — Basic File Operations

The cloud storage system should support basic file manipulation.

## `AddFile`

```cpp
bool AddFile(const std::string& name, int size)
```

Adds a new file `name` to storage.

- `size` is the amount of memory required in bytes.
- If a file with the same name already exists, the operation fails.
- Returns `true` if added successfully, otherwise `false`.

## `GetFileSize`

```cpp
std::optional<int> GetFileSize(const std::string& name)
```

Returns the file size if it exists, otherwise `std::nullopt`.

## `DeleteFile`

```cpp
std::optional<int> DeleteFile(const std::string& name)
```

Deletes the file and returns its size. If the file does not exist, returns `std::nullopt`.

## Example

```cpp
AddFile("/dir1/dir2/file.txt", 10)   // true
AddFile("/dir1/dir2/file.txt", 5)    // false
GetFileSize("/dir1/dir2/file.txt")   // 10
GetFileSize("/not-existing.file")    // std::nullopt
DeleteFile("/dir1/dir2/file.txt")    // 10
GetFileSize("/dir1/dir2/file.txt")   // std::nullopt
```

# Level 2 — Find the Largest Files

Extend the system so it can return the largest files matching a prefix.

## `GetNLargest`

```cpp
std::vector<std::string> GetNLargest(
    const std::string& prefix,
    int n)
```

Find files whose names start with `prefix`.

Return at most `n` files ordered by:

1. size descending;
2. if sizes are equal, file name lexicographically ascending.

Each result is formatted as:

```text
<file_name>(<size>)
```

Example:

```text
/file1.txt -> 100
/file2.txt -> 200
/file3.txt -> 150
```

```cpp
GetNLargest("/", 2)
```

returns:

```cpp
{
    "/file2.txt(200)",
    "/file3.txt(150)"
}
```

For equal sizes:

```text
/a.txt -> 100
/b.txt -> 100
```

the order is:

```cpp
{
    "/a.txt(100)",
    "/b.txt(100)"
}
```

# Level 3 — Users, Capacity, and Merging

Extend the system to support users with storage capacities.

Files added through the original `AddFile(...)` operation are owned by the special user:

```text
admin
```

`admin` has effectively unlimited capacity.

## `AddUser`

```cpp
bool AddUser(
    const std::string& user_id,
    int capacity)
```

Creates a new user with the given capacity.

Returns:

- `true` if created;
- `false` if the user already exists.

A new user starts with:

```text
remaining = capacity
```

## `AddFileBy`

```cpp
std::optional<int> AddFileBy(
    const std::string& user_id,
    const std::string& name,
    int size)
```

Adds a file for `user_id`.

Fails with `std::nullopt` if:

- the user does not exist;
- the file name already exists globally;
- the user does not have enough remaining capacity.

On success:

- the file is added;
- ownership is recorded;
- the user's remaining capacity is reduced by `size`;
- the remaining capacity is returned.

Example:

```text
capacity = 100
AddFileBy("user1", "/a.txt", 30)
```

returns:

```text
70
```

## Delete behavior after Level 3

Deleting a normal user's file restores that file's size to the user's remaining capacity.

Admin files do not require capacity accounting.

## `MergeUser`

```cpp
std::optional<int> MergeUser(
    const std::string& user_id_1,
    const std::string& user_id_2)
```

Merges `user_id_2` into `user_id_1`.

Fails if:

- either user does not exist;
- the IDs are the same.

On success:

- all `user_id_2` files become owned by `user_id_1`;
- capacities are combined;
- remaining capacities are combined;
- `user_id_2` is removed;
- the new remaining capacity of `user_id_1` is returned.

Conceptually:

```text
user1.capacity  += user2.capacity
user1.remaining += user2.remaining
```

# Level 4 — Backup and Restore

Extend the system with user backup and restore.

A backup is a snapshot of the files currently owned by a user. Calling backup again replaces the previous snapshot.

`admin` must also support backup and restore, even though `admin` is not stored in the normal finite-capacity user table.

## `BackupUser`

```cpp
std::optional<int> BackupUser(
    const std::string& user_id)
```

Creates or replaces the backup for the user.

The snapshot stores the user's file names and file sizes.

Returns:

- the number of files in the backup;
- `std::nullopt` for a nonexistent normal user.

`admin` is valid.

Observed unit-test behavior included:

```cpp
BackupUser("admin") == 1
```

when admin owns one file, and:

```cpp
BackupUser("admin") == 0
```

when admin owns no files.

## `RestoreUser`

```cpp
std::optional<int> RestoreUser(
    const std::string& user_id)
```

Restores the user's latest backup.

Behavior:

1. Validate the user. A normal user must exist; `admin` is always valid.
2. Remove all files currently owned by the user.
3. For normal users, removing those files restores their capacity.
4. If no backup exists, return `0`.
5. Otherwise, recreate files from the backup.
6. If a backed-up file name is currently occupied globally, skip it.
7. Restored files become owned by the restoring user.
8. For normal users, restored files consume capacity again.
9. Return the number of files successfully restored.

A backup is a snapshot and does not change automatically when live files are added or deleted later.

## Merge interaction with backups

After:

```cpp
MergeUser(user_id_1, user_id_2)
```

succeeds:

- `user_id_2` is deleted;
- `user_id_2`'s backup is also removed;
- `user_id_1`'s old backup is not automatically rebuilt.

# Minimal Data Model

```cpp
std::unordered_map<std::string, int> files_;
```

Meaning:

```text
file_name -> size
```

For Level 3:

```cpp
std::unordered_map<std::string, std::string> owners_;
```

Meaning:

```text
file_name -> owner
```

and:

```cpp
struct UserInfo {
  int capacity;
  int remaining;
};

std::unordered_map<std::string, UserInfo> users_;
```

For Level 4:

```cpp
std::unordered_map<
    std::string,
    std::unordered_map<std::string, int>>
    backups_;
```

Meaning:

```text
user_id -> { file_name -> size }
```

`admin` does not need an entry in `users_`.

# Complete Reference Implementation

```cpp
#ifndef CLOUD_STORAGE_IMPL_HPP_
#define CLOUD_STORAGE_IMPL_HPP_

#include "cloud_storage.hpp"

#include <algorithm>
#include <optional>
#include <string>
#include <unordered_map>
#include <vector>

class CloudStorageImpl : public CloudStorage {
 public:
  bool AddFile(const std::string& name, int size) override {
    if (files_.count(name)) return false;
    files_[name] = size;
    owners_[name] = "admin";
    return true;
  }

  std::optional<int> GetFileSize(
      const std::string& name) override {
    auto it = files_.find(name);
    if (it == files_.end()) return std::nullopt;
    return it->second;
  }

  std::optional<int> DeleteFile(
      const std::string& name) override {
    auto it = files_.find(name);
    if (it == files_.end()) return std::nullopt;

    int size = it->second;

    auto owner_it = owners_.find(name);
    if (owner_it != owners_.end()) {
      const std::string owner = owner_it->second;

      if (owner != "admin" && users_.count(owner)) {
        users_[owner].remaining += size;
      }

      owners_.erase(owner_it);
    }

    files_.erase(it);
    return size;
  }

  std::vector<std::string> GetNLargest(
      const std::string& prefix,
      int n) override {
    std::vector<std::pair<std::string, int>> matching;

    for (const auto& [name, size] : files_) {
      if (name.rfind(prefix, 0) == 0) {
        matching.push_back({name, size});
      }
    }

    std::sort(
        matching.begin(),
        matching.end(),
        [](const auto& a, const auto& b) {
          if (a.second != b.second) return a.second > b.second;
          return a.first < b.first;
        });

    std::vector<std::string> result;

    for (int i = 0;
         i < n && i < static_cast<int>(matching.size());
         ++i) {
      result.push_back(
          matching[i].first +
          "(" +
          std::to_string(matching[i].second) +
          ")");
    }

    return result;
  }

  bool AddUser(
      const std::string& user_id,
      int capacity) override {
    if (users_.count(user_id)) return false;
    users_[user_id] = {capacity, capacity};
    return true;
  }

  std::optional<int> AddFileBy(
      const std::string& user_id,
      const std::string& name,
      int size) override {
    if (!users_.count(user_id) ||
        files_.count(name) ||
        users_[user_id].remaining < size) {
      return std::nullopt;
    }

    files_[name] = size;
    owners_[name] = user_id;
    users_[user_id].remaining -= size;

    return users_[user_id].remaining;
  }

  std::optional<int> MergeUser(
      const std::string& user_id_1,
      const std::string& user_id_2) override {
    if (user_id_1 == user_id_2 ||
        !users_.count(user_id_1) ||
        !users_.count(user_id_2)) {
      return std::nullopt;
    }

    for (auto& [file, owner] : owners_) {
      if (owner == user_id_2) {
        owner = user_id_1;
      }
    }

    users_[user_id_1].capacity += users_[user_id_2].capacity;
    users_[user_id_1].remaining += users_[user_id_2].remaining;

    users_.erase(user_id_2);
    backups_.erase(user_id_2);

    return users_[user_id_1].remaining;
  }

  std::optional<int> BackupUser(
      const std::string& user_id) override {
    if (user_id != "admin" && !users_.count(user_id)) {
      return std::nullopt;
    }

    backups_[user_id].clear();

    for (const auto& [name, owner] : owners_) {
      if (owner == user_id) {
        backups_[user_id][name] = files_[name];
      }
    }

    return static_cast<int>(backups_[user_id].size());
  }

  std::optional<int> RestoreUser(
      const std::string& user_id) override {
    if (user_id != "admin" && !users_.count(user_id)) {
      return std::nullopt;
    }

    std::vector<std::string> to_delete;

    for (const auto& [name, owner] : owners_) {
      if (owner == user_id) {
        to_delete.push_back(name);
      }
    }

    for (const auto& name : to_delete) {
      int size = files_[name];

      if (user_id != "admin") {
        users_[user_id].remaining += size;
      }

      files_.erase(name);
      owners_.erase(name);
    }

    if (!backups_.count(user_id)) {
      return 0;
    }

    int restored = 0;

    for (const auto& [name, size] : backups_[user_id]) {
      if (files_.count(name)) {
        continue;
      }

      files_[name] = size;
      owners_[name] = user_id;

      if (user_id != "admin") {
        users_[user_id].remaining -= size;
      }

      ++restored;
    }

    return restored;
  }

 private:
  struct UserInfo {
    int capacity;
    int remaining;
  };

  std::unordered_map<std::string, int> files_;
  std::unordered_map<std::string, std::string> owners_;
  std::unordered_map<std::string, UserInfo> users_;

  std::unordered_map<
      std::string,
      std::unordered_map<std::string, int>>
      backups_;
};

#endif  // CLOUD_STORAGE_IMPL_HPP_
```

# How the Four Levels Build

```text
Level 1
  Basic file CRUD

Level 2
  Prefix filtering + largest-file ranking

Level 3
  Users + quotas + ownership + user merging

Level 4
  Per-user backup + restore
```

The key challenge is preserving earlier behavior while evolving the same in-memory model.
