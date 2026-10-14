# Cloud Compute Credits System — Four-Level Coding Assessment

> **Reconstructed practice handout.** This document consolidates the problem visible in the uploaded assessment photographs, including its four levels, starter project structure, examples, and a reference implementation developed during our discussion. It is **not** an original export of the assessment. Wording and formatting have been reconstructed where necessary; the screenshots do not show every line of the starter repository or every test. The reference solution is a teaching implementation, not a verbatim copy of code supplied by the assessment.

## General instructions

Implement a simplified cloud compute credits system that manages prepaid credits for workspaces running compute jobs.

The task consists of **four successive levels**. Each level introduces new behavior, building on all preceding levels. Your solution should continue to pass earlier-level tests as additional features are introduced. You may use any implementation that passes the unit tests; maximum theoretical efficiency is not required.

To run a single test case, the assessment describes the command:

```bash
bash run_single_test.sh "<test_case_name>"
```

Timestamps are integer values representing **milliseconds**. They are unique, between **1 and 10^9**, and input operations are supplied in **strictly increasing timestamp order**. The sample source files use `int timestamp`. When calculating deadlines 24 hours later, use a sufficiently wide type (such as `long long`).

### Features by level

| Level | Features |
| --- | --- |
| 1 | Create workspaces, add credits, transfer credits between workspaces |
| 2 | Rank workspaces by cumulative outgoing credit transactions |
| 3 | Consume credits, schedule a 2% rebate, retrieve consumption status |
| 4 | Merge workspaces, preserving balances and transactions; query historical credit balances |

---

## Level 1 — Workspace creation and credit transfers

Initially, the system has **no workspaces**. Implement the following operations.

### `bool CreateWorkspace(int timestamp, const string& workspace_id)`

Create a workspace with the supplied identifier and initial balance **0**.

- Return `true` when the workspace is successfully created.
- Return `false` if a workspace with the same identifier already exists.

### `optional<int> TopUp(int timestamp, const string& workspace_id, int amount)`

Add `amount` credits to the specified workspace.

- Return its **new balance** after the top-up.
- Return `nullopt` if the workspace does not exist.

### `optional<int> TransferCredits(int timestamp, const string& source_workspace_id, const string& target_workspace_id, int amount)`

Move credits from the source to the target workspace.

- Return the **source workspace's remaining balance** if the transfer succeeds.
- Return `nullopt` if either workspace does not exist.
- Return `nullopt` if source and target are the same workspace.
- Return `nullopt` if the source has fewer than `amount` credits.
- On a failed transfer, do not change either balance.

### Level 1 example

| Query | Result |
| --- | --- |
| `CreateWorkspace(1, "workspace1")` | `true` |
| `CreateWorkspace(2, "workspace1")` | `false` — already exists |
| `CreateWorkspace(3, "workspace2")` | `true` |
| `TopUp(4, "non-existing", 3500)` | `nullopt` |
| `TopUp(5, "workspace1", 3500)` | `3500` |
| `TransferCredits(6, "workspace1", "workspace2", 3501)` | `nullopt` — insufficient balance |
| `TransferCredits(7, "workspace1", "workspace2", 400)` | `3100` |

---

## Level 2 — Rank workspaces by outgoing credits

The platform wants to identify workspaces that use up credits fastest. Add the following operation without breaking Level 1.

### `vector<string> TopConsumers(int timestamp, int n)`

Return up to `n` workspace identifiers ranked by **total outgoing credit transactions**.

At this stage, outgoing credits are the total amount **successfully transferred out** of each workspace. Level 3 adds successful credit consumption to this total.

Sorting rules:

1. **Higher** outgoing total comes first.
2. When outgoing totals are equal, sort by **workspace identifier in ascending alphabetical order**.
3. Include workspaces with **zero outgoing credits**.
4. If fewer than `n` workspaces exist, return all existing workspaces.
5. Return each string formatted exactly as `"workspace_id(total_outgoing)"` (no extra spaces).
6. Credit top-ups and incoming transfers do **not** increase outgoing totals.
7. The **rebate** introduced in Level 3 does **not** reduce outgoing totals.

