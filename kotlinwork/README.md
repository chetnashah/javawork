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
 lambda body is after "->".

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
e.g. String? response = getStringResponse();


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