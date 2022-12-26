

## Sequence

https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.sequences/-sequence/

A sequence that returns values through its iterator. The values are evaluated lazily, and the sequence is potentially infinite.

Sequences can be iterated multiple times, however some sequence implementations might constrain themselves to be iterated only once. That is mentioned specifically in their documentation (e.g. generateSequence overload). The latter sequences throw an exception on an attempt to iterate them the second time.

## Grouping

https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-grouping/

Represents a source of elements with a `keyOf` function, which can be applied to each element to get its key.

A `Grouping` structure serves as an intermediate step in `group-and-fold` operations: they group elements by their keys and then fold each group with some aggregating operation.

It is created by attaching `keySelector: (T) -> K` function to a source of elements. To get an instance of Grouping use one of **groupingBy extension functions**

* Iterable.groupingBy
* Sequence.groupingBy
* Array.groupingBy
* CharSequence.groupingBy

Extension functions: https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-grouping/#extension-functions

## For loop is inclusive of end index

```kt
for (i in 0..5) {
        println(i) // 0,1,2,3,4,5   --> upto 5
}
```

## Iterating an array with index

```kt
for ((index, value) in array.withIndex()) {
}
```

## Sort by indexing into another array

```kt
/**
    Input: names = ["Mary","John","Emma"], heights = [180,165,170]
    Output: ["Mary","Emma","John"]
    Explanation: Mary is the tallest, followed by Emma and John.
*/
fun sortPeople(names: Array<String>, heights: IntArray): Unit {
    val sortedArray = names.withIndex().sortedBy {
        heights[it.index]
    }
    val ans = sortedArray.map { it.value }
}
```

## Counting down

```kt
// 100 downTo 0
for (i in 100 toward 0) {
    // Do things
}
```


## IntArray has `.size` and not `.length`


## Ranges using `a..b` and complemented by `in` operator for containment checking

**Ranges can be used for any data type that is Comparable**

`..` is an operator function with name `rangeTo(that)` which is **called on the start instance**, and given the value of `end`. 

`Range<T>` requires `<T : Comparable>`, i.e. it needs parameters to be comparable. 

`rangeTo()` is complemented by `in` or `!in` functions, to check containment, which invokes `.contains()` method on range object.

**Note** - `Range` can be created from **any Comparable** via `C1..C2`, e.g. proof:
```kt
public operator fun <T : Comparable<T>> T.rangeTo(that: T): ClosedRange<T> = ComparableRange(this, that)
// rangeTo is invoked via `C1..C2`
```
e.g. Making `MyDate` comparable will allow us to use `..` and `in` operator on `MyDate` automatically.
```kt
data class MyDate(val year: Int, val month: Int, val dayOfMonth: Int) : Comparable<MyDate> {
    override fun compareTo(other: MyDate): Int {
        if (year != other.year) return year - other.year
        if (month != other.month) return month - other.month
        return dayOfMonth - other.dayOfMonth
    }
}
```

```kt
operator fun <T : Comparable<T>> T.rangeTo(
    that: T
): ClosedRange<T>
```


## Useful extensions

### Convert List to Set

`list.toSet()`

### Convert Set to List

`set.toList()`