### Level 2 example

| Query | Result |
| --- | --- |
| `CreateWorkspace(1, "workspace3")` | `true` |
| `CreateWorkspace(2, "workspace2")` | `true` |
| `CreateWorkspace(3, "workspace1")` | `true` |
| `TopUp(4, "workspace1", 2000)` | `2000` |
| `TopUp(5, "workspace2", 3000)` | `3000` |
| `TopUp(6, "workspace3", 4000)` | `4000` |
| `TopConsumers(7, 3)` | `["workspace1(0)", "workspace2(0)", "workspace3(0)"]` |
| `TransferCredits(8, "workspace3", "workspace2", 500)` | `3500` |
| `TransferCredits(9, "workspace3", "workspace1", 1000)` | `2500` |
| `TransferCredits(10, "workspace1", "workspace2", 2500)` | `500` |
| `TopConsumers(11, 3)` | `["workspace1(2500)", "workspace3(1500)", "workspace2(0)"]` |

The outgoing total for `workspace3` is `500 + 1000 = 1500`. Its incoming transactions are irrelevant to its own outgoing total.

---

## Level 3 — Consumption, reserved-instance rebates, and charge status

A workspace may now consume prepaid credits to run a compute job. Each successful consumption generates a delayed **2% rebate**.

### `optional<string> Consume(int timestamp, const string& workspace_id, int amount)`

Deduct `amount` credits from the workspace immediately.

- If the workspace does not exist, return `nullopt`.
- If it has insufficient credits, return `nullopt`; do not change its balance or create a charge.
- On success, return a globally unique charge ID: `"charge1"`, `"charge2"`, and so on, **numbered by successful consumptions across all workspaces**.
- The entire `amount` is included in that workspace's **outgoing total** used by `TopConsumers`.
- Schedule a rebate of **2% of the consumed amount, rounded down to an integer**: `amount * 2 / 100`.
- The rebate becomes payable exactly **24 hours** after the consumption, at `timestamp + 86,400,000` milliseconds.
- Once due, credit the rebate to the appropriate workspace **before processing any other operation at or after the rebate deadline**.
- A rebate is *incoming credit*, not an outgoing transaction; it does not subtract from `TopConsumers` totals.

### `optional<string> GetChargeStatus(int timestamp, const string& workspace_id, const string& charge)`

Retrieve the status of a consumption transaction.

- Return `nullopt` if `workspace_id` does not exist.
- Return `nullopt` if the specified charge does not exist or belongs to another workspace.
- Return `"IN_PROGRESS"` before its rebate has been processed.
- Return `"REBATE_RECEIVED"` after its rebate has been processed.

All operations should process any due rebates first, including failed operations and read-only queries.

### Level 3 example

Let `DAY = 86400000`.

| Query | Result / meaning |
| --- | --- |
| `CreateWorkspace(1, "workspace1")` | `true` |
| `CreateWorkspace(2, "workspace2")` | `true` |
| `TopUp(3, "workspace1", 2000)` | `2000` |
| `Consume(4, "workspace1", 1000)` | `"charge1"` |
| `Consume(100, "workspace1", 1000)` | `"charge2"` |
| `GetChargeStatus(101, "non-existing", "charge1")` | `nullopt` |
| `GetChargeStatus(102, "workspace2", "charge1")` | `nullopt` |
| `GetChargeStatus(103, "workspace1", "charge1")` | `"IN_PROGRESS"` |
| `TopConsumers(104, 2)` | `["workspace1(2000)", "workspace2(0)"]` |
| `TopUp(3 + DAY, "workspace1", 100)` | `100` — `charge1` rebate is **not due yet** |
| `GetChargeStatus(4 + DAY, "workspace1", "charge1")` | `"REBATE_RECEIVED"` |
| `TopUp(5 + DAY, "workspace1", 100)` | `220` — includes 20-credit rebate for `charge1` |
| `TopUp(99 + DAY, "workspace1", 100)` | `320` — second rebate not yet due |
| `TopUp(100 + DAY, "workspace1", 100)` | `440` — includes 20-credit rebate for `charge2` |

