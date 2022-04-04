

### Default types are non-nullable in kotlin

e.g. `String` typed variable cannot hold null.

### Type? to specify a nullable type

e.g. 
```kotlin
var abc : String? = null
```

### lateinit var preferred for onetime late initialization

Use the lateinit keyword for the field to avoid needing to declare it nullable. (one time late initialization use case)
Can be used for class member fields or also function level variables.

```kt
class Something{
    lateinit var abc: String
    fun onCreated(){
        abc = '1234';
    }
}
```

### Platform types (Type!)

Any reference in Java may be `null`, which makes Kotlin's requirements of strict null-safety impractical for objects coming from Java. Types of Java declarations are treated in Kotlin in a specific manner and called platform types. Null-checks are relaxed for such types, so that safety guarantees for them are the same as in Java (not strict null safety, and can throw NPEs)

* `T!` means `T` or `T?`,
* **Note: You cannot create directly values of `Type!` in kotlin, only returned at a Java framework/sdk/jar**


### Null checking 

#### Explicit if check for null

Do a null check for `Type?` variables.

```kt
val b: String? = "Kotlin"
if (b != null && b.length > 0) {
    print("String of length ${b.length}")
} else {
    print("Empty string")
}
```

#### Safe call/optional chaining operator (`?.`)

if preceding variable `v` is null `v?.prop` is also `null`,
else the value is `v.prop`.

```kt
val a = "Kotlin"
val b: String? = null
println(b?.length)// null
```

A safe call can also be placed on the left side of an assignment. Then, if one of the receivers in the safe calls chain is null, the assignment is skipped and the expression on the right is not evaluated at all:

```kt
// If either `person` or `person.department` is null, the function is not called:
person?.department?.head = managersPool.getManager()
```

#### Elvis operator (`?:`), with a colon after question mark

Provides a default value for a nullish variable
```kt
var b: String? = null
var j = b ?: "hola" // j is now hola
```
**Similar to nullish coaelescing operator in JS: `??`**

If the expression to the left of `?:` is not `null`, the Elvis operator returns it, 
otherwise it returns the expression to the right.

#### Non-null assertion operator (!!) - May cause NPEs

the not-null assertion operator `(!!)` converts any value to a non-null type 
and throws an exception if the value is null. 

You can write b!!, and this will return a non-null value of b 
(for example, a String in our example) or throw an NPE if b is null

**Note: prone to NullPointerException**
```kt
val l = b!!.length
```

