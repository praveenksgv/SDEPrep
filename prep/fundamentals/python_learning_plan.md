## Time Allocation

| Area | Priority | Time |
|---|---:|---:|
| Objects, references, mutability, copying, function arguments | Must know | 1.5 h |
| Iterables, iterators, generators, comprehensions | Must know | 1 h |
| Closures, decorators, function behavior | Must know | 1.5 h |
| Python OOP + important data model concepts | Must know | 1.25 h |
| Context managers + exceptions/resource handling | High | 0.75 h |
| Threading, multiprocessing, GIL, race conditions | Must know | 1.5 h |
| `asyncio`, event loop, coroutines, Tasks | Must know | 1.5 h |
| Pythonic collections / standard-library tools | High | 0.75 h |
| Output-prediction + Python-specific coding practice | Must do | 1.5–2 h |

Total: roughly **11–12 hours**.

Do not allocate dedicated study time to:
- testing/fixtures
- descriptors
- metaclasses
- import internals
- CPython bytecode/C API
- deep memory-management internals

For memory management, one short explanation is enough: **CPython primarily uses reference counting plus cyclic garbage collection**.

---

# 1. Objects, References, Mutability and Copying — 1.5 Hours

This is one of the highest-return Python interview topics.

Know very well:

```text
mutable vs immutable
assignment does NOT copy objects
identity vs equality: is vs ==
mutation vs rebinding
function argument behavior
mutable default arguments
shallow copy vs deep copy
nested mutable objects
hashability
why lists aren't dict keys
tuple containing mutable objects
```

You should confidently explain:

```python
def change(xs):
    xs.append(4)
    xs = [100]

a = [1, 2, 3]
change(a)

print(a)
```

Output:

```text
[1, 2, 3, 4]
```

Also understand:

```python
def add(x, values=[]):
    values.append(x)
    return values

print(add(1))
print(add(2))
```

And:

```python
a = [[1, 2], [3, 4]]
b = a.copy()

b[0].append(99)

print(a)
```

And:

```python
a = [[0] * 3] * 3
a[0][0] = 1

print(a)
```

These are excellent interview-style questions because they quickly reveal whether someone really understands Python references and mutation.

---

# 2. Iterables, Iterators, Generators and Comprehensions — 1 Hour

Know the relationship:

```text
Iterable
   ↓ iter()
Iterator
   ↓ next()
Values until StopIteration
```

Be able to explain:

```text
Iterable vs Iterator
Iterator protocol
__iter__
__next__
StopIteration
generator
yield
generator expression
list comprehension vs generator expression
lazy vs eager evaluation
```

Be able to write a custom iterator:

```python
class Countdown:
    def __init__(self, n):
        self.n = n

    def __iter__(self):
        return self

    def __next__(self):
        if self.n <= 0:
            raise StopIteration

        value = self.n
        self.n -= 1
        return value
```

Then write the same behavior as a generator:

```python
def countdown(n):
    while n > 0:
        yield n
        n -= 1
```

Be ready to explain why the generator version is usually simpler.

Also practise output questions involving `yield` and `next()`.

---

# 3. Closures and Decorators — 1.5 Hours

This is a high-priority area because interviewers often ask both conceptual questions and decorator coding exercises.

First understand closures:

```python
funcs = []

for i in range(3):
    funcs.append(lambda: i)

print([f() for f in funcs])
```

Understand why the result is:

```text
[2, 2, 2]
```

And why this fixes it:

```python
lambda i=i: i
```

Know:

```text
LEGB scope
closure
late binding
nonlocal
first-class functions
functions returning functions
```

Then decorators.

You should immediately understand that:

```python
@timer
def foo():
    pass
```

is roughly equivalent to:

```python
foo = timer(foo)
```

## Write This From Memory

```python
from functools import wraps
from time import perf_counter

def timer(func):
    @wraps(func)
    def wrapper(*args, **kwargs):
        start = perf_counter()

        try:
            return func(*args, **kwargs)
        finally:
            print(perf_counter() - start)

    return wrapper
```

Then understand parameterized decorators:

```python
@retry(3)
def operation():
    ...
```

Conceptually:

```text
retry(3)
    ↓
decorator
    ↓
wrapper
```