For equal-to-deadline operations, the rebate is applied **first**.

---

## Level 4 — Merge workspaces and query historical credit balances

Add workspace merging and historical balance queries while retaining all features from Levels 1–3.

### `bool MergeWorkspaces(int timestamp, const string& workspace_id_1, const string& workspace_id_2)`

Merge **workspace 2 into workspace 1**. Workspace 1 survives; workspace 2 is removed from the set of active workspaces.

- Return `false` when both identifiers are the same.
- Return `false` if either workspace does not exist.
- Otherwise return `true` and complete the merge.
- Add workspace 2's current balance to workspace 1's balance.
- Add workspace 2's accumulated outgoing total to workspace 1's outgoing total. The merged workspace appears as a **single entry** in `TopConsumers`.
- All pending rebates for consumption originally made by workspace 2 must still be paid **on schedule**, but credited to workspace 1 after the merge.
- Charges created under workspace 2 must remain queryable through workspace 1, using their **original charge IDs**. A charge must no longer be queryable through the removed workspace 2.
- Workspace 2 must no longer be available for ordinary operations after the merge.
- Preserve historical balances as described below.
- Apply rebates due at the merge timestamp before merging.

### `optional<int> GetCredits(int timestamp, const string& workspace_id, int time_at)`

Return the workspace balance **as of historical timestamp `time_at`**, considering only operations that had been processed at or before that time.

- Return `nullopt` when that workspace did **not yet exist** at `time_at`.
- Return `nullopt` when that workspace had **already been merged away** by `time_at`.
- For a workspace existing at `time_at`, return its balance after all operations processed at that time.
- Scheduled rebates count as of their **actual due timestamps**, even if the next API call happens later.
- Historical queries may refer to a workspace which has subsequently been merged away, provided the queried time precedes its removal.
- A surviving workspace keeps its earlier history, with the **combined balance** recorded from the merge timestamp onward. The task says that the surviving workspace inherits the merged workspace's balance history; this means historical states for the removed ID also remain accessible for its own pre-merge lifetime.

**Note on wording:** The uploaded screenshot shows the final bullet about inheriting balance history but does not clarify any additional identity-remapping rules beyond the examples. This reconstruction follows the directly demonstrated behavior: histories remain attached to the respective identifiers for times before the merge, and the surviving ID contains the merged balance afterward.

### Level 4 example A

Let `DAY = 86400000`.

| Query | Result |
| --- | --- |
| `CreateWorkspace(1, "workspace1")` | `true` |
| `CreateWorkspace(2, "workspace2")` | `true` |
| `TopUp(3, "workspace1", 2000)` | `2000` |
| `TopUp(4, "workspace2", 2000)` | `2000` |
| `Consume(5, "workspace2", 300)` | `"charge1"` |
| `TransferCredits(6, "workspace1", "workspace2", 500)` | `1500` |
| `MergeWorkspaces(7, "workspace1", "non-existing")` | `false` |
| `MergeWorkspaces(8, "workspace1", "workspace1")` | `false` |
| `MergeWorkspaces(9, "workspace1", "workspace2")` | `true` |
| `TopUp(10, "workspace1", 100)` | `3800` |
| `TopUp(11, "workspace2", 100)` | `nullopt` |
| `GetChargeStatus(12, "workspace2", "charge1")` | `nullopt` |
| `GetChargeStatus(13, "workspace1", "charge1")` | `"IN_PROGRESS"` |
| `GetCredits(14, "workspace2", 1)` | `nullopt` — not yet created |
| `GetCredits(15, "workspace2", 9)` | `nullopt` — merged at time 9 |
| `GetCredits(16, "workspace1", 11)` | `3800` |
| `TopUp(5 + DAY, "workspace1", 100)` | `3906` — includes the delayed `6`-credit rebate |

At merge time, `workspace1` has 1500 credits and `workspace2` has 2200 credits, for **3700 total**. The next 100-credit top-up brings this to **3800**. The delayed rebate is `floor(300 × 0.02) = 6`.

