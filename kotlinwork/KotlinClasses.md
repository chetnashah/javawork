
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

A class in Kotlin can have a primary constructor and one or more secondary constructors. **The primary constructor is a part of the class header, and it goes after the class name and optional type parameters.**

**The primary constructor cannot contain any code, only member declarations. Initialization code can be placed in initializer blocks prefixed with the init keyword**
```kotlin
// this kind of looks like JS constructor functions
class Person(firstName: String) { // firstName is a parameter to constructor body, not a property/member on the class
    /*...*/ // class body - only memer declarations allowed, for other code it will complain: Expecting member declaration
    
    val name = "Mr."+firstName // member declarations and assignment expression allowed, but not statements
    init {
        // custom init code goes here
    }

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

## Secondary constructors (helpers constructors in the same class)

The main use case of secondary constructors is overloaded constructors which can have some sensible defaults or extra logic before calling primary constructor.
A class can also declare secondary constructors, which are prefixed with constructor keyword:
Note - You cannot have primary and secondary constructor with same argument list, they must be overloaded with different signature.
```kotlin
class Pet {
    constructor(owner: Person) {
        owner.pets.add(this) // adds this pet to the list of its owner's pets
    }
}
```

**Note** - **`If the class has a primary constructor, each secondary constructor needs to delegate to the primary constructor, either directly or indirectly through another secondary constructor(s) using this`**. Delegation to another constructor of the same class is done using the `this` keyword.

```kotlin
class Abc(val name: String, val surname: String) {
    constructor(singleName: String): this(singleName, singleName) { // secondary constructor delegating to primary constructor

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
## Inheritance notation

Instead of `extends` keyword, we simply use `:` notation, must also call base class constructor in class header

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

## Abstract classes (can have constructors, but cannot be instantiated without being subclassed)

An abstract class does not have an implementation in its class. 
You cannot create an instance of abstract class.
**No need** to annotate abstract classes or functions with **open** keyword.

**Abstract classes can have constructors!**

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

class Rect : Polygon() { //Note braces around Polygon, we are calling parent primary constructor
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

### subclassing a normal class

Since classes are `final` by default, you must add `open` keyword to indicate that a normal class is a candidate for subclassing
all properties or functions must also be declared `open` if they are meant for overriding, and derived class must use `override` keyword to compile.
```kotlin
open class RoundShape(open val radius: Double){ // radius property available for overriding
    val buildingMaterial = "Straw"
    
    fun floorArea(): Double {
        return PI * radius * radius;
    }
    fun calculateMaxCarpetSize():Double {
        val diameter = 2 * radius
        return sqrt(diameter * diameter / 2)
    }
}

class RoundCylinder(override val radius: Double) : RoundShape(radius){

}
```

###  anonymous i.e. temporary/inline class objects (that implement interfaces etc)

**Allows us to create temporary unnamed subclass impl + its object instantiated at the same place.**
Note if it is a SAM interface, you can directly give a lambda and language will convert it to expected interface.

*Note - if we are subclassing interface, specifying interface is fine, If we are subclassing another normal/abstract class with constructor(s), it's constructor must be called in the header*

In other cases temporary class object (that usually extends interfaces or abstract classes) can be made like this:
```kotlin
val tempclickListener = object : View.OnClickListener() {  
    override fun onClick(view: View) {
        // do something on click
    }
}
``` 

## Visibility

### private visibility

All class members (i.e. properties and functions) are public by default.
To make members private, explicitly specify `private` keyword

```kotlin
class Something(private val name: String) // name only accessible inside of class
```

## Properties

In Kotlin, each mutable (`var`) property has `default getter and setter functions automatically generated` for it. 
The setter and getter functions are called when you assign a value or read the value of the property.

For a read-only property (`val`), it differs slightly from a mutable property. 
Only the `getter function is generated` by default. This getter function is called when you read the value of a read-only property.

### What is a property?

The most common kind of property simply reads from (and maybe writes to) a backing field, but custom getters and setters allow you to use properties so one can implement any sort of behavior of a property.

### Fields vs properties

**You cannot directly declared fields. The field identifier can only be used in the accessors of the property.**
fields can be referenced in accessors using the field identifier

```kt
var counter = 0 // the initializer assigns the backing field directly
    set(value) {
        if (value >= 0)
            field = value
            // counter = value // ERROR StackOverflow: Using actual name 'counter' would make setter recursive
    }
```

### Properties syntax

```
var <propertyName>[: <PropertyType>] [= <property_initializer>]
    [<getter>]
    [<setter>]
```

```kt
var initialized = 1 // has type Int, default getter and setter
val simple: Int? // has type Int, default getter, must be initialized in constructor
val inferredType = 1 // has type Int and a default getter
```

e.g.
```kt
var stringRepresentation: String
    get() = this.toString()
    set(value) {
        setDataFromString(value) // parses the string and assigns values to other properties
    }
```

### When is backing field generated?

A backing field will be generated for a property if 
1. it uses the default implementation of at least one of the accessors, or 
2. if a custom accessor references it through the field identifier.

No backing field for this:
```kt
val isEmpty: Boolean // no set accessor because this is a val
    get() = this.size == 0 // this only accessor, does not use field identifier, so no backing field
```

### Why property must be initialized?

Reason behind this is Backing field. 
case 1(`val`): When you create val with custom getter that does not use field identifier to access its value, then backing field is not generated.

```kt
val greeting: String
    get() = "hello"
```

case 1.1(`val` with getter doing `field` access): If you do, then backing field is generated and needs to be initialized.
```kt
val greeting: String // Property must be initialized
    get() = field
```

case 2(`var`): Now with var. Since backing filed is generated by default, it must be initialized, or you can add/implement a setter that does not use `field` identifier.
```kt
var greeting: String // Property must be initialized
    get() = "hello"
```

### Example use case

```kt
class GameViewModel {
    private var _score = 0 // mutable state is local, modifiable only through methods
    val score: Int         // public state is immutable
        get() = _score

    private var _currentWordCount = 0 // mutable state is local, modifiable only through methods
    val currentWordCount: Int         // public state is immutable
        get() = _currentWordCount


    private var _currentScrambledWord = "test" // mutable state is local, modifiable only through methods
    val currentScrambledWord: String           // public state is immutable
        get() = _currentScrambledWord
}
```

## Object expressions can have members/state

e.g.
```kotlin
class DateRange(val start: MyDate, val end: MyDate) : Iterable<MyDate> {
    override fun iterator(): Iterator<MyDate> {
        return object : Iterator<MyDate> {
            var currDate = start // member of this object expression i.e anonymous class
            override fun hasNext(): Boolean {
                return currDate <= end
            }

            override fun next(): MyDate {
                val result = currDate
                currDate = currDate.followingDate()
                return result
            }

        }
    }
}

```

## Operator overloads can also be extension functions on a class/type

```kotlin
operator fun MyDate.plus(timeInterval: TimeInterval): MyDate = TODO()
```

## Objects with invoke method can be invoked as a function

You can add `invoke()` operator to anyy class as an extension, to make it's objects callable.

```kt
operator fun Int.invoke() { println(this) }

1() //huh?..
```