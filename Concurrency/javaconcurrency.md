
A good tool to do visual tracing is VisualVM.
https://docs.oracle.com/javase/specs/jls/se8/html/jls-17.html#jls-17.4

http://jeremymanson.blogspot.com/

https://www.youtube.com/watch?v=YzpAJ08c61s&list=PLZ9NgFYEMxp6IM0Cddzr_qjqfiGC2pq1a&index=18

## Java Thread states

A thread state. A thread can be in one of the following states:
* `NEW` - A thread that has not yet started is in this state.
* `RUNNABLE` - A thread executing in the Java virtual machine is in this state ( but it may be waiting for other resources from the operating system like file I/O). **Note** - this also contains `RUNNING` inside of it, there is no way to distinguish from `RUNNING` from `RUNNABLE` via `thread.getState()`.
* `BLOCKED` - A thread that is blocked waiting for a monitor lock is in this state.
* `WAITING` -A thread that is waiting indefinitely for another thread to perform a particular action is in this state. A thread is in the waiting state due to calling one of the following methods:
`Object.wait with no timeout`,`Thread.join with no timeout`,`LockSupport.park`.
* `TIMED_WAITING` - A thread that is waiting for another thread to perform an action for up to a specified waiting time is in this state. (Think `Thread.Sleep`), sometimes also considered `SLEEPING`.  A thread is in the timed waiting state due to calling one of the following methods with a specified positive waiting time:
`Thread.sleep`,`Object.wait with timeout`,`Thread.join with timeout`,`LockSupport.parkNanos`,`LockSupport.parkUntil`
* `TERMINATED` - A thread that has exited is in this state.

Linux States -  state (R is `running`, S is `sleeping`, D is `sleeping in an uninterruptible wait`, Z is `zombie`,T is `traced or stopped`)

Checking Windows thread states - download (Process Explorer) http://technet.microsoft.com/en-us/sysinternals/bb896653.aspx

## Synchronization actions

Synchronization actions, which are:

1. `Volatile read`. A volatile read of a variable.
2. `Volatile write`. A volatile write of a variable.
3. `Lock`. Locking a monitor
4. `Unlock`. Unlocking a monitor.
5. The (synthetic) `first and last action of a thread`. (everything has to be flushed before start of a thread and at end of a thread), thus introducing a synchronization order/barrier.
6. Actions that start a thread or detect that a thread has terminated (§17.4.4).

jni actions.


## Happens-before order

If we have two actions x and y, we write `hb(x, y)` to indicate that x happens-before y.

1. If x and y are actions of the same thread and x comes before y in program order, then hb(x, y).
There is a happens-before edge from the end of a constructor of an object to the start of a finalizer (§12.6) for that object.

2. If an action x synchronizes-with a following action y, then we also have hb(x, y).

3. If hb(x, y) and hb(y, z), then hb(x, z).


## UI frameworks and threads

To protect UI objects, windowing/UI toolkits will restrict threads from which UI objects can be manipulated.
And UI objects will be modified only via UI threads, and it will be applications responsibility to manage communication
between bg thread and UI thread and call UI objects only on UI thread.

### JNI and threads

The JNI does not create any new thread behind the scene. A native function is executed in the same thread as the java method that calls the native function. And vice versa when native code calls a java method then the the java method is executed in the same thread as the native code calling the method.


### I/O and thread states:
A thread that is blocked in an I/O syscall should be in RUNNABLE state.

Checking source code of `https://github.dev/openjdk/jdk/blob/master/src/java.base/share/native/libjava/FileInputStream.c` also suggests, busy while loop code for `RESTARTABLE` macro, which means thread doing I/O system call is doing a busy loop and hence is RUNNABLE (INternally READY or RUNNING).

