
To access the information about the type of class, we use a keyword called reified.

**In order to use the reified type, we need to use the inline function**

```kt
inline fun <reified T> genericsExample(value: T) {
    println(value)
    println("Type of T: ${T::class.java}")
}
fun main() {
    genericsExample<String>("Learning Generics!")
    genericsExample<Int>(100)
}
```

Generic return types:

```kt
inline fun<reified T> showMessage(marks: Int): T {
    return when (T::class) {
        Int::class -> marks as T
        String::class -> "Congratulations! you scored more than 90%" as T
        else -> "Please enter valid type" as T
    }
}
fun main() {
    val intMarks: Int = showMessage(70) // returning integer value
    val stringMessage: String = showMessage(95) // returning string value
    println("Your marks: $intMarks \nMessage: $stringMessage")
}
```