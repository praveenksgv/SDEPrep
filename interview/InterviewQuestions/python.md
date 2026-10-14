1. Fastapi problem - Curefit 
```

"""Candidate exercise: reliable payment submission.


## Brief


An invoice-reconciliation service has approved a payment and must submit it to
an external finance ledger. Implement the missing parts of ``submit_payment``.


The ledger API can succeed, raise ``TransientFinanceError`` for a temporary
failure, raise ``PermanentFinanceError`` for an invalid payment, or raise
``AmbiguousFinanceError`` when the request may have been accepted but no
response was received, such as after a network timeout.


Your implementation must:


1. Validate the payment before making an external call.
2. Reuse the same idempotency key on every attempt.
3. Retry transient failures only, up to ``max_attempts``.
4. Apply bounded exponential backoff using ``sleep_fn``. ``sleep_fn`` is
  injected so tests can record delays without actually waiting.
5. Return ``POSTED`` on success with the ledger transaction ID.
6. Return ``FAILED`` for permanent failures or exhausted transient retries.
7. Return ``UNKNOWN`` for an ambiguous result; never treat it as definite
  failure because the ledger may already contain the payment.
8. Keep the code easy to unit test. Do not make a real network call.


The test harness provides a fake ``FinanceClient`` that simulates the ledger.
Assume ``post_payment`` is an atomic, idempotent ledger operation: concurrent
requests with the same idempotency key create at most one ledger entry and
return the same transaction ID. Do not implement a real network call or the
body of ``post_payment``; call the injected method from ``submit_payment`` and
handle its return value and exceptions. You may change the interfaces if you
explain the tradeoff.


At minimum, your implementation should make these scenarios clear:


- A successful first call returns ``POSTED`` and the ledger transaction ID.
- A transient failure followed by success retries with the same idempotency
 key and eventually returns ``POSTED``.
- A permanent failure is not retried and returns ``FAILED``.
- Exhausting transient retries returns ``FAILED`` with the attempt count.
- An ambiguous timeout returns ``UNKNOWN`` and can be reconciled later using
 the payment ID or idempotency key.
- For two workers submitting the same payment, rely on the supplied client's
 atomic ledger-side idempotency guarantee: both calls use the same key and
 produce at most one ledger entry. Explain that a local atomic claim/store
 would be needed if the ledger did not provide this guarantee; you do not
 need to build one.


Concurrency example:


   Worker A: post_payment(payment=PAY-42, idempotency_key="KEY-42")
             -> creates ledger transaction TX-100
   Worker B: post_payment(payment=PAY-42, idempotency_key="KEY-42")
             -> receives the existing transaction ID TX-100


The two calls may happen at the same time. The ledger's atomic idempotency
behavior, not a check-then-submit sequence in this function, prevents a
duplicate entry. A different payment using ``KEY-42`` would be an
idempotency-key conflict and should not be silently submitted.


You should be prepared to explain where payment state would be persisted in a
real service, how a later reconciliation would resolve ``UNKNOWN``, and how
you would test all of these cases without waiting in real time. Also explain
what changes, if any, are needed when two workers submit the same payment
concurrently.


"""


from dataclasses import dataclass
from decimal import Decimal
from enum import Enum
from time import sleep
from typing import Callable, Optional, Protocol




class PaymentStatus(Enum):
   PENDING = "pending"
   POSTED = "posted"
   FAILED = "failed"
   UNKNOWN = "unknown"




@dataclass(frozen=True)
class Payment:
   payment_id: str
   amount: Decimal
   currency: str
   idempotency_key: str




@dataclass(frozen=True)
class PaymentResult:
   payment_id: str
   status: PaymentStatus
   ledger_transaction_id: Optional[str] = None
   attempts: int = 0
   message: Optional[str] = None




class TransientFinanceError(Exception):
   """The request can be retried safely with the same idempotency key."""




class PermanentFinanceError(Exception):
   """The request should not be retried without correcting the payment."""




class AmbiguousFinanceError(Exception):
   """The ledger may have processed the request, but the result is unknown."""




class FinanceClient(Protocol):
   """Dependency supplied by the caller or test harness.


   The candidate does not need to implement this external API client.
   ``post_payment`` is assumed to atomically deduplicate concurrent requests
   using ``idempotency_key`` and return the same transaction ID for repeats.
   """


   def post_payment(
       self,
       *,
       payment: Payment,
       idempotency_key: str,
   ) -> str:
       """Return the ledger transaction ID or raise a finance error."""
       ...




def submit_payment(
   payment: Payment,
   client: FinanceClient,
   *,
   max_attempts: int = 3,
   base_backoff_seconds: float = 0.25,
   # Called once before each retry with the delay in seconds. It does not
   # control how many times the operation is retried; max_attempts does that.
   sleep_fn: Callable[[float], None] = sleep,
) -> PaymentResult:
   """Submit one payment reliably.


   Requirements:
   - Retry transient failures, but never retry permanent failures.
   - Reuse the same idempotency key on every attempt.
   - Do not report an ambiguous timeout as a definite failure.
   - Do not make tests wait in real time; use the injected sleep function.


   ``sleep_fn(delay_seconds)`` should be called only before a retry, not after
   the final attempt. In production it defaults to ``time.sleep``; in tests,
   pass something like ``lambda delay: delays.append(delay)``.
   """


   if max_attempts <= 0:
       raise ValueError("max_attempts must be positive")


   # TODO 1: validate payment_id, amount, currency, and idempotency_key.
   # Decide which validation failures should be raised before calling client.


   attempts = 0


   while attempts < max_attempts:
       attempts += 1


       try:
           # TODO 2: call the finance client with the payment and the same
           # idempotency key for every attempt.
           ledger_transaction_id = ""


           # TODO 3: return a POSTED result containing the transaction ID and
           # the number of attempts.
           raise NotImplementedError


       except TransientFinanceError as exc:
           # TODO 4: retry only if attempts remain. Apply bounded exponential
           # backoff, e.g. base * (2 ** (attempts - 1)).
           # Call sleep_fn(delay) before another attempt, not after the final
           # attempt. In the final-attempt case, return FAILED with a useful
           # message.
           raise NotImplementedError from exc


       except PermanentFinanceError as exc:
           # TODO 5: return FAILED without retrying.
           raise NotImplementedError from exc


       except AmbiguousFinanceError as exc:
           # TODO 6: return UNKNOWN. The caller must reconcile this payment
           # using the idempotency key or a finance-side status lookup.
           raise NotImplementedError from exc


   # TODO 7: make this branch unreachable or return a safe failure result.
   raise NotImplementedError

```