### Level 4 example B — historical rebate timing

| Query | Result |
| --- | --- |
| `CreateWorkspace(1, "workspace1")` | `true` |
| `TopUp(2, "workspace1", 1000)` | `1000` |
| `Consume(3, "workspace1", 300)` | `"charge1"` |
| `GetCredits(4, "workspace1", 3)` | `700` |
| `GetCredits(5 + DAY, "workspace1", 2 + DAY)` | `700` |
| `GetCredits(6 + DAY, "workspace1", 3 + DAY)` | `706` |

The scheduled 6-credit rebate becomes effective at timestamp `3 + DAY`, not at the later query timestamp.

---

## Starter repository structure (visible in the screenshots)

```text
src/
  compute_credits_system.hpp          # Provided interface; normally read-only
  compute_credits_system_impl.hpp     # Your implementation class
  compute_credits_system_impl.cpp     # Existing implementation translation unit

tests/
  level_1_tests.cpp
  level_2_tests.cpp                   # Appears after Level 2 unlocks
  level_3_tests.cpp                   # Appears after Level 3 unlocks
  level_4_tests.cpp                   # Appears after Level 4 unlocks
  sandbox_tests.cpp                   # Editable practice tests
  utility.cpp
  utility.hpp

main.sh
Makefile
run_single_test.sh
```

The screenshots show `ComputeCreditsSystemImpl : public ComputeCreditsSystem`, starting with an empty implementation class. The provided `ComputeCreditsSystem` interface initially has three virtual methods with default failure responses; further methods appear when levels unlock. Its exact full final header is not entirely visible, so the following is a **reconstructed interface matching the visible signatures**.

### Reconstructed final interface: `src/compute_credits_system.hpp`

```cpp
#ifndef COMPUTE_CREDITS_SYSTEM_HPP
#define COMPUTE_CREDITS_SYSTEM_HPP

#include <optional>
#include <string>
#include <vector>

struct ComputeCreditsSystem {
    virtual bool CreateWorkspace(int timestamp, const std::string& workspace_id) { return false; }
    virtual std::optional<int> TopUp(int timestamp, const std::string& workspace_id, int amount) { return std::nullopt; }
    virtual std::optional<int> TransferCredits(int timestamp, const std::string& source_workspace_id,
                                               const std::string& target_workspace_id, int amount) { return std::nullopt; }
    virtual std::vector<std::string> TopConsumers(int timestamp, int n) { return {}; }
    virtual std::optional<std::string> Consume(int timestamp, const std::string& workspace_id, int amount) { return std::nullopt; }
    virtual std::optional<std::string> GetChargeStatus(int timestamp, const std::string& workspace_id,
                                                       const std::string& charge) { return std::nullopt; }
    virtual bool MergeWorkspaces(int timestamp, const std::string& workspace_id_1,
                                 const std::string& workspace_id_2) { return false; }
    virtual std::optional<int> GetCredits(int timestamp, const std::string& workspace_id, int time_at) { return std::nullopt; }
    virtual ~ComputeCreditsSystem() = 0;
};
inline ComputeCreditsSystem::~ComputeCreditsSystem() = default;

#endif
```

### Original implementation skeleton (as visible in screenshot)

```cpp
#ifndef COMPUTE_CREDITS_SYSTEM_IMPL_HPP
#define COMPUTE_CREDITS_SYSTEM_IMPL_HPP

#include "compute_credits_system.hpp"

class ComputeCreditsSystemImpl : public ComputeCreditsSystem {
};

#endif
```

**Compiler compatibility:** The provided assessment uses `std::optional`, which was standardized in **C++17**. The algorithmic code below avoids newer syntax, but the original interface requires compiling in **C++17 or later** as shown by the assessment environment.

---

## Reference solution — all four levels in one file

The following is a self-contained reference for `src/compute_credits_system_impl.hpp` against the reconstructed final interface above. It retains the small maps-and-methods approach we developed, with explicit historical-balance entries and rebate processing at due times.

