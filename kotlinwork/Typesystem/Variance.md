

## What is variance?

The concept of variance describes how types with the same base type and different type arguments relate to each other: for example, `List<String>` and `List<Any>`.

It helps you create APIs that don’t restrict users in inconvenient ways and don’t break their type-safety expectations.


### Why is `List<String>` not a subtype of `List<Any>`?

Consider function 
```kt
fun addAnswer(list: MutableList<Any>) {
    list.add(42)
}

val ls = mutableListOf("af","Adf")
addAnswer(ls); // Error: Expected MutableList<Any>
```

Solution: Specify type constraint for compile time type checking:
```kt
fun <T: String> addAnswer(list: MutableList<T>) {
    list.add(42); // Error cannot add Int to list of Strings.
}
```

Now you can answer the question of whether it’s safe to pass a list of strings to a function that expects a list of Any objects. **It’s not safe if the function adds or replaces elements in the list, because this creates the possibility of type inconsistencies.**

### How mutability affects variance

**If a function accepts a read-only list, you can pass a List with a more specific element type**

**If the list is mutable, you can’t do that.**

```kt
fun addAnswer(list: List<Any>) {
    list.forEach { println("it = $it") }
}

val ls = mutableListOf("af","Adf")
addAnswer(ls)
```

#### MutableList is invariant, List is co-variant

In java, all classes are invariant. but in kotlin, mutability factors in.


## Co variance

Co-variance means preserved subtyping relation when more types become more composite.
e.g. `A <: B` -> `List<A> <: List<B>`

### Declaring co-variant generic classes in Kotlin - `out` keyword

