

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

## Why do we have named/default parameters for function?

Default and named arguments help to minimize the number of overloads and improve the readability of the function invocation.

### Default argument values

Imagine you have several overloads of 'foo()' in Java:
```java
public String foo(String name, int number, boolean toUpperCase) {
    return (toUpperCase ? name.toUpperCase() : name) + number;
}
public String foo(String name, int number) {
    return foo(name, number, false);
}
public String foo(String name, boolean toUpperCase) {
    return foo(name, 42, toUpperCase);
}
public String foo(String name) {
    return foo(name, 42);
}
```

**You can replace all these Java overloads with one function in Kotlin.**
i.e.
```kotlin
fun foo(name: String, number: Int = 42, toUpperCase: Boolean = false) =
        (if (toUpperCase) name.uppercase() else name) + number
```


## Kotlin has trailing lambdas (just like swift)

If a lambda/anonymous function is last argument of a function, it can be moved out of function call

e.g.
```kt
val itemPricesDoubled = itemPrices.map { itemPrice -> itemPrice * 2 }
```

If lambda is the only argument in the call, the parentheses can be omitted entirely
```kt
run { println("...") }
```
