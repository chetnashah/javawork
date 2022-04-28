
## Kotlin Stdlib `thread` helper function

`import kotlin.concurrent.thread`

```kt
fun thread(
    start: Boolean = true,
    isDaemon: Boolean = false,
    contextClassLoader: ClassLoader? = null,
    name: String? = null,
    priority: Int = -1,
    block: () -> Unit
): Thread
```
This is a stdlib function that directly runs the given lambda which is part of `block` variable in the definition mentioned above.
**The thread will be started as soon as created.**

**Note** - you don't need to call `start()`, because as you see above, the default value of `start` is true, and can be turned off if necessary. 

```kt
fun main(){
    println("Main running! from thread name = ${Thread.currentThread().name}")// "main"
    thread {
        println("Running from a separate thread name = ${Thread.currentThread().name}")// "thread-0"
    }
}
```