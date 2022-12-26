

## `use` extension on Closeable (e.g. files)

`use` executes the given block function on the resource and then closes it down correctly whether an exception is thrown or not.

```kt
val file = File("myfile.txt")
file.bufferedReader().use {
    println(it.readText())
}
```