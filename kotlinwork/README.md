Function Syntax:

1. functions are defined using fun keyword
```
fun double(x: Int): Int {

}
```

2. Function usage, uses traditional approach, no surprises here.
```
val result = double(2)
```

3. Parameters are defined using pascal notation i.e. name: type
```
fun powerOf(number: Int, exponent: Int){

}
```

4. Unit return type, is optional
```
fun printHello(name: String): Unit {
  println("Hi there")
}
```

or

```
fun printHello(name: String) {
  println("Hello htere")
}
```

5. explicit return types: Function with block body
must always specify return types explicitly, unless it is returning
unit. Kotlin does not infer return types for block bodies, since
they can have complex control flow

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


11. types are non nullable by default, if you want to make a variable
which can hold null value, add a ? at the end
e.g. String? response = getStringResponse();


11. types are non nullable by default, if you want to make a variable
which can hold null value, add a ? at the end
e.g. String? response = getStringResponse();


11. types are non nullable by default, if you want to make a variable
which can hold null value, add a ? at the end
e.g. String? response = getStringResponse();


11. types are non nullable by default, if you want to make a variable
which can hold null value, add a ? at the end
e.g. String? response = getStringResponse();


11. types are non nullable by default, if you want to make a variable
which can hold null value, add a ? at the end
e.g. String? response = getStringResponse();


11. types are non nullable by default, if you want to make a variable
which can hold null value, add a ? at the end
e.g. String? response = getStringResponse();


11. types are non nullable by default, if you want to make a variable
which can hold null value, add a ? at the end
e.g. String? response = getStringResponse();


11. types are non nullable by default, if you want to make a variable
which can hold null value, add a ? at the end
e.g. String? response = getStringResponse();


11. types are non nullable by default, if you want to make a variable
which can hold null value, add a ? at the end
e.g. String? response = getStringResponse();


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
