

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


