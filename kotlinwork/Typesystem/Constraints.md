
## Upper bound on type parameter by colon with type parameter `:` 
Constraints are defined by specifying an upper bound after a type parameter.
```kt
fun <T: Number> List<T> sum(): T
```

## Type constraints

To specify a constraint, you put a colon `:` after the type parameter name.

In java we use `extends` keyword to represent same concept.

e.g.
`<T extends Number> T sum(List<T> list)` is equivalent to `fun <T : Number> List<T>.sum(): T`

function call is allowed on `T = Int` because `Int extends Number`.

### Usecase for type constraints: using Comparables

```kt
fun <T: Comparable<T>> getMax(first: T, second: T): T {
    return if (first > second) first else second;
}
```

### Non nullability type constraint

Usng `<T: Any>` ensures non-nullable type as type parameter. Default `<T>` is equivalent to `<Any?>`

