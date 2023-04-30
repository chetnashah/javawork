
## You cannot use return keyword inside a lambda!

**A bare return is forbidden inside a lambda because a lambda cannot make the enclosing function return**



```kt
fun ordinaryFunction(block: () -> Unit) {
    println("hi!")
}

fun foo() {
    ordinaryFunction {
        return // ERROR: cannot make `foo` return here
    }
}

fun main() {
    foo()
}
```

To return from lambdas use the label of function that invoked lambda:
```kt
fun foo() {
    ordinaryFunction {
        return@ordinaryFunction // ERROR: cannot make `foo` return here
    }
}
```


**Note** - if you inline functions accepting lambdas, then return inside lambda body is allowed
e.g.
```kt
inline fun ordinaryFunction(block: () -> Unit) {
    println("hi!")
}

fun foo() {
    ordinaryFunction {
        return // ERROR: cannot make `foo` return here
    }
}
```

## Qualified return

Qualified returns allow us to return from an outer function.


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

ints.filter {
    val shouldFilter = it > 0
    shouldFilter
}
```

If lambda is the only argument in the call, the parentheses can be omitted entirely
```kt
run { println("...") }
```


## Inline functions

https://kotlinlang.org/docs/inline-functions.html

Inline functions are unique because they are replaced at the callsite

### Performance improvements via inlining functions that accept lambdas

If your function takes lambdas as arguments, then they usually create anonymous objects to hold lambdas.
If you inline function that takes lambdas, the callsite can be replaced with function including the lambda, improving performance

**The inline modifier affects both the function itself and the lambdas passed to it: all of those will be inlined into the call site.**

Consider `lock` function that takes a lock obj and a lambda containing body around which to lock.


```kt
class LockIt {
    fun lock() {
        println("Locking lockit")
    }
    fun unLock() {
        println("Unlocking lockit")
    }
}

inline fun lockMe(lock: LockIt, body : () -> Unit) {
    lock.lock();
    body();
    lock.unLock();
}
```

We see inliing happen with lambdas as follows:
```kt
val l: LockIt = LockIt();
lockMe(l, { println("Hello body!")})

// converts to inline calling 
LockIt l = new LockIt();
l.lock();
String var5 = "Hello body!";
System.out.println(var5);
l.unLock();
```

### Re-ification of type parameters supported for inline functions

Since the inline functions is inline at compile time, the actual type parameter is known as compile time,
and can be inlined as an actual class instance instead of generic type.

