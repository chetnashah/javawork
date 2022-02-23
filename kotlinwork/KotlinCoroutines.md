
* A suspendable computation.
* Not bound to any particular thread. It may suspend its execution on one thread and resume on another.

### CoroutineContext

Coroutines always execute in some context represented by a value of `CoroutineContext type`.



### CoroutineScope

Every `coroutine builder`, e.g. `launch`, `async` is an extension of `CoroutineScope` and automatically inherits its `coroutineContext` to automatically propogate all its elements and cancellation.

### Dispatchers

Base class to be extended by all coroutine dispatcher implementations.

Three types:
1. `Dispatchers.Default`:
2. `Dispatchers.IO`: uses a shared thread pool of ondemand created threads and is designed for offloading of IO intensive blocking operations.
3. `Dispatchers.Unconfined`:

### launch

`launch` is a coroutine builder.
It launches a new coroutine concurrently with the rest of code.


### delay

special suspending function, `suspending does not block the thread`.

