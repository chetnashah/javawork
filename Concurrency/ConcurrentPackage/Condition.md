

## Can only exist in context of a Lock

A Condition instance is intrinsically bound to a lock. To obtain a Condition instance for a particular Lock instance use its `lock.newCondition()` method.

## Used for wait/notify

Condition factors out the Object monitor methods (wait, notify and notifyAll) into distinct objects to give the effect of having multiple wait-sets per object, by combining them with the use of arbitrary Lock implementations. Where a Lock replaces the use of synchronized methods and statements, a Condition replaces the use of the Object monitor methods.


## Main interface


```java
interface Condition {
    fun await()
    fun awaitUninterruptibly()
    fun awaitNanos(nanosTimeout: Long): Long
    fun awaitUntil(deadline: Date): Boolean
    fun signal()
    fun signalAll()
}
```

## Types of Condition wait

1. interruptible, 
2. non-interruptible, and 
3. timed

## Example usage

```java
 class BoundedBuffer {
   final Lock lock = new ReentrantLock();
   final Condition notFull  = lock.newCondition(); 
   final Condition notEmpty = lock.newCondition(); 

   final Object[] items = new Object[100];
   int putptr, takeptr, count;

   public void put(Object x) throws InterruptedException {
     lock.lock();
     try {
       while (count == items.length)
         notFull.await();// lock will be suspended till someone notifies on this condition
       items[putptr] = x;
       if (++putptr == items.length) putptr = 0;
       ++count;
       notEmpty.signal();
     } finally {
       lock.unlock();
     }
   }

   public Object take() throws InterruptedException {
     lock.lock();
     try {
       while (count == 0)
         notEmpty.await();// lock will be suspended till someone notifies on this condition
       Object x = items[takeptr];
       if (++takeptr == items.length) takeptr = 0;
       --count;
       notFull.signal();
       return x;
     } finally {
       lock.unlock();
     }
   }
 }
```