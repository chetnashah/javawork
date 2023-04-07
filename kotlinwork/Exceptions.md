
## try/catch is an expression

```kotlin
fun main(){
    val v = readLine()?.toInt() // enter a alphabetstring to throw NumberFormatException

    val v2 = try {
        readLine()?.toInt() 
    } catch(e: NumberFormatException) {
        println(e)
        0 // returned from this block as expression return value
    } finally {

    }
}
```

## Custom exceptions

```kotlin
class MyCustomException : Exception("My custom exception") // Note the exception constructor being called

class Abc(name:String) {
    init {
        if(name == "") {
            throw MyCustomException()
        }
    }
}
```

## Throws method declaration and custom exception
```kt
class BankAccount {
    var balance = 0.0
        private set

    fun deposit(depositAmount: Double) {
        balance += depositAmount;
    }

    @Throws(InsufficientFunds::class)
    fun withdraw(withdrawAmount: Double) {
        if (balance < withdrawAmount) {
            throw InsufficientFunds()
        }
        balance -= withdrawAmount;
    }
}

class InsufficientFunds: Exception()
```