

**`Array` is invariant.**
This means that Kotlin does not let us assign an `Array<String>` to an `Array<Any>`, which prevents a possible runtime failure (but you can use `Array<out Any>`)

## `Array<Int>` vs `IntArray`

`Array<Int>` compiles to `Integer[]`, but `IntArray` compiles to `int[]` i.e. primitive array of ints.

## Useful technique to print underlying representation

```kt
println(arr::class.java.simpleName);
```

## Ways to create Array

### arrayOf and primitiveArrayOf methods

Similar to `listOf` methods which return lists, we have arrayOf methods which return Arrays.

```kt
val arr = intArrayOf(1,2,3)
println(iss::class.java.simpleName) // int[]
```

`arrayOf` will create boxed version of primitive arrays, instead of primitive arrays, 
```kt
var is2: Array<Int> = arrayOf(1,2,23);
println(is2::class.java.simpleName);// Integer[]
```

**Note** - to create primitive array versions (non-boxed), use `primitiveArrayOf` method e.g. `intArrayOf`, regular `arrayOf` will return auto-boxed versions.

## Array to List and vice-versa

### Array to List

All Arrays will have a `arr.toList()` method. Autoboxing happens by default.

### List to primitiveArray (Use list.toPrimitiveArray())

`List` has useful extension method `toIntArray` to convert from `List` -> `IntArray`.

### List to Array (Boxed)

**Note** - The method is `toTypedArray`!

`List` has a useful extension method `toTypedArray()` to convert from `List` -> `Array`.