A program based Proof:
```java
public class TestThreadStates {
    public static void main(String[] args) throws InterruptedException {
        Thread t = new Thread(new Runnable() {
            @Override
            public void run() {
                while(true) {
                    try {
                        System.in.read();// System.in is an InputStream
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
        t.start();
        while(true) {
            System.out.println("State of t is  "+ t.getState());// RUNNABLE prints
            Thread.sleep(1500);
        }
    }
}
```
### 
* It is a common mistake to assume that synchronization needs to be used only when writing to shared variables. this is simply not true.

Java Memory model FAQ - http://www.cs.umd.edu/~pugh/java/memoryModel/jsr-133-faq.html#reordering

### Main causes of concurrency issues

1. interleaved execution between threads
2. instruction re-ordering (instruction 2 happens before instruction 1) (compilers are free to do this for optimization)
3. cpu caches not flushing values to main memory.


### Memory visibility

Java Memory model defines, when changes made to memory by one thread is visible to another thread.

**There are no guarantees for memory changes unless reading and writing threads use synchronization**

**When you synchronize on an object monitor A, it is guaranteed that another thread synchronizing on the same monitor A afterwards will see any changes made by the first thread to any object.** That's the visibility guarantee provided by synchronized, nothing more.

a "dirty read" can occur if a read operation is allowed (without synchronization) while a write
operation is in progress. If read is also synchronized, it will wait for unlock+value flush done by write synchronization.

### Memory barriers

At the processor level, a memory model defines necessary and sufficient conditions for knowing that writes to memory by other processors are visible to the current processor, and writes by the current processor are visible to other processors. Some processors exhibit a strong memory model, where all processors see exactly the same value for any given memory location at all times. Other processors exhibit a weaker memory model, where special instructions, called memory barriers, are required to flush or invalidate the local processor cache in order to see writes made by other processors or make writes by this processor visible to others. These memory barriers are usually performed when lock and unlock actions are taken; they are invisible to programmers in a high level language.


### volatile keyword

makes sure cache variables are flushed to RAM on write,
and read from RAM on read. effectively keeping the volatile variable out of CPU caches. **Volatile influences visibility by influencing both instruction reordering fence and also main memory flushing**

`certain reorderings are not allowed, once you introduce a volatile keyword, i.e. instructions around volatile reads and writes are not allowed to reorder across to the other side of volatile read/write`.

- When a thread is writes to a volatile variable, all of its previous writes are guaranteed to be visible to another thread when that thread is reading the same value.

http://jeremymanson.blogspot.com/2008/11/what-volatile-means-in-java.html

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

### Object construction can be a non-atomic three step process (that might have reordering)

```java
MyClass obj = new MyClass();
```
1. Allocate memory for `new MyClass()`.
2. update obj reference to point to that memory
3. initalize fields & run constructor of `mYClass`.

### Synchronized

**Synchronization also creates a `"happens-before" memory barrier`, causing a memory visibility constraint such that anything done up to the point some thread releases a lock appears to another thread subsequently acquiring the same lock to have happened before it acquired the lock**, i.e. memory is flushed to main memory, just before exiting the synchronized block.

exiting monitor flushes everyting, entering monitor gets everything afresh.

synchronized blocks are by definition `mutually exclusive`.

instructions that are part of the same synchronized block, may be reordered with each other.

![Synchronization](images/synchronization.png)


### synchronized and constructors

**constructors cannot be marked synchronized** because other threads 
cannot see the object being created until the thread creating it has finished it.

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
To ensure visibility across all threads for a common shared mutable object, **reading and writing threads must synchronize on a common lock.**

**When you synchronize on an object monitor A, it is guaranteed that another thread synchronizing on the same monitor A afterwards will see any changes made by the first thread to any object.** That's the visibility guarantee provided by synchronized, nothing more.

## Double checked locking

