Note: see (inferred) type of a binding with Ctrl + Shft + P.

## Difference from Java

1. functions can be put on the top of a file, no need to put them in classes. This helps in modular and cleaner strucuture and separation of logic from entities.


### All properties must be initialized or lateinit

e.g.
```kotlin
class Some {
  var abc: String // Compiler Error! Value must be initialized or abstractk
  lateinit var p; // This is fine!
}
```

### Reverse for loop

```kt
for (n in 100 downTo 1) {
    println(n)
}
```

## Common Idioms

Function Syntax:

1. functions are defined/declared using "fun" keyword
``` kt
fun double(x: Int): Int {

}
```

2. Function usage, uses traditional approach, no surprises here.
``` kt
val result = double(2)
```

3. Parameters are defined using pascal notation i.e. name: type
``` kt
fun powerOf(number: Int, exponent: Int){

}
```

4. Unit return type, is optional
``` kt
fun printHello(name: String): Unit {
  println("Hi there")
}
```

or

``` kt
fun printHello(name: String) {
  println("Hello htere")
}
```


5. explicit return types: Function with block body
must always specify return types explicitly, unless it is returning
unit. Kotlin does not infer return types for block bodies, since
they can have complex control flow

5. Lambdas/Anonymous functions:
 lambdas start and end with curly braces, 
 parameters (with annotations) are before "->" and 
 lambda body is after "->"(thin arrow function)

 e.g.
 ``` kt
  val sum = { x: Int , y: Int -> x + y }
 ```


6. varArgs

7. Local functions: Kotlin supports functions inside functions

8. Member functions: A member function is a function that is defined
inside a class or object

9. val for immutable variables, var for mutable variables

10. For function to accept other function as parameter, we
have a specify a function type for that parameter.

11. types are non nullable by default, if you want to make a variable
which can hold null value, add a ? at the end
e.g. 
```kt
String? response = getStringResponse();
```
Other use is safe calls.
e.g.
```kt
val l = response?.length
```
In above code, the type of expression is Int? since response?.length 
returns null if response is null, or an int representing length of response

11. types are non nullable by default, if you want to make a variable
which can hold null value, add a ? at the end
e.g. String? response = getStringResponse();

12. Similar to groovy if there is single parameter, you can use it
via "it" it is a implicit parameter.

13. kotlin std lib has higher order functions like map, filter etc.
as extension functions of generic collections

14. if is an expression (and not a statement - statements typically would return unit), meaning it returns a value.
e.g.
``` kt
val max = if (a > b) a else b
```

15. When expression - replaces switch statement, and being an expression returns a value too. The value of the satisified branch becomes the value of the overall expression. 

16. typically when using if-else or when as expressions. all branches must be returning the same type.

17. create POJOs with proper equals, hashcode, toString using "data class"
``` kt
data class Customer(val name: String, val email: String)
```

18. Kotlin class instance creation syntax:
Like python, there is no need of "new" keyword to create instances of a class
``` kt
val invoice = Invoice() // creating instance of class Invoice

val customer = Customer("Joe Smith") // create instance of class Customer
```

18. Kotlin classes have concept of primary and secondary constructors.
Primary constructor is what goes along with class header.
Secondary constructor are prefixed with constructor keyword. 

19. "is" operator for type check + automatic cast:
As soon as "is" keyword is used for checking the type of a (immutable) variable,
the consequenting code has variable typecasted if type matched.
 If an immutable local variable or property is checked for a specific type, there's no need to cast it explicitly
e.g.
``` kt
if (obj is String) {
  // obj is automatically cast to String in this branch
}
```

20. "for in" loop loops over iterable sequences like lists ranges etc.

21. ranges have a concise syntax like in haskell i.e. 1..29
e.g.
``` kt
for (x in 1..5) {
  print (x)
}
```

22. Referential and structural equality
Referential equality is checked with triple equals "===",
and structural + type both equality is checked with double equals "==", 
no need of .equals like java for structural equality

23. Due to representation issues, smaller types are not implicitly converted to bigger types
e.g. Int will not be auto converted to Long, instead use Int.toLong()
e.g. Byte will not be auto converted to Int, instead use Byte.toInt()

24. Number is an important abstract class with following usefule methods
- toByte(): Byte
- toChar(): Char
- toDouble(): Double
- toFloat(): Float
- toInt(): Int
- toLong(): Long

25. Arrays in kotlin are represented by Array class, has get and set functions that turn into [] by operator overloading conventions.

26. Note: **Kotlin Arrays are invariant**

27. kotlin.Any and java.lang.Object are not the same thing, Any has only three methods - equals(), hashCode() and toString(). Every kotlin class has Any as a superclass.

28. When you want to throw null pointer exception, use !! operator, e.g.
```kt
val l = b!!.length
```
The above code will throw NPE if b is null and work as expected if b is not null.

29. Inheritance:
Inheritance should be designed and documented for, and that is the reason all classes are final by default in kotlin. To make classes overridable, they should be prefixed open.

30. For use with android, take a look at: https://kotlinlang.org/docs/tutorials/android-plugin.html

31. Null Safety and platform types(for use with Java): Any reference in Java may be null, which makes Kotlin's requirements of strict null-safety impractical for objects coming from Java. Types of Java declarations are treated specially in Kotlin and called platform types. Null-checks are relaxed for such types, so that safety guarantees for them are the same as in Java.
IDE represents them using:
```
T! means "T or T?"
``` 

32. Kotlin use of data classes with default values
``` kt
data class Artist(
        val id: String,
        val name: String,
        val url: String? = null,
        val bio: String? = null,
        val albums: List<Album>? = null)
```

33. Annotations for "type usage" (which is from java 8) 
go before parameter name (not type name).
e.g.
```kt
public abstract void setTitle(@StringRes int resId) { … }
becomes
setTitle(@StringRes resId: Int): Void
```

