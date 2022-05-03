
* classes are **public and final by default** (final classes cannot be extended in Java)

* If you intend a class to be a base clase, it needs to be specified as `open` explicitly, meaning all classes are closed for inheritance by default.


## Object instantiation

No **new** keyword
```kotlin
val invoice = Invoice()

val customer = Customer("Joe Smith")
```

`Why two kinds of constructors?`
After this logic applying we get major rule: to cover the most frequent class initializing scenarios you have to have primary constructor with ability to define default parameter values. Additionally you should have ability to create secondary constructors to cover all other scenarios.

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

Note- it is possible to have a class with no primary constructor, but secondary constructor(s)
```kotlin
class Abc {
    lateinit var jj: String
    constructor(k: String) {
        jj = k
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

Note- it is possible to have a class with no primary constructor, but secondary constructor(s)
```kotlin
class Abc {
    lateinit var jj: String
    constructor(k: String) {
        jj = k
    }
}
```
## Inheritance notation

Instead of `extends` keyword, we simply use `:` notation.

```kotlin
open class Base(p: Int)

class Derived(p: Int) : Base(p)
```

**If the derived class has a primary constructor, the base class can (and must) be initialized in that primary constructor according to its parameters.**

If the derived class has no primary constructor, then each secondary constructor has to initialize the base type using the super keyword
```kotlin
class MyView : View {
    constructor(ctx: Context) : super(ctx)
}
```

## Abstract classes

An abstract class does not have an implementation in its class. 
You cannot create an instance of abstract class.
**No need** to annotate abstract classes or functions with **open** keyword.

Unlike Java, abstract classes in kotlin are allowed to mark properties abstract.
```kotlin
abstract class Polygon{
    abstract val X: Int    // abstract properties
    val fixednum = 11      // non abstract properties
    
    // property with initalizer cannot be abstract
    // abstract val abc = 22 // Compiler error! Property with initalizer cannot be abstract!!!


    // methods: both abstract and non-abstract allowed
    abstract fun draw()    // abstract methods
	fun returnTwo(): Int { // non abstract methods
		return 2
    }
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

}
```

### derived classes need to re-declare all abstract members with override keyword

e.g. following **does not work**
```kotlin
class SquareCabin: Dwelling(2){ 
    init { // Compiler error! Class 'SquareCabin' is not abstract and does not implement abstract base class member public abstract val buildingMaterial: String defined in Dwelling
        buildingMaterial = ""
        capacity = 2
    }
}

abstract class Dwelling(private var residents: Int){
    abstract val buildingMaterial: String
    abstract val capacity: Int
	fun hasRoom(): Boolean {
        return residents < capacity
    }
}
```

ONe needs to **re-declare members with override keyword** in derived class




## Visibility

### private visibility

All class members (i.e. properties and functions) are public by default.
To make members private, explicitly specify `private` keyword

```kotlin
class Something(private val name: String) // name only accessible inside of class
```
