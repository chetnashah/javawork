
* classes are **public and final by default** (final classes cannot be extended in Java)

* If you intend a class to be a base clase, it needs to be specified as `open` explicitly, meaning all classes are closed for inheritance by default.


## Object instantiation

No **new** keyword
```kotlin
val invoice = Invoice()

val customer = Customer("Joe Smith")
```

## Primary constructor

A class in Kotlin can have a primary constructor and one or more secondary constructors. The primary constructor is a part of the class header, and it goes after the class name and optional type parameters.

**The primary constructor cannot contain any code, only member declarations. Initialization code can be placed in initializer blocks prefixed with the init keyword**
```kotlin
// this kind of looks like JS constructor functions
class Person(firstName: String) { // firstName is a parameter to constructor body, not a property/member on the class
    /*...*/ // class body - only memer declarations allowed, for other code it will complain: Expecting member declaration
    init {
        // custom init code goes here
    }
}
```

Another e.g. with member fun.
```kotlin
fun main() {
	val p = Polygon()
    p.doSomething()
}

 class Polygon {
   fun doSomething(){
          println("blah blah")  
   }
}
```
## Concise init syntax (for property member declarations in the class header arguments itself)

Kotlin has a concise syntax for declaring properties and initializing them from the primary constructor:


```kotlin
class Person(val firstName: String, val lastName: String, var age: Int)
```

## Secondary constructors

A class can also declare secondary constructors, which are prefixed with constructor:

```kotlin
class Pet {
    constructor(owner: Person) {
        owner.pets.add(this) // adds this pet to the list of its owner's pets
    }
}
```

**Note** - If the class has a primary constructor, each secondary constructor needs to delegate to the primary constructor, either directly or indirectly through another secondary constructor(s). Delegation to another constructor of the same class is done using the `this` keyword.

## Inheritance notation

Instead of `extends` keyword, we simply use `:` notation.

```kotlin
open class Base(p: Int)

class Derived(p: Int) : Base(p)
```

## Abstract classes

An abstract class does not have an implementation in its class. 
You cannot create an instance of abstract class.
**No need** to annotate abstract classes or functions with **open** keyword.

```kotlin
abstract class Polygon{
    abstract fun draw()
}

class Rect : Polygon() { //Note braces around Polygon
    override fun draw(){

    }
}
```

It is possible to have all members non-abstract within abstract class:
```kotlin
abstract class Polygon {
    fun draw(): Unit {}
	fun returnTwo(): Int {
		return 2
    }
}
```