Understand why parameterized decorators typically involve three function layers.

Also know why:

```python
functools.wraps
```

is used.

---

# 4. Python OOP / Data Model — 1.25 Hours

Do not study OOP generically.

Focus on Python-specific OOP behavior.

Know well:

```text
instance method
classmethod
staticmethod

__init__
__new__

__repr__
__str__

__eq__
__hash__

__iter__
__next__

__enter__
__exit__

__call__

inheritance
MRO
super()
```

Prepare these questions especially:

- `staticmethod` vs `classmethod`?
- `__new__` vs `__init__`?
- What does `super()` actually do?

Do not answer that `super()` simply "calls the parent".

A better mental model is:

```text
continue method lookup according to the MRO
```

Understand this example:

```python
class A:
    def hello(self):
        print("A")

class B(A):
    def hello(self):
        print("B")
        super().hello()

class C(A):
    def hello(self):
        print("C")
        super().hello()

class D(B, C):
    pass

D().hello()
```

And:

```python
D.__mro__
```

Also understand the relationship between:

```python
__eq__
__hash__
```

and dictionary/set keys.

Do not spend time learning metaclasses.

---

# 5. Context Managers + Exceptions — 45 Minutes

This is a small topic with excellent interview value.

Know:

```python
with open(...) as f:
```

and what happens through:

```text
__enter__()
__exit__()
```

Be able to write:

```python
class Timer:
    def __enter__(self):
        ...
        return self

    def __exit__(self, exc_type, exc_value, traceback):
        ...
```

Also know the generator-based form:

```python
from contextlib import contextmanager

@contextmanager
def timer():
    try:
        yield
    finally:
        ...
```

For exceptions revise:

```text
try
except
else
finally
raise
```

Be ready for questions like:

- When does `else` execute?
- Does `finally` execute if an exception occurs?
- What happens if `__exit__()` returns `True`?
- Why shouldn't you catch `Exception` everywhere?

Do not go much deeper than this.

---

# 6. Threading, Multiprocessing and the GIL — 1.5 Hours

For a senior/backend Python interview, this is mandatory.

Use this mental model:

| Tool | Think | Typical use |
|---|---|---|
| `threading` | Multiple OS threads, shared memory | I/O-bound work |
| `multiprocessing` | Multiple processes | CPU-bound work |
| `asyncio` | Cooperative concurrency | Large amounts of I/O |

Know:

```text
What is the GIL?
Why does it exist?
What does it prevent?
Does it mean Python is single-threaded?
Why can threads still improve I/O workloads?
Why don't threads normally improve pure-Python CPU-heavy work?
Why can multiprocessing help?
Do threads share memory?
Do processes share memory?
What is a race condition?
Does the GIL prevent race conditions?
What is a lock?
```

Do **not** answer:

> Python doesn't support multithreading.

That is incorrect.

A better answer is:

> CPython supports threads, but in a normal GIL-enabled runtime only one thread executes Python bytecode at a time. Threads are still useful for I/O-bound concurrency. CPU-heavy pure-Python work generally benefits more from multiprocessing.

You may mention free-threaded Python if needed, but do not spend serious prep time on it.

---

# 7. `asyncio` — 1.5 Hours

This should also be one of your stronger areas.

Know this relationship:

```text
async def function
       ↓ called
coroutine object
       ↓ scheduled
Task
       ↓ executed by
event loop
```

Know:

```text
async
await
coroutine
awaitable
event loop
Task
Future
create_task()
gather()
TaskGroup
cancellation
asyncio.to_thread()
```

Important interview questions:

### What happens when you call an `async def` function?

It returns a coroutine object. It does not simply run to completion synchronously.

### What does `await` do?

Be able to explain this clearly.

### Coroutine vs Task?

Know the difference.

### What is an event loop?

Explain it at a practical level.

### What happens if you call blocking synchronous code inside an async function?

It can block the event loop.

### If you have 100 HTTP requests, would you use threading, multiprocessing or asyncio?

Be ready to reason about this.

### If those 100 tasks instead perform CPU-intensive pure-Python calculations, does your answer change?

Yes. Be ready to explain why.

Do not study low-level event-loop internals.

