
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

## val can only have getter, var can have both getter/setter

