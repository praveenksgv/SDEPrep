Task Scheduler with Variable Execution Time and Cooldown

You are given a list of tasks.

Each task has:
1. A task type represented by a string.
2. An execution time representing how long the task takes to complete.

You are also given an integer cooldown `n`.

Tasks can be executed in any order, but only one task can execute at a time.

After a task of a particular type finishes execution, another task of the
same type cannot start until at least `n` units of time have passed.

Tasks of different types may execute during this cooldown period.

If no task can currently be executed because all remaining task types are
under cooldown, the processor must remain idle until some task becomes
available.

Return the minimum total time required to execute all tasks.


Example 1:

Input:
tasks = [
    ("A", 1),
    ("B", 2),
    ("B", 2)
]
n = 2

Output:
6

Explanation:

One optimal schedule is:

Time 0 - 2 : B
Time 2 - 3 : A
Time 3 - 4 : Idle
Time 4 - 6 : B

The first B finishes at time 2.

Because the cooldown is 2, another B cannot start before time 4.

A executes between time 2 and 3, followed by one unit of idle time.

Therefore, all tasks finish at time 6.


Example 2:

Input:
tasks = [
    ("A", 1),
    ("B", 2),
    ("B", 2),
    ("C", 4)
]
n = 2

Output:
9

One possible schedule:

Time 0 - 2 : B
Time 2 - 6 : C
Time 6 - 8 : B
Time 8 - 9 : A

The execution of C completely covers the cooldown between the two B tasks,
so no idle time is required.

Total time = 9.


Constraints / Assumptions:

- Task types may be arbitrary strings.
- Multiple tasks can have the same type.
- Different tasks may have different execution times.
- Execution time is positive.
- cooldown >= 0.
- Tasks may be reordered.
- Only one task executes at a time.
- Cooldown starts after a task finishes.