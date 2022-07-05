
## Collections.sort is in-place stable sort for any collections

https://docs.oracle.com/javase/7/docs/api/java/util/Collections.html#sort(java.util.List)

## Java 8 way of converting Integer List to int array

```java
int[] arr = list.stream().mapToInt(i -> i).toArray();
```

## Create ArrayList from array

```java
new ArrayList<>(Arrays.asList(array));
```