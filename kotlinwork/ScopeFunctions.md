
## Resources

https://kotlinlang.org/docs/scope-functions.html

Five types:

1.`with`
2. `run`
3. `let`
4. `apply`
5. `also`



## why scope functions?

Execute a block of code within scope of an object.
When you call such a function on an object with a lambda expression provided, 
it forms a temporary scope. 

In this scope, you can access the object members like properties and functions without its name.

`with`, `let`, `run` return lambda result

## Usage table

https://kotlinlang.org/docs/scope-functions.html#function-selection

## With

General syntax is `with(instanceObj) { lambdacode }`

```kotlin
val numbers = mutableListOf("one", "two", "three")
with(numbers) {
    val firstItem = first()
    val lastItem = last()        
    println("First item: $firstItem, last item: $lastItem")
}
```
## run

## let

## apply

## also