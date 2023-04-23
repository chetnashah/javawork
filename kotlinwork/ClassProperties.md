
a `field` (property storage area) is only used as a part of a property to hold its value in memory. Fields cannot be declared directly.

This backing `field` can be referenced in the accessors using the `field` identifier for stored property use cases

## property with custom setter

**If you define a custom setter, it will be called every time you assign a value to the property, except its initialization**

```kt
class PropertyExample() {
    var counter = 0
    // property with backing data
    var propertyWithCounter: Int? = null
        set(value) {
            counter++;
            field = value;
        }
        get() = field

    // property without backing data
    var totalCounter
        get() = counter + propertyWithCounter
}
```

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
class Delegate {
    operator fun setValue(v: Type) {}
    operator fun getValue() {}
}
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

## val can only have getter, var can have both getter/setter

## properties with getters cannot be smart casted (type narrowed)

Since getter might result in any value on every invocation, the control flow cannot be narrowed down by if guards, and smart casts cannot be done on properties with getters

**You cannot use if/else etc with properties defined by getters!**

```kt
class Person {
    val name: String? = "Marton"
    val surname: String = "braun"

    val fullName: String?
        get() = name?.let { "$it $surname"}
    val fullName2: String? = name?.let { "$it $surname" }

    fun tryNames(){

        if (fullName != null) {
            // Smart cast to 'String' is impossible, because 'fullName' is a property that has open or custom getter
            println(fullName.length) // not possible because getter may return null here and break cast
        }
        if(fullName2 != null) {
            println(fullName2.length)
        }
    }
}
```