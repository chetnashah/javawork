
### volatile keyword

makes sure cache variables are flushed to RAM on write,
and read from RAM on read.


### Synchronized

#### synchronized method

Holds an intrinsic mutex lock on the this object.

Intrinsic locks are re-entrant on a thread level, JVM maintains an owner thread and a counter. If a lock is already held, same method can be re-entered. Useful in cases like recursion