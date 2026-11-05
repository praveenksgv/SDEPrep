# GreyOrange — Software Developer / SSE Bar-Raiser Interview

**Interview date:** 17 October 2026  
**Candidate:** Utkarsh Mishra

---

## 1. Bridge and Torch

### Question
Four people need to cross a bridge at night. They share one torch, and at most two people can cross at once. Their individual crossing times are **1, 2, 7, and 10 minutes**. When two cross together, they move at the slower person's speed. The torch must be carried on each crossing or return.

**Task:** Find the minimum total time for all four to cross.  
**Extension:** Write code that finds the minimum total time for **n** people with given crossing times.


## 2. Minimum Window Substring - [resource](https://leetcode.com/problems/minimum-window-substring/) 

### Question
Given a string **S** and a set of characters **T**, find the **shortest contiguous substring** of `S` containing all characters of `T`.

The screenshot explicitly asked:
- Which data structures would you use?
- What is the time complexity?
- How would you improve the approach if `S` has **millions of characters**?

**Clarification worth asking:** Is `T` a true set (one occurrence of each character suffices), or a string/multiset (duplicate characters in `T` must be matched)? The standard Minimum Window Substring problem uses the *multiset* interpretation; the implementation below supports duplicates.

**Example:** `S = "ADOBECODEBANC"`, `T = "ABC"` → `"BANC"`.


## 3. String Anagram Validator with Edge Cases - [resource](https://leetcode.com/problems/valid-anagram/description/)

### Question
Write a function checking whether **two strings are anagrams**, while ignoring **spaces, punctuation, and case differences**, and supporting **Unicode**. The screenshot gave `é` versus `e` as an example requiring special handling.


## 4. SQL — Second, Third, and Top 3 Highest Salaries

### Question
A table is given:

```sql
Employees(emp_id, name, department, salary)
```

Write an SQL query to get the **second-highest salary** in the company. The discussion then continued with **third-highest salary** and **top 3 salaries**.

**Clarify:** Are these **distinct salary values** or individual employees sorted by salary? The examples below treat them as **distinct salaries**.

### Second-highest distinct salary — nested MAX

### Third-highest distinct salary — nested MAX

```sql
SELECT MAX(salary) AS third_highest_salary
FROM Employees
WHERE salary < (
    SELECT MAX(salary)
    FROM Employees
    WHERE salary < (SELECT MAX(salary) FROM Employees)
);
```

### Third-highest using LIMIT/OFFSET (MySQL / PostgreSQL syntax)

```sql
SELECT DISTINCT salary
FROM Employees
WHERE salary IS NOT NULL
ORDER BY salary DESC
LIMIT 1 OFFSET 2;
```

## 5. System Design — Millions of User Activity Logs per Day

### Question
A system must **store and retrieve millions of user-activity logs per day**. Would you choose **NoSQL** or a **relational database (RDBMS)**, and why?