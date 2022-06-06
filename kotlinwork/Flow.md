
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

## Basic type: `Flow<T>`

## flow coroutine builder function