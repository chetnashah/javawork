
## Resources

https://developer.android.com/kotlin/flow


## Flow sounds a lot like Observable

A alternative successor to LiveData.

a flow is a type that can emit multiple values sequentially, as opposed to suspend functions that return only a single value. 
For example, you can use a flow to receive live updates from a database.

Flows are built on top of coroutines and can provide multiple values. 
**A flow is conceptually a stream of data that can be computed asynchronously.**

## Type of flow

The emitted values must be of the same type. For example, a `Flow<Int>` is a flow that emits integer values.

## Creating flows

To create flows, use the `flow builder APIs`.

