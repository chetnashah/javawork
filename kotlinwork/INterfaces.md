

## Methods

Interfaces in Kotlin can **contain declarations of abstract methods, as well as method implementations**. What makes them different from abstract classes is that **interfaces cannot store a state (non-abstract properties and backing fields)**. 

## Properties

They can have properties, but these **need to be abstract or provide accessor implementations**

## Functional/SAM interfaces

An interface with `single abstract method` is called a functionl/SAM interface.
It can have many non-abstract methods but single abstract method.

### SAM conversions

These are for SAM interfaces, not abstract classes that have single abstract method.
The technique is like this
```kotlin
// there is no need to know the name of the SAM method
val interfaceInstance = InterfaceName { method-body-logic }

// In case the there is a inferred interface type, then even specifying the interfaceName is not needed
calculateButton.setOnClickListener { method-body-logic-for-click-event } // just passing lambda is enough, the expected View.OnClickListener is a Single Abstract Method Interface
```

**Wherever a SAM instance is expected, you can just pass a lambda**, e.g. in onClickListner, or any callback based SAM interfaces.

This feature is very useful for single listener callbacks e.g.
```kotlin
interface OnCoffeeBrewedListener {
    fun onCoffeeBrewed(coffee: Coffee)
}
```
Anothr e.g.
```kotlin
fun interface IntPredicate {
   fun accept(i: Int): Boolean
}
```

Without sam conversion, you can pass anonymous object like following
```kotlin
val isEven = object : IntePredicate {
    fun accept(i: Int): Boolean {
        return i % 2 == 0
    }
}
```

A simplar creation of interface object using lambda
```kotlin
val isEven = IntPredicate { it % 2 == 0 }
```

Another example is of `Runnable`, where the single abstract method is `run`:
```kotlin
val runnable = Runnable { println("hello") }
```
