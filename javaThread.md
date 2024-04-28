

## wait method

Causes the current thread to wait until it is awakened, typically by being `notified` or `interrupted`.

## User thread vs Daemon thread

In Java, threads can be categorized as user threads and daemon threads based on their behavior. Here's an explanation of the two:

1. **User Threads:**
   - These are the threads that keep the program alive as long as they are running.
   - If any user thread is still running, the Java Virtual Machine (JVM) will not exit, even if all the main threads have finished executing.
   - User threads are created by default when you create a new `Thread` object unless specified otherwise.

   Example of creating a user thread:
   ```java
   Thread userThread = new Thread(() -> {
       // Thread logic
   });
   userThread.start();
   ```

2. **Daemon Threads:**
   - Daemon threads, on the other hand, are considered as service providers for user threads running in the same process.
   - If all user threads finish their execution, daemon threads are automatically terminated by the JVM, and the program exits.
   - If java main thread was started with arguments (command line), the spawned threads are considered daemon threads.
   - You can explicitly set a thread as a daemon thread by calling the `setDaemon(true)` method before starting the thread.

   Example of creating a daemon thread:
   ```java
   Thread daemonThread = new Thread(() -> {
       // Thread logic
   });
   daemonThread.setDaemon(true);
   daemonThread.start();
   ```

**Important points:**
- Daemon threads are typically used for background tasks or services that should not prevent the JVM from shutting down when the main program has completed its execution.
- User threads are usually used for tasks that are essential to the program's functionality and should keep the program running until they complete.

In summary, the key difference lies in the termination behavior: user threads keep the program alive, while daemon threads do not prevent the JVM from exiting once all user threads have finished executing.
