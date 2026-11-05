ZLURI - ROUND 1 DSA INTERVIEW QUESTIONS

==================================================
QUESTION 1: BROKEN KEYBOARD - https://codeforces.me/problemset/problem/1907/B

Similar leetcode question - https://leetcode.com/problems/clear-digits/description/
==================================================

Given a string containing uppercase and lowercase
English letters.

The characters 'b' and 'B' act as special
backspace keys.

Rules:
1. 'b' removes the rightmost lowercase letter
   before it.
2. 'B' removes the rightmost uppercase letter
   before it.
3. If no matching character exists, ignore
   the operation.
4. 'b' and 'B' are not included in the final string.

Return the resulting string after processing
all characters.

Example 1:
Input: "aaabbbBBBAAA"
Output: "AAA"

Example 2:
Input: "AabcCBe"
Output: "Aace"

Follow-up Questions:
1. Explain the brute-force approach.
2. Can you optimize the solution?
3. How can two stacks be used?
4. Explain with a dry run.
5. What is the time and space complexity?
6. What edge cases should be handled?

Brute Force:
Time Complexity: O(N^2)
Space Complexity: O(N)

Optimized Approach: Two Stacks
Time Complexity: O(N)
Space Complexity: O(N)


==================================================
QUESTION 2: FRIEND GROUPS - https://leetcode.com/problems/number-of-provinces/description/
==================================================

Given an N x N friendship matrix representing
N employees.

matrix[i][j] = 1 means employee i and
employee j are friends.

matrix[i][j] = 0 means they are not
direct friends.

Friendship is transitive.

If A is friends with B and B is friends
with C, then A, B, and C belong to the
same friend group.

Find the total number of distinct friend groups.

Example 1:
Input:
[
  [1, 1, 0],
  [1, 1, 0],
  [0, 0, 1]
]

Output: 2

Explanation:
Group 1: {0, 1}
Group 2: {2}

Example 2:
Input:
[
  [1, 1, 0],
  [1, 1, 1],
  [0, 1, 1]
]

Output: 1

Explanation:
All employees are directly or indirectly
connected and belong to the same group.

Follow-up Questions:
1. Explain the DFS approach.
2. How does the visited array work?
3. Explain each row of the matrix.
4. Perform a dry run with visited[].
5. What is the time and space complexity?
6. Can we optimize the solution further?
7. Can we solve it using BFS or Union-Find?

Approach: DFS / BFS
Time Complexity: O(N^2)
Space Complexity: O(N)

Alternative Approach: Union-Find (DSU)
Time Complexity: O(N^2)
Space Complexity: O(N)

==================================================
END - ZLURI ROUND 1 DSA QUESTIONS
==================================================