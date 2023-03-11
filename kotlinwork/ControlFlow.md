

**Control flow operators are also useful in type narrowing**

## If is both statement and an expression

`if` expression returns a value.

## when is like switch case with super powers

when without an argument will checkk for boolean true in the case branches. first true branch wins, use `else` for catch-all.

```kotlin
fun main() {
    println("Is it going to rain?") val probability = 70
    val text = when {
        probability < 40 -> "Na-ha" 
        probability <= 80 -> "Likely" 
        probability < 100 -> "Yes" 
        else -> "Holly Crab"
    }
    println(text)
}
```

`when` keyword when provided with an value argument, can help in checking:
1. value via equality
2. type via `is` operator
3. containment via `in` operator


Use `else` for catch-all cases
e.g.
```kotlin
fun describe(a: Any?) { 
    when (a) {
        null -> println("Nothing")
        1, 2, 3 -> println("Small number")
        in magicNumbers -> println("Magic number")
        in 4..100 -> println("Big number")
        is String -> println("This is just $a")
        is Long, is Int -> println("This is Int or Long") else -> println("No idea, really")
    } 
}
```


### What if more than one cases match in when expression?

**The first match from top to bottom wins.**

```kotlin
fun main() {
    describe(1) // is Integer
    describe(null) // is null
}

fun describe(v: Any?): Unit {
    when(v) {
        null -> println("is null")
        is Int -> println(" is Integer")
        1,2,3 -> println("small number")
    }
}
```
