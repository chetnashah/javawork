
Kotlin provides the ability to **extend a class or an interface** with new functionality without having to inherit from the class or use design patterns such as Decorator. This is done via special declarations called extensions.

## Extension Functions

To declare an extension function, prefix function name with a `receiver type: which refers to the type being extended` e.g. 
e.g. `fun List<Int>.myFunction() { ... }` or `fun Int.isPrime(): Boolean { ... }`
* **The `this` keyword inside an extension function corresponds to the receiver object (the one that is passed before the dot).**

Add `swap` method to `MutableList` class:

```kt
fun MutableList<Int>.swap(index1: Int, index2: Int) {
    val tmp = this[index1] // 'this' corresponds to the list
    this[index1] = this[index2]
    this[index2] = tmp
}
```

## Extension properties

Kotlin supports extension properties much like it supports functions.

## Extensions are resolved statically

Extensions do not actually modify the classes they extend. 

By defining an extension, you are not inserting new members into a class, only making new functions callable with the dot-notation on variables of this type.

## Companion object extensions

If a class has a companion object defined, you can also define extension functions and properties for the companion object. Just like regular members of the companion object, they can be called using only the class name as the qualifier.

TODO: add examples

## function literals with receivers

Same as extension functions, but we can have arbitrary name assigned to the literal and use that later as a extended function, instead of a fix name
```kt
val sum = fun Int.(other: Int): Int = this + other // sum will be a function on Int type.
println(sum(2,3))
println(2.sum(3))
```

Another example:
```kt
val isEven: Int.() -> Boolean = { this % 2 == 0 }
val isOdd: Int.() -> Boolean = { this % 2 == 1 }

return listOf(42.isOdd(), 239.isOdd(), 294823098.isEven())
```


## Pattern: creating builders with function literal with receivers
https://kotlinlang.org/docs/type-safe-builders.html
This is like groovy style DSL closures for building/configuring objects

Implicit receiver rock DSL creation
One of the most significant feature of Kotlin for designing DSLs is the ability to pass implicit receivers to lambda expressions—see Implicit Receivers. This feature singlehandedly sets Kotlin apart from other statically typed languages for the ability to attain fluency in DSLs. And the receivers are a great way to pass a context object between layers of code in DSL.

Here’s a little DSL to place an order for some office supplies:
```kt
placeOrder { // this lambda will be passed to placeOrder
 // placeOrder is available as this in this lambda
  an item "Pencil"  // all calls are implicitly done on "this" to build it
  an item "Eraser"
  complete {
    this with creditcard number "1234-5678-1234-5678"
  } 
}

// builder fun gets a build-configuration lambda as a arg
fun placeOrder(desc: Order.() -> Order): Order {
    var order = Order() // create receiver obect
    order.desc()        // function literal with param name does its magic by build object in steps as per lambda
    return order;       // return fully configured object
}
```
In this code, there’s a context of an order and a context of a payment, but the code may need both to perform the payment transaction. That’s not an issue thanks to implicit receivers.

In the execution of each lambda expression, there’s an implicit receiver that carries the context—a thread of conversation—forward. This makes it easy to carry the state forward for processing between the layers of code without the need to pass parameters or maintain global state. 

One more example:
```kt
fun <K,V> buildMutableMap(desc: MutableMap<K,V>.() -> Unit): Map<K,V> { // lambda taken as a function literal with receiver, and we will configure a bare Map using this lambda, and the bare object is passed to lambda for configuration
    val mutMap = mutableMapOf<K,V>()
    mutMap.desc()
    return mutMap
}


fun usage(): Map<Int, String> {
    return buildMutableMap { // configure map creation by given lambda
        put(0, "0")
        for (i in 1..10) {
            put(i, "$i")
        }
    }
}

```