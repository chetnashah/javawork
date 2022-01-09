
### what happens to a thread if the activity which started a thread is destroyed?

Thread will still stay alive, but no guarantee of how long the thread will stay alive.


### Terminating threads

Just return from `run()`.
Some common ways to run a thread indefinitely is to have a `while(true)` inside a thread, accompanied with a blockingqueue to read Runnable tasks to execute.
but you can have external synchronized flag, to bail out of this infinite while loop.

### ThreadPoolExecutor

Main Idea: `X Runnables` in a task Queue, can be consumed by `Y Threads`(Thread pool size). (worker pattern)

Internally Needs a `java.util.concurrent.BlockingQueue of Runnable` to manage a queue of tasks via a collection of threads.
```java
private static int NUMBER_OF_CORES = Runtime.getRuntime().availableProcessors();

// Instantiates the queue of Runnables as a LinkedBlockingQueue
private final BlockingQueue<Runnable> workQueue = new LinkedBlockingQueue<Runnable>();

// Sets the amount of time an idle thread waits before terminating
private static final int KEEP_ALIVE_TIME = 1;
// Sets the Time Unit to seconds
private static final TimeUnit KEEP_ALIVE_TIME_UNIT = TimeUnit.SECONDS;

// Creates a thread pool manager
ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(
        NUMBER_OF_CORES,       // Initial pool size
        NUMBER_OF_CORES,       // Max pool size
        KEEP_ALIVE_TIME,
        KEEP_ALIVE_TIME_UNIT,
        workQueue
);
```

**note**- `ThreadPoolExecutor` extends `Executor`, `ExecutorService`

### Executor vs ExecutorService
`Executor` just executes stuff you give it, it is an abstraction interface.

`ExecutorService` adds startup, shutdown, and the ability to wait for and look at the status of jobs 
you've submitted for execution on top of `Executor` (which it extends).

Another important difference between ExecutorService and Executor is that Executor defines execute() method which accepts an object of the Runnable interface, while submit() method can accept objects of both Runnable and Callable interfaces

the `Executor.execute()` method doesn't return any result, its return type is void but the `submit()` method returns the result of computation via a `Future` object

`Executors` - Factory/facade to get above instances

### Alternative to creating and managing threads by hand

A thread pool is a managed collection of threads that runs tasks in parallel from a queue. New tasks are executed on existing threads as those threads become idle. To send a task to a thread pool, use the ExecutorService interface. Note that `ExecutorService` has nothing to do with `Services`, the Android application component.


```java
public class MyApplication extends Application {
    ExecutorService executorService = Executors.newFixedThreadPool(4);
}
```

```java
public class LoginRepository {
    
    public void makeLoginRequest(final String jsonBody) {
        executor.execute(new Runnable() {
            @Override
            public void run() {
                Result<LoginResponse> ignoredResponse = makeSynchronousLoginRequest(jsonBody);
            }
        });
    }

    public Result<LoginResponse> makeSynchronousLoginRequest(String jsonBody) {
         // HttpURLConnection logic
    }
    
}
```