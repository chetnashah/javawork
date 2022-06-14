
## Declaring enum class

Its not just `enum` it is `enum class`.
Each enum constant is an object of the enum class.

```kt
enum class Direction { // note the extra class keyword after enum
    NORTH, SOUTH, WEST, EAST
}
```

## Useful for modeling exclusive variants

e.g.
```kt
enum class MarsApiStatus { LOADING, ERROR, DONE }
```

## Implementing interfaces in enum classess

An enum class can implement an interface (but it cannot derive from a class), providing either a common implementation of interface members for all of the entries, or separate implementations for each entry within its anonymous class.