---

# 8. Pythonic Collections + Standard-Library Fluency — 45 Minutes

You do not need the entire standard library.

Be comfortable with:

```python
Counter
defaultdict
deque

heapq
bisect

enumerate
zip

sorted(key=...)
min(key=...)
max(key=...)

any
all

itertools

functools
```

Especially:

```python
collections.Counter
collections.defaultdict
collections.deque
```

During coding interviews, know when these make your solution cleaner.

For example, avoid implementing a queue using:

```python
list.pop(0)
```

when:

```python
deque.popleft()
```

is the correct tool.

---

# 9. Final 1.5–2 Hours: Python-Specific Interview Practice

Do not spend all 12 hours reading.

Spend the final section actually writing and reasoning through Python.

## Coding Drills

Write these without copying:

```text
1. Logging/timing decorator
2. Parameterized retry decorator
3. Generator
4. Custom iterator
5. Context manager
6. One ThreadPoolExecutor example
7. One simple asyncio example
```

These should be short exercises. Most should take 5–15 minutes.

Then practise output prediction.

Be able to reason through examples involving:

```text
mutable default arguments
list aliases
shallow copying
[[0] * 3] * 3
closures
+= on lists vs tuples
generators
MRO/super()
async functions
```

Example:

```python
x = [1, 2]
y = x

y += [3]

print(x)
print(x is y)
```

Versus:

```python
x = (1, 2)
y = x

y += (3,)

print(x)
print(y)
print(x is y)
```

These are exactly the kinds of snippets that test real Python understanding.

---

# Final Questions to Rehearse

Use these for final revision.

1. `is` vs `==`?
2. Mutable vs immutable?
3. Does assignment copy an object?
4. Is Python pass-by-value or pass-by-reference?
5. Mutation vs rebinding?
6. Why are mutable default arguments dangerous?
7. Shallow vs deep copy?
8. What makes an object hashable?
9. Iterable vs iterator?
10. How does the iterator protocol work?
11. What does `yield` do?
12. Generator vs list/list comprehension?
13. What is a closure?
14. What is late binding?
15. What does `nonlocal` do?
16. What is a decorator?
17. How do parameterized decorators work?
18. Why use `functools.wraps`?
19. What is a context manager?
20. `classmethod` vs `staticmethod`?
21. `__new__` vs `__init__`?
22. What does `super()` actually do?
23. What is the GIL?
24. Threading vs multiprocessing?
25. Does the GIL prevent race conditions?
26. Coroutine vs Task?
27. What is an event loop?
28. What happens when blocking code runs inside async code?
29. `try/except/else/finally`?
30. Which Python collections/tools would you reach for during coding?

If you can answer these confidently and code the exercises above, stop Python preparation.

---

# Stopping Point

Your complete Python interview syllabus is:

```text
Python object/reference semantics
        ↓
iterators + generators
        ↓
closures + decorators
        ↓
Python-specific OOP/data model
        ↓
context managers + exceptions
        ↓
threading + multiprocessing + GIL
        ↓
asyncio
        ↓
Pythonic standard-library usage
        ↓
output questions + small implementations
```

Once you can confidently explain these topics and write the small exercises from memory, your Python preparation is sufficient for a 10–12 hour focused interview-prep plan.


---

# Actual interview experiences

This section collects **candidate-reported Python interview questions and experiences** from sources such as LeetCode Discuss, Glassdoor, and GeeksforGeeks.

Treat these as evidence of what companies have asked in real interviews, not as a guarantee that the same company will repeat the same questions. The useful signal is the **recurring topic pattern**.

## 1. Darktrace — Software Developer (Python), 2026

A candidate reported being asked to **implement a decorator** for a simple use case. The interviewer then extended the exercise to cover:

- functions with an arbitrary number of arguments
- different versions/variations of the decorator
- practical decorator behavior rather than only defining what a decorator is

### What to prepare from this

You should be able to write something like this without looking it up:

```python
from functools import wraps

def log_call(func):
    @wraps(func)
    def wrapper(*args, **kwargs):
        print(f"calling {func.__name__}")
        return func(*args, **kwargs)

    return wrapper
```

And then handle a parameterized decorator:

