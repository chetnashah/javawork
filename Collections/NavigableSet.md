

`NavigableSet` extends `SortedSet`:

Useful for sorted collections like `TreeSet` and `TreeMap` which can query element positions in O(lg N).

Following important methods:

1. `floor(E e)` - Return greatest element in set less than or equal to given element

2. `ceiling(E e)` - Return least element greater than or equal to given element

3. `lower(E e)` - return greatest element in this set strictly less than given element

4. `higher(E e)` - return least element strictly higher than given element.

```java
		TreeSet set = new TreeSet<Integer>();
		set.add(4);
		set.add(5);
		set.add(5);
		set.add(7);
		set.add(8);
		set.add(25);

        // 4 5 7 8 25

		System.out.println(set.ceiling(5)); // 5
		System.out.println(set.ceiling(6)); // 7

		System.out.println(set.higher(5)); // 7
		System.out.println(set.higher(6)); // 7

```

## Methods inherited from SortedSet

1. `first()` - give smallest element in this set.

2. `last()` - return largest element in this set.