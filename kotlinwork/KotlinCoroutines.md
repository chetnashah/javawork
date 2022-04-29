
## Usage
Not available by default, needs to be added as a gradle dependency.

on jvm:
```groovy
dependencies {
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.6.1")
}
```

On android:
```groovy
dependencies {
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-android:1.6.1")
}
```

## design doc

https://github.com/Kotlin/KEEP/blob/master/proposals/coroutines.md
https://www.youtube.com/watch?v=YrrUCSi72E8
## 
* A suspendable computation.
* Not bound to any particular thread. It may suspend its execution on one thread and resume on another.
* multiple coroutines can concurrently run on a single thread,
* very cheap


## DEbugging coroutines on android

```kotlin
class MainApplication: Application() {
    override fun onCreate() {
        super.onCreate()
        System.setProperty("kotlinx.coroutines.debug", "on" )
    }
}
```

## Starting coroutine



## Coroutines launched on Globalscope do not keep the process alive
Active coroutines launched in GlobalScope do not keep the process alive. 
They are like daemon.

A process can be killed before Globalscope.launch ed coroutine gets a chance to run.
e.g.
```kotlin
fun main(){
    println("Main running! from thread name = ${Thread.currentThread().name}")
    GlobalScope.launch {// does not keep application alive/running
        println("Running inside a launched coroutine")
    }
}
```

## By defaulted coroutines are run in non-main threads (E.g. DefaultDispatcher-worker-1)

See example:
```kotlin
fun main(){
    println("Main running! from thread name = ${Thread.currentThread().name}")
    // creates and runs coroutine on a background thread
    GlobalScope.launch {// does not keep application alive/running
        println("fake work inside a launched coroutine, current thread = ${Thread.currentThread().name}") //DefaultDispatcher-worker-1 
    }
    Thread.sleep(1000) // giving coroutine a chance to run
}
```
## suspend keyword

marks a function as suspendable.
**A suspendable/suspending function is only allowed to be called from a coroutine or from another suspending function.**

The `suspend` modifier indicates that this is a function that can suspend the execution of a coroutine by calling a library suspend function like `delay`, `yeild`.

## suspending lambda

A lambda that might be using a suspending function.
Type signature looks like this: `suspend () -> Unit`

## Suspension point

A suspension point — is a point during coroutine execution where the execution of the coroutine may be suspended. Syntactically, a suspension point is an invocation of suspending function, but the actual suspension happens when the suspending function invokes the standard library primitive to suspend the execution.


## Continuation

A fancy name for a generic callback. 
State of suspended coroutine at the suspension point. You can think of it as remaining code after suspension point.
It conceptually represents the rest of its execution after the suspension point.

Interace definition:
```kotlin
interface Continuation<in T> {
   val context: CoroutineContext
   fun resumeWith(result: Result<T>) // equivalent to resolve in a promise constructor
   fun resumeWithException() // equivalent to reject in a promise constructor
}
```

`resumeWith`: The `resumeWith` function is a completion callback that is used to report either a success (with a value) or a failure (with an exception) on coroutine completion.

## `.await()` extension function on Futures

Only available via following gradle dependency:
`kotlinx-coroutines-jdk8`, in package: `import kotlinx.coroutines.future.await`

```kotlin
        var f = CompletableFuture.completedFuture(11)
        var ans = f.await()
        println("\nF = $ans")
```

### ContinuationInterceptor

It is a `CoroutineContext.Element`.
Interface definition:
```kotlin
interface ContinuationInterceptor : CoroutineContext.Element {
    fun <T> interceptContinuation(continuation: Continuation<T>): Continuation<T>
}
```

## coroutine builders

To create and start a new coroutine we use one of the main "coroutine builders".

All coroutine builders are regular functions, that take a `context` and a `block: suspend () -> ?` i.e. a suspending lambda.

