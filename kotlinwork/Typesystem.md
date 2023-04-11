


### No distinction between primitive and reference types

### no impliciti conversion between primitives.

In java there is implicit conversion from int to long, because of no loss of precision.
But in kotlin you have to be explicit.
```kt
Int.toLong(1)
```

### No type inference for function return type, better be explicit about function return type

`why?`

Return type must be provided in two cases:

* function is public or protected, i. e. it is part of API, and you don't want it's signature to change implicitly;
* function has block body, and it may be hard to infer return types by compiler and human reading the code :)


### By default classes are not extensible (use `open` keyword)


### By default methods are not polymorphic

### Reflection
Not a part of default language.
You have to add platform specific library dependency.

## Runtime Type checks (& type narrowing) with `is` operator

Do runtime type checks with `is` operator which check if an objects confirms to a type.
```kt
if (obj is String) {
    print(obj.length)
}
```

`is` narrows type when used with if condition.
e.g.
```kt
fun demo(x: Any) {
    if (x is String) {
        print(x.length) // x is automatically cast to String
    }
}
```

`is` operator can also be used inside `when` expressions:
```kt
when (x) {
    is Int -> print(x + 1)
    is String -> print(x.length + 1)
    is IntArray -> print(x.sum())
}
```

This kind of typechecks can be considered equivalent to pattern matching/variant processing

## Unsafe cast using `as` infix operator

Usually, **the `as` cast operator throws an exception if the cast isn't possible. And so, it's called unsafe.** The unsafe cast in Kotlin is done by the infix operator `as`.

We also call this down cast. **Upcasting is casting to a supertype, while downcasting is casting to a subtype**. Think of type hierarchy with general types on top, and specific types on the bottom.

**Upcasting is implicit (by the nature of liskov substitution principle), but downcast is unsafe/explicit using as operator.**

```kt
var i: Number = 123
fun main() {
    // Explicit Downcast, we know i is already Int, otherwise we would have ClassCastException
    val j = (i as Int) + 10 println(j) // 133
}
```

Implicit upcast example
```
val j = 11;
val t: Number = j; // Implicit upcast from Int to Number
```

## Safe nullable cast operator `as?`

Safer alternative to `as` operator, as **you get null return value instead of classcastexception.**

**returns null on failure to cast, so receiving type must be nullable**
Note that despite the fact that the right-hand side of `as?` is a non-null type `String`, 

**the result of the cast is nullable**

```kt
var n: Number = 123
fun main() {
    val i: Int? = n as? Int // Successful cast, return as is 
    println(i) // 123

    val d: Double? = n as? Double // unsuccessful cast, return null
    println(d) // null
}
```

Not having receiving type as nullable will cause error, even if there was guarantee of types
```kt
fun main() {
    var j = "987"
	var k: String = j as? String // Error: Type mismatch: inferred type is String? but String was expected
    println(k)
}
```

## Type erasure

https://kotlinlang.org/docs/typecasts.html#type-erasure-and-generic-type-checks

## Nothing type

`Nothing` type can be used as a return type for a function that always throws an exception. 

When you call such a function, the compiler uses the information that the execution doesn't continue beyond the function.

## Type/instance level Equality (==) is actually an operator with following semantics

https://kotlinlang.org/docs/operator-overloading.html#equality-and-inequality-operators

* `a == b` transforms to `a?.equals(b) ?: (b === null)`

* `a != b` transforms to `!(a?.equals(b) ?: (b === null))`

## Type/instance level index [] operator overload/support

https://kotlinlang.org/docs/operator-overloading.html#indexed-access-operator

* `a[i]` transforms to `a.get(i)`

* `a[i, j]` transforms to `a.get(i, j)`

* `a[i_1, ..., i_n]` transforms to `a.get(i_1, ..., i_n)`

* `a[i] = b` transforms to `a.set(i, b)`

* `a[i, j] = b` transforms to `a.set(i, j, b)`

* `a[i_1, ..., i_n] = b` transforms to `a.set(i_1, ..., i_n, b)`


## Type/instance level invoke () operator overload

https://kotlinlang.org/docs/operator-overloading.html#invoke-operator

* `a()` transforms to `a.invoke()`

* `a(i)` transforms to `a.invoke(i)`

* `a(i, j)` transforms to `a.invoke(i, j)`

* `a(i_1, ..., i_n)` transforms to `a.invoke(i_1, ..., i_n)`

## Type/instance level comparision support (<,>) with `compareTo operator function`

https://kotlinlang.org/docs/operator-overloading.html#comparison-operators


## Type/instance level `in` operator transforms to contains call

* `a in b` transforms to `b.contains(a)`

* `a !in b` transforms to `!b.contains(a)`


## Type/instance level range support using `a..b` syntax i.e. `..` operator

`a..b` transforms to `a.rangeTo(b)`

`
