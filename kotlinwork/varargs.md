
Only one parameter can be marked as vararg and is usually the last parameter in the list.

```kt
class Vegetables(vararg val toppings: String) : Item("Vegetables", 5) {
    override fun toString(): String {
        return name + " " + toppings.joinToString()
    }
}
```