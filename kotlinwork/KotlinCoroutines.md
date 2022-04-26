
* A suspendable computation.
* Not bound to any particular thread. It may suspend its execution on one thread and resume on another.

## Using coroutines

Not available by default, needs to be added as a gradle dependency.

## suspend keyword

marks a function as suspendable


## coroutine builders

To start a new coroutine we use one of the main "coroutine builders".

1. runBlocking
2. async
3. launch

All coroutine builders take a lambda in which `this` is set to coroutineScope
### CoroutineContext

Coroutines always execute in some context represented by a value of `CoroutineContext type`.


### CoroutineScope

Coroutine scope is responsible for the structure and parent-child relationships between different coroutines. We always start new coroutines inside a scope.

Every `coroutine builder`, e.g. `launch`, `async` is an extension of `CoroutineScope` and automatically inherits its `coroutineContext` to automatically propogate all its elements and cancellation.

When `launch`, `async`, or `runBlocking` are used to start a new coroutine, they automatically create the corresponding scope.


```kt
launch { /* this: CoroutineScope */
}
```

### Dispatchers

Base class to be extended by all coroutine dispatcher implementations.

Three types:
1. `Dispatchers.Default`:
2. `Dispatchers.IO`: uses a shared thread pool of ondemand created threads and is designed for offloading of IO intensive blocking operations.
3. `Dispatchers.Unconfined`:

### runBlocking

It is designed to bridge regular blocking code to libraries that are written in suspending style, to be used in main functions and in tests.

### launch

`launch` is a coroutine builder.
launch is used for starting a computation that isn't expected to return a specific result. launch returns `Job`, which represents the coroutine. 

It is possible to wait until it completes by calling `Job.join()`.
It launches a new coroutine concurrently with the rest of code.


### async

async starts a new coroutine and returns a Deferred object.

### delay

special suspending function, `suspending does not block the thread`.

