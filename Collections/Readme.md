
## Collections.sort is in-place stable sort for any collections

https://docs.oracle.com/javase/7/docs/api/java/util/Collections.html#sort(java.util.List)

## Java 8 way of converting Integer List to int array

```java
int[] arr = list.stream().mapToInt(i -> i).toArray();
```

## Create ArrayList from array

For primitive array to ArrayList: `no good option exists`
```java
// For primitives we must manually create boxed versions
// and add
List<Integer> l = new ArrayList<>();
for(int i=0;i<arr.length;i++) {
	l.add(arr[i]);
}
```

For array of objects there is a simpler way using `Arrays.asList(T[] as)`
```java
List<String> ls = new ArrayList<>(Arrays.asList(as));// fresh arraylist with contents copied from passed in array
```

**Note** - The `java.util.Arrays.asList(T... a)` **returns a fixed-size list backed by the specified array(Changes to the returned list "write through" to the original array.)**. To get fresh separate allocation, see example above. 

```java
import java.util.*;
class Main {
	public static void main(String[] args) {
		String[] array = {"abc", "2", "10", "0"};
		List<String> list = Arrays.asList(array);
		Collections.sort(list);// sorting this list sorts the backing array
		System.out.println(Arrays.toString(array));// [0, 10, 2, abc]
	}
}
```

## Create array from ArrayList

1. first create an empty array of same size & type
2. pass this new array inside `toArray` method.

```java
ArrayList<String> sList = new ArrayList<>();
sList.add("hey");
sList.add("hi");
sList.add("hello");
String[] sArr = new String[sList.size()];
sList.toArray(sArr); // when sArr is same size as sList, all items are copied over to sAr, the passed arr is also used for type inference
```

There are two variations of `toArray` method:
1. `toArray()` - Returns an array containing all of the elements in this list in proper sequence (from first to last element). The returned array will be "safe" in that no references to it are maintained by this list. (In other words, this method must allocate a new array). The caller is thus free to modify the returned array.
2. `toArray(T[] a)` - Returns an array containing all of the elements in this list in proper sequence (from first to last element); the runtime type of the returned array is that of the specified array. If the list fits in the specified array, it is returned therein. Otherwise, a new array is allocated with the runtime type of the specified array and the size of this list.



## `Arrays.sort` Sorting and primitives

Sorting of an array of primitive values with a custom comparator is not supported by the standard Java libraries.

Java's library doesn't provide a sort function for ints with comparators (comparators can be used only with objects).

The `Arrays.sort(T[], Comparator)` is a generic method where both the first and second parameters use the type variable. Since primitives can't be used as generic type arguments, ex. `Comparator<int>` not possible, you cannot pass an `int[]` as the first argument.

You will need to pass an `Integer[]`.


## TreeMap only allows one key (adding same key with different value will override previous value)

you cannot have multiple keys for that you need MultiMap (e.g. from Guava library).
Adding multiple key, it will override value for previously added key.

Alternatively maintain list of values per key.
