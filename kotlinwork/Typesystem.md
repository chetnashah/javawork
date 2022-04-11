

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
