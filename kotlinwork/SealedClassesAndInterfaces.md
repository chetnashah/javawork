

## Use for sealed classes and interfaces

**Represent a restricted class hierarchy.**

**A sealed class is abstract by itself, it cannot be instantiated directly and can have abstract members**

All direct subclasses of a sealed class are known at compile time.
third-party clients can't extend your sealed class in their code.

once a module with a sealed class or sealed interface is compiled, no new implementations can appear.

## Difference from enum classes

Enum class instances are singletons, sealed class subclasses are dedicated classses which can have instance objects with their own state.

### Example use cases

1. contain error classes to let the library users handle errors that it can throw.

e.g.
```kt
sealed interface Error

sealed class IOError(): Error

class FileReadError(val file: File): IOError()
class DatabaseError(val source: DataSource): IOError()

object RuntimeError : Error
```

## Since sealed/classes interface are exhaustive, else branch is not needed in `when` based pattern matching

```kt
fun eval(expr: Expr): Int =
        when (expr) {
            is Num -> expr.value
            is Sum -> eval(expr.left) + eval(expr.right)
        }

sealed interface Expr
class Num(val value: Int) : Expr
class Sum(val left: Expr, val right: Expr) : Expr
```


In java/kotlin, the sum-types are emulated via sub-classing.