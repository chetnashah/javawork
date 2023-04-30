
### Type parameters

Kotlin always requires type arguments to be either specified explicitly or inferred by the compiler

### Any and Any?: the root types

* `Any` is the super type of all non-nullable types, including primitives like `Int`. Since `Any` is a reference type, autoboxing from `Int` to `Integer` will happen when assigning to variable with type `Any`.
* `Any?` is the super type of all types!
* Under the hood, `Any` type corresponds to `java.lang.Object`

### Unit type: Java's void in Kotlin

`Unit` is a full fledged type, and unlike `void`, it can be used as type parameter.
Functions that do not return anything, implicitly return `Unit`.

Here is a good usecase":
```kt
interface<T> Processor{
    fun process(): T
}

class NoResultProcessor: Processor<Unit> {
    fun process() {
        // do stuff
        // no need to return anything
    }
}
```

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

`Nothing` type can be used as a return type for a function that always throws an exception (never returns). 

When you call such a function, the compiler uses the information that the execution doesn't continue beyond the function.

## Platform types and java interop

Types defined in Java code are seen as platform types. Kotlin does not have nullability info of these platform types.

**Compiler allows platform types to be treated as either nullable or non-null**

* Java primitive types like `int` etc become non-null types `Int` because they cannot hold null values.
By the same reasoning, nullable types in kotlin like `Int?` cannot be stored with java primitive types, because Java primitive types cannot hold null values. That means when you use nullable version of primitive type in Kotlin, it compiles down to corresponding wrapper type like `Integer` in Java.

e.g.
```kt
fun abc() {
    var j : Int? = null;
    var k: Int = 1;
}
```
gets compiled to following Java code
```java
public static final void abc() {
  Integer j = null;
  int k = 1;
}
```

### Boxed primitives types will be used for collections

E.g. following list will used boxed types i.e. `Int` compiles to `Integer` internally for int primitives inside a collection.
```kt
val list = listOf(1,2,3);
```


## Array is an explicit type in Kotlin

it has a type parameter i.e. `Array<T>`.

To represent arrays of primitive types, Kotlin provides a number of separate classes, one for each primitive type. For example, an array of values of type `Int` is called `IntArray`. 

For other types, Kotlin provides `ByteArray`, `CharArray`, `Boolean-Array`, and so on. All of these types are compiled to regular Java primitive type arrays, such as `int[]`, `byte[]`, `char[]`, and so on

## Is it possible to use type arguments in `is` checks?

No, due to type erasure. Ues `*` as type parameter
```kt
    val ls: List<Int> = listOf();
    if(ls is List<String>) { // Error!: Cannot check for instance of erased type: List<String>
        return 0.0;
    }
    if(ls is List<*String*>) { // Correct!
        return 0.0;
    }

```

## Explicitly passing type parameters

```kt
fun <T> myfun() {
    // do something
}

myFun<String>(); // explicitly passing type parameter T = String
```