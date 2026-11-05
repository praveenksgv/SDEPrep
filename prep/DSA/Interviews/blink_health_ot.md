### Bulb Toggle

You are given a series of switches, initially all in the OFF state.

You are also given a 2D integer array operations, where each
operation is represented as [l, r].

For each operation:
- Toggle every switch from index l to index r (inclusive).
- If a switch is OFF, turn it ON.
- If a switch is ON, turn it OFF.

After performing all operations, return the sum of the
1-based indices of all switches that are ON.

Function Signature:
long finalState(vector<vector<int>> operations)

Example 1:
Input:
operations = [[1, 3], [2, 3]]

Initially: All switches OFF
After [1, 3]: Switches 1, 2, 3 are ON
After [2, 3]: Only switch 1 remains ON

Output: 1

Constraints:
The exact constraints from the original assessment
are not fully recoverable from the available context.