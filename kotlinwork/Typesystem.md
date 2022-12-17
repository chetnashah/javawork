


### No distinction between primitive and reference types

### no impliciti conversion between primitives.

In java there is implicit conversion from int to long, because of no loss of precision.
But in kotlin you have to be explicit.
```kt
Int.toLong(1)
```

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

```kt
val x: String = y as String


```

## Safe nullable cast operator `as?`

**returns null on failure, so receiving type must be nullable**
Note that despite the fact that the right-hand side of `as?` is a non-null type `String`, 

the result of the cast is nullable.

```kt
val x: String? = y as? String
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

## Type level Equality (==) is actually an operator with following semantics

https://kotlinlang.org/docs/operator-overloading.html#equality-and-inequality-operators

* `a == b` transforms to `a?.equals(b) ?: (b === null)`

* `a != b` transforms to `!(a?.equals(b) ?: (b === null))`

## Type level index [] operator overload/support

https://kotlinlang.org/docs/operator-overloading.html#indexed-access-operator

* `a[i]` transforms to `a.get(i)`

* `a[i, j]` transforms to `a.get(i, j)`

* `a[i_1, ..., i_n]` transforms to `a.get(i_1, ..., i_n)`

* `a[i] = b` transforms to `a.set(i, b)`

* `a[i, j] = b` transforms to `a.set(i, j, b)`

* `a[i_1, ..., i_n] = b` transforms to `a.set(i_1, ..., i_n, b)`


## Type level invoke () operator overload

https://kotlinlang.org/docs/operator-overloading.html#invoke-operator

* `a()` transforms to `a.invoke()`

* `a(i)` transforms to `a.invoke(i)`

* `a(i, j)` transforms to `a.invoke(i, j)`

* `a(i_1, ..., i_n)` transforms to `a.invoke(i_1, ..., i_n)`

## Type level comparision support (<,>) with `compareTo operator function`

https://kotlinlang.org/docs/operator-overloading.html#comparison-operators