1. `runBlocking` - runs/blocks coroutine on thread in which it is specified
2. `async` - takes a suspending lambda returns a `Deferred<Result>` which is same as a Promise/Future. You can call `.await()` on a deferred object e.g. `deferred1.await()`.
3. `launch` - non-blocking in nature, as it runs coroutine in separate thread than where it is specified.
4. `future` - returns a CompletableFuture.

All coroutine builders take a lambda in which `this` is set to coroutineScope
### CoroutineContext

Coroutine context is a persistent set of user-defined objects that can be attached to the coroutine. 

It may include objects responsible for coroutine threading policy, logging, security and transaction aspects of the coroutine execution, coroutine identity and name, etc. Here is the simple mental model of coroutines and their contexts. 

Think of a coroutine as a light-weight thread. In this case, coroutine context is just like a collection of thread-local variables. The difference is that thread-local variables are mutable, while coroutine context is immutable, which is not a serious limitation for coroutines, because they are so light-weight that it is easy to launch a new coroutine when there is a need to change anything in the context.


Coroutines always execute in some context represented by a value of `CoroutineContext type`.

#### CoroutineContext.Element

An `Element` of the coroutine context is a context itself. 
It is a singleton context with this element only. 

This enables creation of composite contexts by taking library definitions of coroutine context elements and joining them with +. For example, if one library defines auth element with user authorization information, and some other library defines threadPool object with some execution context information, then you can use a `launch{}` coroutine builder with the combined context using `launch(auth + threadPool) {...}` invocation.

### suspendCoroutine

```kotlin
suspend fun <T> suspendCoroutine(block: (Continuation<T>) -> Unit): T
```
Takes a normal callback function and turns it into a suspending lambda.

When `suspendCoroutine` is called inside a coroutine (and it can only be called inside a coroutine, because it is a suspending function) it captures the execution state of a coroutine in a continuation instance and passes this continuation to the specified block as an argument. 

To resume execution of the coroutine, the block invokes `continuation.resumeWith()` (either directly or using `continuation.resume()` or `continuation.resumeWithException()` extensions) in this thread or in some other thread at some later time. 

**The actual suspension of a coroutine happens when the suspendCoroutine block returns without invoking resumeWith**

e.g. usage
```kotlin
suspend fun <T> CompletableFuture<T>.await(): T = // extension function on Completablefuture
    suspendCoroutine<T> { cont: Continuation<T> ->
        whenComplete { result, exception -> // whenComplete is a part of Future API
            if (exception == null) // the future has been completed normally
                cont.resume(result) // resolve
            else // the future has completed with an exception
                cont.resumeWithException(exception) // reject
        }
    }
```

Another example is `Call` which is a Future available from Retrofit library
```kotlin
suspend fun <T> Call<T>.await(): T = // extension function on Completablefuture
    suspendCoroutine<T> { cont: Continuation<T> ->
        enqueue(object: Callback<T>{
            override fun onResponse(call: Call<T>, response: Response<T>){
                if(response.isSuccessful) {
                    cont.resume(response.body)
                } else {
                    cont.resumeWithException(ErrorResponse(response))
                }
            }
            overrid fun onFailure(call: Call<T>, t: Throwable) {
                cont.resumeWithException(t)
            }
        })
    }
```

### startCoroutine

It is like a complement to suspendCoroutine.
e.g.
`block.startCoroutine(completionContinuation)` where
It is a method available on suspending lambda e.g.
```kotlin
fun <T> (suspend () -> T).startCoroutine(
    completion: Continuation<T>)
```

Starts a coroutine without a receiver and with result type `T`. 

This function creates and starts a new, fresh instance of suspendable computation every time it is invoked. 
The completion continuation is invoked when the coroutine completes with a result or an exception. basically completion continuation you can specify what to do after `block` finishes, by providing an anonymous object extending Continuation interface.





### CoroutineScope

