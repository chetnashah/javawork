
Kotlin enums are lot more powerful than Java Enums.

## Declaring enum class

Its not just `enum` it is `enum class`.
Each enum constant is an object of the enum class.

```kt
enum class Direction { // note the extra class keyword after enum
    NORTH, SOUTH, WEST, EAST
}
```

## Useful for modeling exclusive variants

e.g.
```kt
enum class MarsApiStatus { LOADING, ERROR, DONE }
```

## Each enum has a name and an ordinal

By default the name of single constant is same as declared name, but it can be optionally changed

e.g.

```kt
enum class Gender {
    MALE       // ordinal = 0, name = "MALE"
    FEMALE     // ordinal = 1, name = "FEMALE"
}
```
```kt
fun main() {    
    println(Gender.MALE)         // "MALE" : invokes toString() -> which uses name
    println(Gender.MALE.name)    // "MALE"
}
```


## Each enum can have arbitrary number of properties saved, which are set during enum declaration

```kt
enum class Gender(val short: String, val age: Int = 18) {
    MALE("m", 10),
    FEMALE("f", 20)
    BINARY("b")    // age is taken as 18 as default specified in class header declaration
}

fun main() {
    println(Gender.MALE)       // "MALE"
    println(Gender.MALE.name)  // "MALE"
    println(Gender.MALE.short) // "m"
    println(Gender.MALE.age)   // 10
}
```

## Usage in `when` expression

Enum classes help in exhaustive pattern matching using `when` expression.

## Implementing interfaces in enum classess

An enum class can implement an interface (but it cannot derive from a class), providing either a common implementation of interface members for all of the entries, or separate implementations for each entry within its anonymous class.

Example:
```kt
interface IHealth {
    fun getHealth(): Int
}

enum class Gender(val short: String, val age: Int = 18): IHealth {
    MALE("m", 10) {
        override fun getHealth(): Int { // note how each enum is implementing interface inside declaration braces
            return age * 10 - 50;
        }
    },
    FEMALE("f", 20) {
        override fun getHealth(): Int {
            return age * 5 - 50
        }
    },
    BINARY("b") {
        override fun getHealth(): Int {
            return age * 7 - 50
        }
    }
}

fun main() {
    println(Gender.MALE)       // "MALE"
    println(Gender.MALE.name)  // "MALE"
    println(Gender.MALE.short) // "m"
    println(Gender.MALE.age)   // 10

    println(Gender.BINARY)              // "BINARY"
    println(Gender.BINARY.age)          // 18
    println(Gender.BINARY.getHealth())  // 76 = 18*7-50
}
```

## Having functions in enum classes

Dont forget to end the list of constants with semilcolons: `;`
Functions in enum classes can access all the properties in enum class header.
```kt
interface IHealth {
    fun getHealth(): Int
}

enum class Gender(val short: String, val age: Int = 18): IHealth { // the properties can be different per enum-constant
    MALE("m", 10) {
        override fun getHealth(): Int {
            return age * 10 - 50;
        }
    },
    FEMALE("f", 20) {
        override fun getHealth(): Int {
            return age * 5 - 50
        }
    },
    BINARY("b") {
        override fun getHealth(): Int {
            return age * 7 - 50
        }
    }; // don't forget to finish the constants by a semicolon

    // has access to all enum class properties specified in header
    fun greet() {
        println(" Hello " +name + " with age "+age)
    }
}

fun main() {
    println(Gender.MALE)       // "MALE"
    println(Gender.MALE.name)  // "MALE"
    println(Gender.MALE.short) // "m"
    println(Gender.MALE.age)   // 10

    println(Gender.BINARY)
    println(Gender.BINARY.age)
    println(Gender.BINARY.getHealth())

    Gender.FEMALE.greet() // Hello FEMALE with age 20
}
```


## Relation with sealed stuff.

**enum classes can't extend a sealed class (as well as any other class), but they can implement sealed interfaces**