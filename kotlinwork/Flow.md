
Comes via `kotlinx.coroutines.flow.Flow`

`Flow`s only exist in context of `coroutines`, and do not make sense outside of coroutines.

Conceptually a stream of data whose values can be computed asynchronously..
Similar to observable.

Setup observing via `collect`:

```kt
uiScope.launch {
    dataStream().collect {// run this lambda on each dataStream emit
        updateUI(it)
    }
}
```

## Flow on android

https://developer.android.com/kotlin/flow

## Basic type: `Flow<T>`

## flow coroutine builder function

## Common problem

The problem is that the `List<Schedule>` is returned from each of the DAO functions only once. Even if the underlying data is updated, `submitList()` won't be called to update the UI, and from the user's perspective, it will look like nothing has changed.

`Think reactive database`: allow the DAO to continuously emit data from the database.

## Collect values using `collect`

Using a function called `collect()`, you can call `submitList()` using the new value emitted from the flow so that your ListAdapter can update the UI based on the new data.

Usage :
```kt
        lifecycle.coroutineScope.launch {
            viewModel.fullSchedule().collect {// fullSchedule() returns a Flow<List<Schedule>>, it is List<Schedule>
                busStopAdapter.submitList(it)
            }
        }
```