```cpp
#ifndef COMPUTE_CREDITS_SYSTEM_IMPL_HPP
#define COMPUTE_CREDITS_SYSTEM_IMPL_HPP

#include "compute_credits_system.hpp"
#include <algorithm>
#include <map>
#include <unordered_map>
#include <vector>
using namespace std;

class ComputeCreditsSystemImpl : public ComputeCreditsSystem {
public:
    unordered_map<string, int> balance, outgoing;
    map<string, pair<string, long long>> charges;
    map<long long, vector<pair<string, int>>> rebates;
    map<string, map<long long, optional<int>>> history;
    int chargeCount = 0;

    void SaveBalance(long long t, const string& id) {
        history[id][t] = balance[id];
    }

    void ProcessRebates(long long t) {
        auto it = rebates.begin();
        while (it != rebates.end() && it->first <= t) {
            for (auto& p : it->second) {
                balance[p.first] += p.second;
                SaveBalance(it->first, p.first);
            }
            it = rebates.erase(it);
        }
    }

    bool CreateWorkspace(int timestamp, const string& workspace_id) override {
        ProcessRebates(timestamp);
        if (balance.count(workspace_id)) return false;
        balance[workspace_id] = 0;
        outgoing[workspace_id] = 0;
        SaveBalance(timestamp, workspace_id);
        return true;
    }

    optional<int> TopUp(int timestamp, const string& workspace_id, int amount) override {
        ProcessRebates(timestamp);
        if (!balance.count(workspace_id)) return nullopt;
        balance[workspace_id] += amount;
        SaveBalance(timestamp, workspace_id);
        return balance[workspace_id];
    }

    optional<int> TransferCredits(int timestamp, const string& source_workspace_id,
                                  const string& target_workspace_id, int amount) override {
        ProcessRebates(timestamp);
        if (!balance.count(source_workspace_id) || !balance.count(target_workspace_id) ||
            source_workspace_id == target_workspace_id || balance[source_workspace_id] < amount)
            return nullopt;
        balance[source_workspace_id] -= amount;
        balance[target_workspace_id] += amount;
        outgoing[source_workspace_id] += amount;
        SaveBalance(timestamp, source_workspace_id);
        SaveBalance(timestamp, target_workspace_id);
        return balance[source_workspace_id];
    }

    vector<string> TopConsumers(int timestamp, int n) override {
        ProcessRebates(timestamp);
        vector<pair<string, int>> v;
        for (auto& p : balance) v.push_back({p.first, outgoing[p.first]});
        sort(v.begin(), v.end(), [](const pair<string, int>& a, const pair<string, int>& b) {
            if (a.second != b.second) return a.second > b.second;
            return a.first < b.first;
        });
        vector<string> ans;
        for (int i = 0; i < n && i < (int)v.size(); i++)
            ans.push_back(v[i].first + "(" + to_string(v[i].second) + ")");
        return ans;
    }

    optional<string> Consume(int timestamp, const string& workspace_id, int amount) override {
        ProcessRebates(timestamp);
        if (!balance.count(workspace_id) || balance[workspace_id] < amount) return nullopt;
        balance[workspace_id] -= amount;
        outgoing[workspace_id] += amount;
        SaveBalance(timestamp, workspace_id);
        string id = "charge" + to_string(++chargeCount);
        long long due = (long long)timestamp + 86400000;
        charges[id] = {workspace_id, due};
        rebates[due].push_back({workspace_id, amount * 2 / 100});
        return id;
    }

    optional<string> GetChargeStatus(int timestamp, const string& workspace_id,
                                     const string& charge) override {
        ProcessRebates(timestamp);
        if (!balance.count(workspace_id) || !charges.count(charge) ||
            charges[charge].first != workspace_id) return nullopt;
        return timestamp >= charges[charge].second ? "REBATE_RECEIVED" : "IN_PROGRESS";
    }

    bool MergeWorkspaces(int timestamp, const string& workspace_id_1,
                         const string& workspace_id_2) override {
        ProcessRebates(timestamp);
        if (workspace_id_1 == workspace_id_2 || !balance.count(workspace_id_1) ||
            !balance.count(workspace_id_2)) return false;
        balance[workspace_id_1] += balance[workspace_id_2];
        outgoing[workspace_id_1] += outgoing[workspace_id_2];
        for (auto& p : charges)
            if (p.second.first == workspace_id_2) p.second.first = workspace_id_1;
        for (auto& r : rebates)
            for (auto& p : r.second)
                if (p.first == workspace_id_2) p.first = workspace_id_1;
        SaveBalance(timestamp, workspace_id_1);
        history[workspace_id_2][timestamp] = nullopt;
        balance.erase(workspace_id_2);
        outgoing.erase(workspace_id_2);
        return true;
    }

    optional<int> GetCredits(int timestamp, const string& workspace_id, int time_at) override {
        ProcessRebates(timestamp);
        if (time_at > timestamp || !history.count(workspace_id)) return nullopt;
        auto& h = history[workspace_id];
        auto it = h.upper_bound(time_at);
        if (it == h.begin()) return nullopt;
        --it;
        return it->second;
    }
};

#endif
```

