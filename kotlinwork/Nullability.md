

**auto assignment to null never happens, null init/assignment must also be explicit!**

### Default types are non-nullable in kotlin

e.g. `String` typed variable cannot hold null.

### Type? to specify a nullable type

e.g. 
```kotlin
var abc : String? = null
```


### you can have nullable/optional variables declared with val, but it makes more sense with var (if lifetime updates)

`val` is good for single assignment (does not have to be immediate initializzer). `var` is good for multiple assignments

```kt
fun main() {    
    val k: String?; 
    val j: String;
    j = "hey";
    k = null; // First assignment is ok!
    // k = "hi"; // Error!
    
    var j: String?; // can reassign many times, if varaible lifetime is dynamic, go with var
    j = null;
    j = "hey";
}
```

### lateinit var preferred for onetime late initialization of non-null values

Use the lateinit keyword for the field to avoid needing to declare it nullable. (one time late initialization use case). This is useful when you don’t want to initialize a variable immediately but want to avoid null checks when accessing it later.
Can be used for class member fields or also function level variables.

```kt
class Something{
    lateinit var abc: String
    fun onCreated(){
        abc = '1234';
    }
}
```

**Note** - `lateinit` cannot be used with primitives e.g.
```kt
lateinit var abc: Int // compiler error - 'lateinit' modifier is not allowed on properties of primitive types
```

### Platform types (Type!)

Any reference in Java may be `null`, which makes Kotlin's requirements of strict null-safety impractical for objects coming from Java. Types of Java declarations are treated in Kotlin in a specific manner and called platform types. Null-checks are relaxed for such types, so that safety guarantees for them are the same as in Java (not strict null safety, and can throw NPEs)

* `T!` means `T` or `T?`,
* **Note: You cannot create directly values of `Type!` in kotlin, only returned at a Java framework/sdk/jar**

### lateinit var - initialized late once, but non-null once initialized

Use case: where real initialization happens after construction and its impossible to provide any value at all of the right type before the initialization method is called.

e.g.
On Android, I can’t have an Activity member variable of type View without lateinit. It’s impossible to create a View without the Context that is provided to `onCreate`.  I just can’t create any value at all to use as a placeholder until `onCreate` is called.

#### lateinit with val is not allowed

If I try `lateinit val abc`: I will get following:
```
'lateinit' modifier is allowed only on mutable local variables
```

#### lateinit with nullable types is not allowed e.g. `String?`

I will get error:
`'lateinit' modifier is not allowed on local variables of nullable types`

```kt
fun main() {
    lateinit var kk: Int? // ERROR!: 'lateinit' modifier is not allowed on local variables of nullable types

    kk = 11
    println(kk)
}
```
Why?
https://discuss.kotlinlang.org/t/why-cant-one-use-lateinit-with-nullable-types/19992
https://gyurigrell.com/2018/08/lateinit-modifier-is-not-allowed/

* What if I try to access lateinit variable before init?

A lateinit variable in Kotlin is a variable that is not initialized when it is declared. It must be initialized before it is used. If you try to use a lateinit variable before initializing it, you will get a `UninitializedPropertyAccessException exception`.

**The default value of a lateinit variable in Kotlin is not defined**

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

#### Safe call/optional chaining operator (`?.`) (Similar to one found in Javascript)

if preceding variable `v` is null `v?.prop` is also `null`,
else the value is `v.prop`.

```kt
val a = "Kotlin"
val b: String? = null
println(b?.length)// null
```

**A safe call can also be placed on the left side of an assignment.** Then, if one of the receivers in the safe calls chain is null, the assignment is skipped and the expression on the right is not evaluated at all:

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
**Same as nullish coaelescing operator in JS and Swift: `??`**

If the expression to the left of `?:` is not `null`, the Elvis operator returns it, 
otherwise it returns the expression to the right.

`return` and `throw` with elvis operator:
```
fun doSomething(a: Int?) {
    var b = a ?: throw SomeException("Expected a to be defined")
}

fun doSomething2(a: Int?) {
    var b = a ?: return // early return
}
```

#### Non-null assertion operator (!!) - May cause NPEs

the not-null assertion operator `(!!)` converts any value to a non-null type 
and throws an exception if the value is null. 

You can write b!!, and this will return a non-null value of b 
(for example, a String in our example) or throw an NPE if b is null

**Note: prone to NullPointerException**
```kt
val l = b!!.length
```

