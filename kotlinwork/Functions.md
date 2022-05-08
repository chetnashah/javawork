

## named parameters support (allows arbitrary order of passing parameters)

with named parameters calling, you can pass parameters in arbitrary order

e.g.
```kotlin
fun main() {
	doingSomething(num = 11, msg = "hello")
}

fun doingSomething(msg: String, num: Int) {
    println(" msg = $msg, num = $num")
}
```

## invoke support

Objects with the `invoke()` method can be invoked as a function.

e.g.
```kotlin
operator fun Int.invoke() { println(this) }

1() // prints 1
```