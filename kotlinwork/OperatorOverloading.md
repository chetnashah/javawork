
**Note** - you can have different types for operand and return type!

## Arithmetic overloads via `plus`, `times`, `mult`

## Type/instance level Equality (==) is actually an operator with following semantics

https://kotlinlang.org/docs/operator-overloading.html#equality-and-inequality-operators

It has compatibility `equals` from JVM i.e. override of `Any`, which is same as override of `Object`

* `a == b` transforms to `a?.equals(b) ?: (b === null)`

* `a != b` transforms to `!(a?.equals(b) ?: (b === null))`

### Reference equality operator (===)

`===` is reference equality operator which checks for same reference.

## Type/instance level index [] operator overload/support

https://kotlinlang.org/docs/operator-overloading.html#indexed-access-operator

* `a[i]` transforms to `a.get(i)`

* `a[i, j]` transforms to `a.get(i, j)`

* `a[i_1, ..., i_n]` transforms to `a.get(i_1, ..., i_n)`

* `a[i] = b` transforms to `a.set(i, b)`

* `a[i, j] = b` transforms to `a.set(i, j, b)`

* `a[i_1, ..., i_n] = b` transforms to `a.set(i_1, ..., i_n, b)`


## type/instance level containment operator `in` overload

Useful for check for interable containment, the bag in which we are searching should be iterable.

`a in c` converts to `c.contains(a)`


## Type/instance level invoke () operator overload

https://kotlinlang.org/docs/operator-overloading.html#invoke-operator

* `a()` transforms to `a.invoke()`

* `a(i)` transforms to `a.invoke(i)`

* `a(i, j)` transforms to `a.invoke(i, j)`

* `a(i_1, ..., i_n)` transforms to `a.invoke(i_1, ..., i_n)`

## Type/instance level comparision support (<,>) with `compareTo operator function`

https://kotlinlang.org/docs/operator-overloading.html#comparison-operators

Internally refers to `compareTo` method of comparable interface.
All calls of `>` are converted to `compareTo` automatically.

`a > b` converts to `a.compareTo(b) > 0`.

**Note** - if your element is `Comparable`, then you do not need to implement `rangeTo`, it is automatically defined as an extension function on `Comparable` interface as following:

```kotlin
operator fun <T: Comparable<T>> T.rangeTo(that: T): ClosedRange<T>
```

Useful utils for comparisions - https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.comparisons/



## Ranges `(A..B)` are not iterable, they are just a start,end pair!

If you want to make your range iterable override following function via extension function defining `.iterator()` on `ClosedRange<MyType>`.

```kt
// defining an iterator for MyType's ClosedRange to help iteration with .. operator
operator fun ClosedRange<MyType>.iterator(): Iterator<MyType> = 
  object: Iterator<MyType> {
    hasNext() { // impl }
    next() { // impl }
  };
```


e.g.
```kt
operator fun ClosedRange<BankAccount>.iterator() = object : Iterator<BankAccount> {
    var curr = start.balance
    override fun hasNext(): Boolean {
       return curr < endInclusive.balance;
    }

    override fun next(): BankAccount {
        val bb = BankAccount()
        bb.deposit(curr+1)
        curr += 1;
        return bb;
    }
}
```

And usage as follows:
```kt
var b1 = BankAccount()
b1.deposit(22.0)

var b2 = BankAccount()
b2.deposit(29.0)

// ClosedRange b1..b2 is iterable, because we defined ClosedRange<BankAccount>.iterator() as an extension function
for(k in b1..b2) {
    println("k = ${k}")
}
```

## Type/instance level `in` operator transforms to contains call

* `a in b` transforms to `b.contains(a)`

* `a !in b` transforms to `!b.contains(a)`


## Type/instance level range support using `a..b` syntax i.e. `..` operator

`a..b` transforms to `a.rangeTo(b)`.

No need to 

## No special bitwise operators

* `shl`—Signed shift left
* `shr`—Signed shift right
* `ushr`—Unsigned shift right
* `and`—Bitwise and
* `or`—Bitwise or
* `xor`—Bitwise xor
* `inv`—Bitwise inversion

## Destructuring is risky, has gotchas, avoid it!