```python
def retry(times):
    def decorator(func):
        @wraps(func)
        def wrapper(*args, **kwargs):
            ...
        return wrapper

    return decorator
```

**Source:** [Glassdoor — Python Developer Interview Questions](https://www.glassdoor.com/Interview/python-developer-interview-questions-SRCH_KO0%2C16_SDRD.htm)

---

## 2. LG Soft India — Python Developer, 2026

A candidate reported being questioned on:

- generators
- decorators
- GIL
- Python memory management
- threads
- async programming
- their projects in detail

This is particularly relevant for an experienced backend Python candidate because it shows that interviewers may move directly from basic Python language features into **runtime/concurrency behavior**.

### Questions worth rehearsing

```text
What is a generator?
How does yield differ from return?
What is a decorator?
What is the GIL?
Does the GIL make Python single-threaded?
When are threads useful in Python?
What changes when the work is CPU-bound?
How does async differ from threading?
```

**Source:** [Glassdoor — Python Developer Interview Questions](https://www.glassdoor.com/Interview/python-developer-interview-questions-SRCH_KO0%2C16_SDRD.htm)

---

## 3. EPAM — Python Interview Experience, 2026

A candidate shared a detailed Python interview in which they were asked about:

- decorators
- the execution/order of **multiple decorators**
- exception handling
- ordering specific and general `except` clauses
- raising/chaining exceptions
- multiple inheritance
- what happens when parent classes expose the same method
- context managers

One of the interview questions used two stacked decorators and asked the candidate to reason about execution order.

### You should be able to reason through this

```python
@decorator1
@decorator2
def foo():
    ...
```

It is approximately:

```python
foo = decorator1(decorator2(foo))
```

The same interview also tested inheritance behavior similar to:

```python
class A:
    def hello(self):
        print("A")

class B:
    def hello(self):
        print("B")

class C(A, B):
    pass

C().hello()
```

You should immediately connect this to the **method resolution order (MRO)**.

**Source:** [LeetCode Discuss — EPAM Interview Experience](https://leetcode.com/discuss/post/7303754/)

---

## 4. EPAM India — Senior Software Engineer, January 2026

A candidate interviewing for a **Senior Software Engineer — Python** role reported questions including:

- iterators
- Python namespaces
- running synchronous code from asynchronous code
- blocking vs non-blocking I/O
- parameterized decorators
- context managers
- the Python event loop

This is one of the most useful reports for a senior-level Python preparation plan because the questions overlap heavily with the high-priority topics in this document.

### Questions to rehearse

```text
What makes an object iterable?
What makes an object an iterator?
What is a namespace?
What is blocking I/O?
What happens if blocking code is executed inside an async coroutine?
How could you run blocking work without blocking the event loop?
How does a parameterized decorator work?
What is a context manager?
What is an event loop?
```

For modern Python, know the role of tools such as:

```python
asyncio.to_thread(...)
asyncio.create_task(...)
asyncio.gather(...)
```

**Source:** [LeetCode Discuss — EPAM India, Senior Software Engineer Python Web Fullstack](https://leetcode.com/discuss/post/7509167/)

---

## 5. Aspire Systems — Python Technical Interview, 2026

A candidate reported Python questions on:

- decorators
- generators vs normal functions
- built-in decorators
- `@staticmethod` vs `@classmethod`
- Python logging
- exception handling

The coding exercise was especially useful: the candidate was asked to design a **logging decorator** that could work with different function signatures and capture details such as:

- function arguments
- execution time
- return values
- raised exceptions

### This is a very good practice exercise

Try implementing:

```python
@log_execution
def process(a, b, flag=False):
    ...
```

Your decorator should:

1. accept arbitrary `*args` and `**kwargs`
2. preserve the wrapped function's metadata
3. calculate execution time
4. return the original result
5. log and re-raise exceptions rather than silently swallowing them

**Source:** [LeetCode Discuss — Aspire Systems Python Full Stack Developer Interview](https://leetcode.com/discuss/post/7296963/)

---

## 6. Fynd — Python Developer I/II

A candidate reported two interview rounds containing Python-specific questions such as:

- GIL
- memory management
- threading
- whether Python is single-threaded or multithreaded
- decorators
- generators
- multithreading vs multiprocessing
- how Python threading works in the presence of the GIL

### Especially important questions

```text
Is Python single-threaded?
What exactly does the GIL restrict?
Can Python have multiple OS threads?
Why can threading help I/O-bound applications?
Why is multiprocessing often better for CPU-bound pure-Python code?
Do threads share memory?
Do processes share memory?
```

Do not answer these using simplistic statements such as:

```text
"Python cannot do multithreading."
```

That answer is incorrect.

**Source:** [LeetCode Discuss — Fynd Python Developer I/II](https://leetcode.com/discuss/post/7183172/)

---

## 7. XTX Markets — Python Developer

A reported Python Developer interview included a concentrated set of core-Python questions:

- Python memory management
- GIL and its effect on multithreading
- when to use `asyncio` vs multiprocessing
- generators
- iterators
- decorators
- context managers
- garbage collection
- `copy` vs `deepcopy`
- mutable vs immutable objects

This is almost a compact version of the highest-value sections of this preparation plan.

### Questions to use as a self-test

Try answering all of these aloud in under 10 minutes:

```text
How does Python manage objects in memory?
What is the GIL?
How does the GIL affect threads?
asyncio vs multiprocessing — when would you choose each?
Iterable vs iterator?
Generator vs normal function?
What does a decorator do?
What protocol powers a context manager?
Shallow copy vs deep copy?
Mutable vs immutable?
```

**Source:** [Glassdoor — XTX Markets Python Developer Interview Question](https://www.glassdoor.com/Interview/Explain-how-memory-management-works-in-Python-What-is-the-GIL-and-how-does-it-affect-multithreading-When-should-you-use-QTN_9048630.htm)

---

## 8. BOLD — Senior Backend Python Developer

A candidate interviewing for a **Senior Backend Python Developer** role reported being asked directly about:

- decorators
- generators
- context managers

This is a good reminder that topics which may appear "language-theoretical" are still used as direct senior-backend interview questions.

**Source:** [Glassdoor — BOLD Senior Backend Python Developer Interview Question](https://www.glassdoor.com/Interview/Do-you-know-about-decorators-generators-and-context-managers-in-python-QTN_7777694.htm)

---

## 9. TCS — Senior Software Engineer

A reported Senior Software Engineer interview included:

- multithreading
- multiprocessing
- asynchronous programming
- generators
- closures
- context managers
- GIL
- Python OOP

For a short preparation window, this is strong evidence that **closures, generators, context managers, concurrency, and GIL** are worth prioritizing over obscure Python internals.

**Source:** [Glassdoor — TCS Senior Software Engineer Interview Question](https://www.glassdoor.com/Interview/Python-basics-multi-threading-multi-processing-asynchronous-programming-pip-generators-closures-context-managers-f-QTN_8449997.htm)

---

## 10. Annalect — Senior Python Developer / Software Engineer

A reported senior Python interview included direct questions on:

- what the GIL is
- why the GIL exists
- Python memory management
- class methods
- abstract methods

### Questions to prepare

```text
What is the GIL?
Why does CPython have a GIL?
What is a classmethod?
How is a classmethod different from a staticmethod?
How does Python manage object memory at a high level?
```

**Source:** [Glassdoor — Annalect Senior Python Developer/Software Engineer Interview](https://www.glassdoor.com/Interview/What-is-GIL-How-is-memory-managed-in-python-Stack-or-Heap-Why-GIL-What-is-Class-method-What-is-abstract-method-QTN_7949568.htm)

---

## 11. Dell Technologies — Python Developer

A reported Dell Python interview included questions covering:

- decorators
- list comprehensions
- generator expressions
- lambda functions
- GIL
- `__init__`
- shallow copy vs deep copy
- exception handling with `try`, `except`, `finally`, and `raise`

### Useful takeaway

Notice how these questions are not advanced framework questions. They test whether someone who says **Python** on their resume understands the language itself.

**Source:** [Glassdoor — Dell Technologies Python Developer Interview Question](https://www.glassdoor.com/Interview/What-is-Python-and-why-is-it-popular-in-data-processing-and-analysis-Explain-the-differences-between-Python-2-and-Python-QTN_6681000.htm)

---

## 12. Systango — Senior Python Developer

A candidate described two technical rounds where Python questions included:

- decorators
- shallow vs deep copy
- lists
- modules/packages/namespaces
- memory management
- garbage collection
- whether numeric types are mutable or immutable
- exception handling

The candidate explicitly described the later round as testing how strong their **Python fundamentals/base** were.

### Relevant questions for this plan

```text
What happens when a list is shallow copied?
Why are integers immutable?
What is a Python namespace?
How does garbage collection complement reference counting?
What happens to an exception if you do not catch it?
```

Do not spend hours on garbage-collector internals; understand the high-level explanation.

**Source:** [Glassdoor — Systango Senior Python Developer Interview Question](https://www.glassdoor.com/Interview/Will-talk-only-about-2-technical-round-In-1st-technical-round-They-will-check-your-basic-concepts-They-asked-me-ques-ab-QTN_4813133.htm)

---

## 13. Scaler — Python Assessment

A candidate who selected Python in an assessment reported questions on:

- dunder methods
- Python OOP
- polymorphism
- GIL
- Python thread safety
- multithreading
- multiprocessing

The assessment was largely verbal: the candidate had to **explain the concepts**, not merely recognize a correct multiple-choice answer.

### Practise answering aloud

```text
Name important dunder methods and explain when Python invokes them.
Is Python thread-safe?
Does the GIL make every operation thread-safe?
How is multiprocessing different from multithreading?
What is polymorphism in Python?
```

**Source:** [LeetCode Discuss — Scaler Online Assessment](https://leetcode.com/discuss/post/6703079/)

---

## 14. Paytm — Python Developer Interview Experience

An older but still useful Paytm interview report described a written round containing roughly a dozen Python questions ranging from easy to harder concepts, including:

- list comprehensions
- decorators
- generators
- iterators

The age of this report means it should not be used for current company-specific predictions, but it is useful because these same language concepts continue to appear in much newer interview reports.

**Source:** [GeeksforGeeks — Paytm Interview Experience, Python Developer](https://www.geeksforgeeks.org/interview-experiences/paytm-interview-experience-python-developer-2-5-years/)

---

# What these real reports suggest you should prioritize

Across the reports above, several themes repeat far more than obscure Python internals.

## Tier 1 — repeatedly seen

```text
decorators
generators / iterators
GIL
threading vs multiprocessing
async / event loop
Python OOP behavior
```

## Tier 2 — also clearly worth knowing

```text
context managers
exceptions
closures
mutable vs immutable
shallow vs deep copy
classmethod vs staticmethod
dunder methods
MRO / multiple inheritance
```

## Lower value for a 10–12 hour plan

Topics such as deep metaclass mechanics, CPython bytecode internals, C-extension internals, and advanced descriptor implementation did **not** show the same repetition in the interview reports reviewed for this plan.

That is why they are intentionally excluded from the main study schedule.

---

# Actual-interview rapid-fire mock

After completing the main plan, try answering these **without notes**.

1. Implement a decorator that works with arbitrary function arguments.
2. How do multiple decorators execute?
3. What is a generator and how does it differ from a normal function?
4. Iterable vs iterator?
5. What is the GIL?
6. Does the GIL mean Python cannot use multiple threads?
7. Why can threads still help I/O-bound applications?
8. Threading vs multiprocessing?
9. `asyncio` vs multiprocessing?
10. What is an event loop?
11. What happens when blocking code is called from an async coroutine?
12. What is a context manager?
13. What do `__enter__` and `__exit__` do?
14. `classmethod` vs `staticmethod`?
15. What happens with multiple inheritance when two parents define the same method?
16. What is MRO?
17. Shallow copy vs deep copy?
18. Mutable vs immutable?
19. Why is a mutable default argument dangerous?
20. Explain exception ordering from specific exceptions to `Exception`.
21. What does `finally` guarantee?
22. What are important dunder methods you have actually used?
23. How would you write a timing/logging decorator?
24. What is a closure?
25. How does late binding affect lambdas created inside a loop?

If these questions feel comfortable, the **10–12 hour Python preparation goal is complete**.
