
Primary library to do Rx stuff with Java.

`RxAndroid` is addon library that provides with Android specific utils
like scheduler to schedule work on android main thread.

To work with `Retrofit` refer Retrofit RxJava adapter at 
`https://github.com/square/retrofit/tree/master/retrofit-adapters/rxjava2`.

### RxAndroid

`AndroidSchedulers` is a part of `RxAndroid`.

### Scheduling on android

1. `Schedulers.io()`: A thread pool executor for stuff like network request, db stuff.
2. `Schedulers.computation()`: for long running calculations.
3. `Schedulers.newThread()`: Spawn new thread for all tasks.
4. `Schedulers.from(Executor executor)`: A scheduler backed by give executor.
5. `AndroidSchedulers.mainThread()`: Schedules on mainthread.

`subscribeOn()` instructs the source `Observable` which thread to emit items on — this thread will push the emissions all the way to our `Observer`. However, if it encounters an `observeOn()` anywhere in the chain, it will switch and pass emissions using that `Scheduler` for the remaining (downstream) operations

### Flowable

`Flowable` is typically used when an `Observable` is emitting huge amounts of data but the `Observer` is not able to handle this data emission. This is known as Back Pressure.

there is no concept of backpressure in `Observables` anymore, and no way to handle it. If you're designing a reactive sequence that will probably require explicit backpressure handling - then `Flowable` is your best choice.

### Error handling

Strategies:

you might:

1. swallow the error and switch over to a backup Observable to continue the sequence
2. swallow the error and emit a default item
3. swallow the error and immediately try to restart the failed Observable
4. swallow the error and try to restart the failed Observable after some back-off interval.

To do these some of the functions are `onErrorReturn`, `onErrorResumeNext`,
`onExceptionResumeNext`.

### onNext vs doOnNext

`doOnNext` is for side-effects: you want to react (eg. log) to item emissions in an intermediate step of your stream, for example before the stream is filtered, for transverse behavior like logging, but you still want the value to propagate down the stream.

`onNext` is more final, it consumes the value.

