
Kotlin provides the ability to **extend a class or an interface** with new functionality without having to inherit from the class or use design patterns such as Decorator. This is done via special declarations called extensions.

## Extension Functions

To declare an extension function, prefix function name with a `receiver type: which refers to the type being extended`

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