### Notes on the reference implementation

- `unordered_map<string, int> balance` tracks current active workspace balances.
- `outgoing` tracks total successful transfers-out and consumption amounts; it is **not** decremented by rebates.
- `charges` associates each globally unique charge with the workspace currently responsible for it and the rebate deadline.
- `rebates` is ordered by due time, ensuring scheduled credits apply before later operations.
- `history` stores historical balance changes at operation times and at actual rebate due times, plus a `nullopt` marker when a workspace is merged away.
- The code is intentionally minimal and is **not independently verified against the hidden assessment test suite**. The previous Levels 1–3 implementations were reported as passing by the user; Level 4 was not reported as passed.
- The screenshot's `GetCredits` examples demonstrate checking historical balances by the ID that existed at that earlier time. More nuanced interpretations of “inherit balance history” may require additional logic if the original hidden tests expect querying a removed workspace's historical data through the surviving ID.

---

## Design discussion and complexity

The exercise can be implemented as an in-memory service; no persistence, distributed transactions, external database, or concurrency model is requested by the visible specification.

| Operation | Typical implementation cost |
| --- | --- |
| `CreateWorkspace`, `TopUp`, `TransferCredits`, `Consume` | O(1) average for hash-map work, **plus due-rebate processing** |
| `TopConsumers` | O(W log W), where W is the number of currently active workspaces |
| `GetChargeStatus` | O(log C) to look up a charge in the ordered map, plus due-rebate processing |
| `MergeWorkspaces` | O(C + R), where C is the number of charges and R is the number of pending rebate entries |
| `GetCredits` | O(log H) for historical lookup of one workspace, plus due-rebate processing |

Since input operations arrive in strictly increasing timestamp order, a sorted structure for pending rebates lets the implementation process each scheduled rebate once.

### Common edge cases worth testing

1. Duplicate workspace creation.
2. Top-up or transfer involving missing workspaces.
3. Transfer to the same workspace.
4. Transfer or consumption exceeding available credits.
5. Sorting workspaces with equal outgoing totals, including zero totals.
6. Generating charge IDs after unsuccessful consumption attempts.
7. Rebates occurring at exactly the same timestamp as an operation.
8. A historical query immediately before and at a rebate timestamp.
9. Merging workspaces with pending rebates and existing charge histories.
10. Merging a workspace into itself or merging a non-existent workspace.
11. Historical lookup before creation, at creation, before merge, and at merge.
12. Multiple merges in sequence and `TopConsumers` rankings after merges.

---

## Preparation note

This was a timed, test-driven **low-level implementation** exercise. A practical sequence is:

1. Implement balance storage and the three Level 1 operations.
2. Add cumulative outgoing tracking and sorting for Level 2.
3. Add globally sequenced charge IDs, scheduled rebates, and status queries for Level 3.
4. Add identity-preserving merges, charge and rebate reassignment, and historical snapshots for Level 4.

The most subtle requirements are applying delayed rebates at their **scheduled timestamps**, preserving charge ownership across merges, and answering `GetCredits` according to **historical existence**, not only current existence.
