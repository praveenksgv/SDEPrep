You are given:
* An integer array weights, where weights[i] is the weight of the i-th package.
* An integer D, the number of days within which all packages must be shipped.
You have a ship with a fixed integer capacity C (same capacity every day).
Rules
1. Packages must be shipped in the given order (you cannot reorder them).
2. Each day, you load packages onto the ship in order, adding as many as possible without exceeding capacity C.
3. If adding the next package would exceed capacity, you stop loading for the day and ship what you have loaded.
4. You can ship at most one load per day.
5. Every package must be shipped exactly once.
Task
Return the minimum ship capacity C such that all packages can be shipped within D days.
Examples
1. weights = [1,2,3,4,5,6,7,8,9,10], D = 5 → 15
2. weights = [3,2,2,4,1,4], D = 3 → 6
Constraints
* 1 <= len(weights) <= 50_000
* 1 <= weights[i] <= 500
* 1 <= D <= len(weights)