Double Checked Locking is this idiom:
```java
// Broken -- Do Not Use!
class Foo {
  private Helper helper = null;
  public Helper getHelper() {
    if (helper == null) {
      synchronized(this) {
        if (helper == null) {
          helper = new Helper();
        }
      }
    }
  return helper;
}
```
The point of this code is to avoid synchronization when the object has already been constructed. This code doesn't work in Java. The basic principle is that compiler transformations (this includes the JIT, which is the optimizer that the JVM uses) can change the code around so that the code in the Helper constructor occurs after the write to the helper variable. If it does this, then after the constructing thread writes to helper, but before it actually finishes constructing the object, another thread can come along and read helper before it is finished initializing.
See the double-checked locking is broken declaration for a much more detailed explanation.

Anyway, the question I always get is "does making the helper field volatile fix this?" The answer is yes. If you make the helper field volatile, the actions that happen before the write to helper in the code must, when the program executes, actually happen before the write to helper — no sneaky reordering is allowed.

## static method Thread.yield()

It provides hint to scheduler that current thread wants to yield/give up its use of processor.

## t.join() method makes current thread wait till thread t terminates

`join()` is another mechanism of inter-thread synchronization, waits for referred thread to finish.

`threadRef.join()` puts the calling thread in waiting state, and the calling thread waits till the referred thread `threadRef` is either terminated or interrupted.

if the referenced thread was already terminated or hasn't been started, the call to join() method returns immediately.

Here is an example program where main thread waits for a spawned thread.
This will always print message from new/fresh spawned thread before message from main thread.
```java
public class JoinSample {
    public static void main(String[] args) throws InterruptedException {
        Thread t = new Thread(new Runnable() {// this thread terminates when run returns
            @Override
            public void run() {
                System.out.println("printing from fresh new thread, will finish now");
            }
        });

        t.start();
        t.join();// wait for t to finish
        System.out.println("Main thread done");
    }
}
```


## Shared memory Example - shared counter updated by two threads

Have same counter variable be updated by two threads, 100 times by each thread.
At the end of execution of both, the counter value should be 200. 

Solution part 1: `increment` method that increments the shared counter should be marked `synchronized`,
for exclusive access by a single thread at a time.

**There would still be a bug present.**
**increment/write method alone synchronized is not enough. getCount/read method also needs to be synchronized** - Why?



## How to make my thread run forever?

### Approach 1: while(true) + break;



## Spin wait approach to co-ordinated concurrency

mutex is usually not enough, since if some condition was not satisfied, consumer must retry in a loop.
See example below.Common use case include: check queue size non-empty before consume, or check queue size non-full before produce.

```java
void busyWaitFunction() {
    acquire-mutex();
    while (predicate is false) {
      release-mutex();
      acquire-mutex();
    }
    // do something useful
    // in this critical section
    release-mutex();
}
```


## Instead of spinning in a loop, we introduce a queue + notification event system

This is essence of a monitor/condition variable i.e. a wait queue+notify over a condition, 
instead of spin lock over a condition.

The `wait()` method when call on a condition-variable, will be release the lock, and will be placed on a `wait queue` for that condition variable.

The `signal()` method causes one of the threads in the waitqueue to continue execution.

**Condition variable based solution, note: predicate var and condVar are separate** - wait/notify being methods on condition variable(queue):

```java
void efficientWaitingFunction() {
    mutex.acquire()
    while (predicate == false) { // note here also we have while loop, but no spinning
      condVar.wait() // enqueue and wake up on some signaling
    }
    // Do something useful
    mutex.release()     
}

void changePredicate() {
    mutex.acquire()
    set predicate = true
    condVar.signal() // signal to wake up others based whoe were waiting based on some predicate
    mutex.release()
}
```


## Why do we need wait/notify in java?

We can do things perfectly with schronzied blocks + busy loop checking (e.g. a synchronized queue).
But that is wasteful for CPU, that is why wait/notify were introduced. So there is a sense of observability/reactivity in case of wait/notify, where the waited one does not take up cpu and notifying one notifies the waiting one.


## Use @GuardedBy annotation for static analysis

https://developer.android.com/reference/androidx/annotation/GuardedBy

