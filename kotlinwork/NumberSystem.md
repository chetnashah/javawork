

## Booleans

Another basic type is Boolean, which has two possible values: true and false.
```kotlin
fun main() {
    val b1: Boolean = true 
    println(b1) // true 
    val b2: Boolean = false 
    println(b2) // false
}
```

## Charachter

We use `Char` type for single charachter

Each character is represented as a Unicode number. 
To find out the Unicode of a character, use the code property

```kotlin
fun main() { 
    println('A'.code) // 65
}
```

## Number class has all the conversion functions

```kt
abstract class Number {
    abstract fun toDouble(): Double 
    abstract fun toFloat(): Float 
    abstract fun toLong(): Long 
    abstract fun toInt(): Int 
    abstract fun toChar(): Char 
    abstract fun toShort(): Short 
    abstract fun toByte(): Byte
}
```

This means that for each basic number you can transform it into a different basic number using the to{new type} function. Such functions are known as **conversion functions.**

```kt
fun main() {
    val b: Byte = 123
    val l: Long = b.toLong() 
    val f: Float = l.toFloat() 
    val i: Int = f.toInt()
    val d: Double = i.toDouble() 
    println(d) // 123.0
}
```


## Bitwise operations with names

```kt
fun main() {
    println(0b0101 and 0b0001) // 1, that is 0b0001 
    println(0b0101 or 0b0001) // 5, that is 0b0101 
    println(0b0101 xor 0b0001) // 4, that is 0b0100 
    println(0b0101 shl 1) // 10, that is 0b1010 
    println(0b0101 shr 1) // 2, that is 0b0010
}
```

## Large Numbers

For unlimited sized numbers - `BigInteger`
For unlimited sized + precision numbers - `BigDecimal`