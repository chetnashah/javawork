

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

Do runtime type checks with `is` operator.
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

## Unsafe cast using `as` infix operator

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

