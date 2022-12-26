
a `field` (property storage area) is only used as a part of a property to hold its value in memory. Fields cannot be declared directly.

This backing `field` can be referenced in the accessors using the `field` identifier for stored property use cases

## property with custom setter



```kt
class PropertyExample() {
    var counter = 0
    var propertyWithCounter: Int? = null
        set(value) {
            counter++;
            field = value;
        }
        get() = field
}
```