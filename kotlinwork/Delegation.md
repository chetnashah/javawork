
Zero boilerplate needed in kotlin.

https://www.youtube.com/watch?v=MfJB-JhRAoQ

## prop by Delegate()

**Property Delegation** using `by` statement, all access of get/set to property are forwarded to `Delegate`

The `Delegate` object must have following `operator fun` methods:
1. `setValue` 
2. `getValue`

These can be methods or extensions.

This is how it works behind the scenes, compiler generates a hidden property with 

```kt
class MyClass {
    val prop: Type by Delegate()
}
```
gets converted to following:
```kt
class MyClass {
    private val delegate = Delegate()
    val prop: Type
        get() {
            delegate.getValue()
        }
        set(v: Type) {
            delegate.setValue(v)
        }
}
```

## Supports inheritance delegation Derived -> Base without any boiler plate code

### Need a delegate interface and a delegate object

### `by` clause in supertype list

Given an interface `Base`:
```kt
interface Base {
    fun print()
}
```

and a base class implementation `BaseImpl`:
```kt
class BaseImpl(val x: Int): Base {
    override fun print(){
        print(x)
    }
}
```

We cannot delegate directly to subclass, but only via interface.

e.g.
```kt
class SomeClass(b: BaseImpl): BaseImpl by b // Error! Only interfaces can be delegated to
```


**Only delegation via interfaces is allowed, not directly via classes**
```kt
// here b is also called the delegate object
class SomeClass(b: Base) : Base by b // delegate everything to b instance, which is guarantted to fulfill Base interface 

fun main() {
    val b = BaseImpl(10)
    SomeClass(b).print()// you need to give the delegate object which implements the delegate interface
}
```

Common usage:

Or if you dont want to have arguments, and create a new impl instance on the fly:
```kt
class SomeClass(): Base by BaseImpl() // tells to delegate all non-implemented in SomeClass which are part of Base interface to be forwarded to BaseImpl
```

Android usage example
```kt
class Activity: ComponentActivity, AnalyticsLogger by AnalyticsLoggerImpl() {

}
```

## Lifetime of a delegate

Lifetime of delegate object is same as that of a field in the class that is delegating

## Overriding delegated methods works as expected: dervied overrides of methods are given preference


## Edge case : Overriden properties in Derived class 

Consider following case:
Property is overriden in derived class, that is supposed to be accessed by a base object method,
but does not work.
```kt
interface Base {
    val message: String
    fun print()
}

class BaseImpl(val x: Int) : Base {
    override val message = "BaseImpl: x = $x"
    override fun print() { println(message) }
}

class Derived(b: Base) : Base by b {
    // This property is not accessed from b's implementation of `print`
    override val message = "Message of Derived"
}

fun main() {
    val b = BaseImpl(10)
    val derived = Derived(b)
    derived.print()
    println(derived.message)
}
```

## Enter delegated properties

https://kotlinlang.org/docs/delegated-properties.html

The syntax is: `val/var <property name>: <Type> by <expression>` -> `expression` object implements `getValue`/`setValue` methods.

```kt
import kotlin.reflect.KProperty

// delegate obj blue print
class Delegate {
    operator fun getValue(thisRef: Any?, property: KProperty<*>): String {
        return "$thisRef, thank you for delegating '${property.name}' to me!"
    }

    operator fun setValue(thisRef: Any?, property: KProperty<*>, value: String) {
        println("$value has been assigned to '${property.name}' in $thisRef.")
    }
}

class Example {
    var p: String by Delegate() // delegate p's get/set to Delegate() instance, which implements getValue/setValue
}

fun main() {
	val e = Example()
	println(e.p)
    e.p = "NEW"
    println(e.p)
}

// Output:
// Example@736e9adb, thank you for delegating 'p' to me!
// NEW has been assigned to 'p' in Example@736e9adb.
// Example@736e9adb, thank you for delegating 'p' to me!
```

The **expression after by is a delegate,** because the `get()` (and `set()`) that correspond to the property will be delegated to its `getValue()` and `setValue()` methods.

**Property delegates don’t have to implement an interface**, but they have to provide a `getValue()` function (and `setValue()` for vars).


## Standard delegates

### Lazy properties

`lazy()` is a function that takes a lambda and returns an instance of `Lazy<T>`, which can serve as a delegate for implementing a lazy property i.e. `Lazy<T> implements getValue/setValue methods, getValue delegating to lazy lambda`. 

The first call to `get()` executes the lambda passed to `lazy()` and remembers the result. Subsequent calls to `get()` simply return the remembered result.

```kt
fun main() {
	val l by lazy {
        println("computing first time")
        42
    }
    println(l)
    println(l)
}
```

`Note`: By default, the **evaluation of lazy properties is synchronized**: the value is computed only in one thread, but all threads will see the same value.



## Easily achieve observable properties by builtin Delegates.Observable

https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.properties/-delegates/observable.html

```kt
var observed = false
var max: Int by Delegates.observable(0) { property, oldValue, newValue ->
    observed = true
}

println(max) // 0
println("observed is ${observed}") // false

max = 10
println(max) // 10
println("observed is ${observed}") // true
```

It's usage in a real class
```kt
class Person(
    val name: String, age: Int, salary: Int
) : PropertyChangeAware() {

    private val observer = {
        prop: KProperty<*>, oldValue: Int, newValue: Int ->
        changeSupport.firePropertyChange(prop.name, oldValue, newValue)
    }

    var age: Int by Delegates.observable(age, observer)
    var salary: Int by Delegates.observable(salary, observer)
}
```