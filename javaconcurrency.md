

* It is a common mistake to assume that synchronization needs to be used only when writing to shared variables. this is simply not true.

Java Memory model FAQ - http://www.cs.umd.edu/~pugh/java/memoryModel/jsr-133-faq.html#reordering

### Main causes of concurrency issues

1. interleaved execution between threads
2. instruction re-ordering (instruction 2 happens before instruction 1) (compilers are free to do this for optimization)
3. cpu caches not flushing values to main memory.


### Memory barriers

At the processor level, a memory model defines necessary and sufficient conditions for knowing that writes to memory by other processors are visible to the current processor, and writes by the current processor are visible to other processors. Some processors exhibit a strong memory model, where all processors see exactly the same value for any given memory location at all times. Other processors exhibit a weaker memory model, where special instructions, called memory barriers, are required to flush or invalidate the local processor cache in order to see writes made by other processors or make writes by this processor visible to others. These memory barriers are usually performed when lock and unlock actions are taken; they are invisible to programmers in a high level language.


### volatile keyword

makes sure cache variables are flushed to RAM on write,
and read from RAM on read. effectively keeping the volatile variable out of CPU caches


### Out of thin air safety

When a thread reads a variable without synchronization, it may see a stale value but it atleast sees a value
that was placed by some other thread rather than random value.

This safety guarentee is called out-of-thin-air safety.

### Simple instructions are multistep (read-modify-write)

e.g.
```
count++
```
Is a three instruction statement:
1. read count
2. modify (i.e. increment)
3. write to count.

Instruction re-ordering can mess with this, so this must be done atomically.

### Put-if-absent would need lock

```java
if(!vector.contains(element)) {
    vector.add(element);
}
```


### Synchronized

Synchronization also creates a `"happens-before" memory barrier`, causing a memory visibility constraint such that anything done up to the point some thread releases a lock appears to another thread subsequently acquiring the same lock to have happened before it acquired the lock

![Synchronization](synchronization.png)

#### synchronized method

Holds an intrinsic mutex lock on the this object.

Intrinsic locks are re-entrant on a thread level, JVM maintains an owner thread and a counter. If a lock is already held, same method can be re-entered. Useful in cases like recursion


### NonAtomic 64 bit operations

JVM is permitted to treat 64-bit read or write operations as
two separate 32-bit operations.

Even if you don't care about stale values,
it is not safe to use shared mutable long and double variables in multithreaded programs,
unless they are declared volatile or guarded by a lock.

### Visibility and synchronization

Locking is not just about mutual exclusion. `It is also about memory visibility`.
To ensure visibility across all threads for a common shared mutable object, reading and writing threads must synchronize on a common lock.

