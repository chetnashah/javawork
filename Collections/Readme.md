
## Collections.sort is in-place stable sort for any collections

https://docs.oracle.com/javase/7/docs/api/java/util/Collections.html#sort(java.util.List)

## Java 8 way of converting Integer List to int array

```java
int[] arr = list.stream().mapToInt(i -> i).toArray();
```

## Create ArrayList from array

```java
new ArrayList<>(Arrays.asList(array));// new fresh arraylist with similar contents as array, does not modify the original array
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