## Conditional execution with `?.let`

`arguments: Bundle?`, so it must be either surrounded by null check or `?.let` with a lambda where nullable variable can be referred via `it`.

```kotlin
override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)

    arguments?.let {
        letterId = it.getString(LETTER).toString()
    }
}
```

### Lazy types
Represents a value with lazy initialization.
To create an instance of `Lazy`, use the `lazy` function that takes a initializer lambda.

```kt
public interface Lazy<out T> {
    /**
     * Gets the lazily initialized value of the current Lazy instance.
     * Once the value was initialized it must not change during the rest of lifetime of this Lazy instance.
     */
    public val value: T

    /**
     * Returns `true` if a value for this Lazy instance has been already initialized, and `false` otherwise.
     * Once this function has returned `true` it stays `true` for the rest of lifetime of this Lazy instance.
     */
    public fun isInitialized(): Boolean
}

// delegate/extension for get on lazy type, that unwraps the underlying value on get access
public inline operator fun <T> Lazy<T>.getValue(thisRef: Any?, property: KProperty<*>): T = value
```

#### How lazy lambda works

```kt
/**
 * Creates a new instance of the [Lazy] that uses the specified initialization function [initializer]
 * and the default thread-safety mode [LazyThreadSafetyMode.SYNCHRONIZED].
 *
 * If the initialization of a value throws an exception, it will attempt to reinitialize the value at next access.
 *
 * Note that the returned instance uses itself to synchronize on. Do not synchronize from external code on
 * the returned instance as it may cause accidental deadlock. Also this behavior can be changed in the future.
 */
public actual fun <T> lazy(initializer: () -> T): Lazy<T> = SynchronizedLazyImpl(initializer)
```

```kt
private class SynchronizedLazyImpl<out T>(initializer: () -> T, lock: Any? = null) : Lazy<T>, Serializable {
    private var initializer: (() -> T)? = initializer
    @Volatile private var _value: Any? = UNINITIALIZED_VALUE
    // final field is required to enable safe publication of constructed instance
    private val lock = lock ?: this

    override val value: T
        get() {
            val _v1 = _value
            if (_v1 !== UNINITIALIZED_VALUE) {
                @Suppress("UNCHECKED_CAST")
                return _v1 as T
            }

            return synchronized(lock) {
                val _v2 = _value
                if (_v2 !== UNINITIALIZED_VALUE) {
                    @Suppress("UNCHECKED_CAST") (_v2 as T)
                } else {
                    val typedValue = initializer!!()
                    _value = typedValue
                    initializer = null
                    typedValue
                }
            }
        }

    override fun isInitialized(): Boolean = _value !== UNINITIALIZED_VALUE

    override fun toString(): String = if (isInitialized()) value.toString() else "Lazy value not initialized yet."

    private fun writeReplace(): Any = InitializedLazyImpl(value)
}
```


## Least resttrictive to most restrictive variable declarations

```kt
lateinit var abc: String? // can change once assigned, but cannot use before assignment
lateinit var abc: String // can change to non-null values once assigned, but cannot use before assignment 
// all below must be assigned/initialized in scope before use
// auto assignment to null never happens, null init/assignment must also be explicit!
var abc: String? // both variable and value can change be null
var abc: String // variable can change but held value cannot be null
val abc: String? // one time assignment, can be null or non-null
val abc: String // one time non-null assignment
```


## Nullability of type parameters

Type parameter `T` always includes nullable also, even without `?`.
So by default `T` is inferred as `Any?`, if you want to restrict to non-null explicitly add upper bound of `Any`.
```kotlin
// default inference: T = Any?
fun <T> printHashCode(t: T) {
    println(t?.hashCode())
}
printHashCode(null) // ok

// see upper bound of T is non-nullable Any
fun <T : Any> printHashCode2(t: T) {
    println(t.hashCode())
}
```

## Extensions for nullable types

**You can have extensions of nullable types** where you can chck if `this` is null or not.

An interesting side effect: No need of safe call, the receiver extension will take care of check `this` being null or not.

There are some usecasese 

```
// receiver is nullable, inside it check for this is null or not!
fun String?.isBlankOrNull() {
    if(this == null || this == "") {
        return true;
    }
    return false;
}

var str: String? = null
if(str.isBlankOrNull()) { // Note no need of safe call
    println("str was null")
}
```