

## Notation 

`T : A` or `T extends A` means `T is a subtype of A`. or `T <: A`.


Subtypes are substitutable for super types.

subtypes can override with stronger specs(but not more general specs).

![see this](images/kotlin-type-constraints.png)

all subclasses can be considered subtypes (but subtyping - usually via interfaces, is orthogonal to implementation inheritance - composition). subclassing gives us both implementation re-use and subtyping at the same time.

### Method subtyping rules

A method `A1->A2` is a subtype of method `B1->B2` if, `A1->A2` can be used anywhere `B1->B2` is used.

A subtype method would need to have a more loose/general input type, and much specific return type, so callers are not surprised in terms of sending arguments expectations and return value shape expectations.


## Specifying type constraints

In type parameter angle brackets, use the kotlin convention of `<T : X>` instead of `<T extends X>`

```kt
// only allow subtypes of number
fun <T : Number> 
```


## inline and re-ification

You can declare a function as `inline` so that its type arguments are not erased.
In kotlin terms this is known as `re-ification`.

## Arrays are invariant

Arrays in Kotlin are not built on native types, but are instead based on a Java array. Although these are similar, they do behave slightly differently. In Java, we can assign an array of a type to an array of its parent type. Arrays in Kotlin are invariant, which means that an array of a specific type cannot be assigned to an array of its parent type.


```kotlin
val listOfInts :Array<Int> = arrayOf(1,2) 
val listOfNums: Array<Number> = listOfInts // Compiler ERROR! incompatible types
```

## Lists are covariant