Scope decides lifetime in which a coroutine is valid. Every coroutine needs to run in a scope. 
Coroutine scope is responsible for the structure and parent-child relationships between different coroutines. We always start new coroutines inside a scope.

Every `coroutine builder`, e.g. `launch`, `async` is an extension of `CoroutineScope` and automatically inherits its `coroutineContext` to automatically propogate all its elements and cancellation.

When `launch`, `async`, or `runBlocking` are used to create and start a new coroutine, they automatically create the corresponding scope.


```kt
launch { /* this: CoroutineScope */
}
```

`GlobalScope`: top level coroutines launched via `GlobalScope.launch { }` are top level coroutines that can survive entire life span of an application.


Android provides:
1. `lifecycleScope`:
2. `viewModelScope`:

### Dispatchers

Base class to be extended by all coroutine dispatcher implementations.
`determines what thread` or threads the corresponding coroutine uses for its execution. 
The coroutine dispatcher can confine coroutine execution to a specific thread, dispatch it to a thread pool, or let it run unconfined.

**All coroutine builders like `launch` and `async` accept an optional CoroutineContext parameter that can be used to explicitly specify the dispatcher for the new coroutine**

Three types:
1. `Dispatchers.Default`: Shared background pool of threads
2. `Dispatchers.IO`: uses a shared thread pool of ondemand created threads and is designed for offloading of IO intensive blocking operations.
3. `Dispatchers.Unconfined`:

e.g.
```kotlin
launch(Dispatchers.Unconfined) { // not confined -- will work with main thread
    println("Unconfined            : I'm working in thread ${Thread.currentThread().name}")
}
launch(Dispatchers.Default) { // will get dispatched to DefaultDispatcher 
    println("Default               : I'm working in thread ${Thread.currentThread().name}")
}
launch(newSingleThreadContext("MyOwnThread")) { // will get its own new thread
    println("newSingleThreadContext: I'm working in thread ${Thread.currentThread().name}")
}
```

The default dispatcher is used when no other dispatcher is explicitly specified in the scope. It is represented by Dispatchers.Default and uses a shared background pool of threads.

### runBlocking

It is designed to bridge regular blocking code to libraries that are written in suspending style, to be used in main functions and in tests.

**It runs coroutine in the same thread where it is specified** (e.g. here in the example in main thread)

```kotlin
fun main(){
    println("hello ")
    runBlocking {
        println("fake work by coroutine is done inside thread - ${Thread.currentThread().name}") // runs on main thread
    }
}
```

### launch

`launch` is a coroutine builder.
launch is used for starting a computation that isn't expected to return a specific result. launch returns `Job`, which represents the coroutine. 

It is possible to wait until it completes by calling `Job.join()`.
It launches a new coroutine concurrently with the rest of code.


### async

async starts a new coroutine and returns a Deferred object.
Think of `async` as `launch`, but with a Deferred result to be returned.
Runs as soon as specified,
`Deferred` extends `JOb`, but also has extra methods like `.await()`.

### delay

special suspending function, `suspending does not block the thread`.
similar to yeilding.
Code occuring after `delay` might be scheduled on a separate thread thread compared to the code before `delay`.

Since `delay` is a suspendable function, it can be only called inside a coroutine or other suspending functions.

You can think of this as similar to how await is only allowed to be called inside async functions in javascript.

## Job

To better manage a coroutine, a `job` is provided when we `launch` or `async`.
A `job` is part of context of the coroutine. It is kind of like a handle to the coroutine.

A `Job` represents a coroutine or task that is being performed.
A `Job` is a `CoroutineContext.Element`.

A `CoroutineContext` is a collection of different couroutine context elements.
`Deferred` is also a `Job` so it can be cancelled if needed.

## CPS transformation of suspending functions

```kotlin
suspend fun createPost(token: Token, item: Item): Post { ... }
```

gets converted to JVM side as:
```java
Object createPost(Token token, Item item, Continuation<Post> cont)
```

In detail this is done through state machines