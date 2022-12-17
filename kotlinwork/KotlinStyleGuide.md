

https://developer.android.com/kotlin/style-guide

## Rename on imports is supported

When you import a class or a function, you can specify a different name for it by adding as NewName after the import directive. It can be useful if you want to use two classes or functions with similar names from different libraries.

e.g.
```kt
 import kotlin.random.Random as KRandom
 import java.util.Random as JRandom

fun useDifferentRandomClasses(): String {
    return "Kotlin random: " +
             KRandom.nextInt(2) +
            " Java random:" +
             JRandom().nextInt(2) +
            "."
}
```

## Properties

When a property initializer does not fit on a single line, break after the equals sign (=) and use an indent.

```kt
private val defaultCharset: Charset? =
    EncodingRegistry.getInstance().getDefaultCharsetForPropertiesFiles(file)
```
Properties declaring a get and/or set function should place each on their own line with a normal indent (+4). Format them using the same rules as functions.

```kt
var directory: File? = null
    set(value) {
        // …
    }
```

read only properties if fit on single line can be used directly
```kt
val defaultExtension: String get() = "kt"
```

## Annotations

Member or type annotations are placed on separate lines immediately prior to the annotated construct.
```kt
@Retention(SOURCE)
@Target(FUNCTION, PROPERTY_SETTER, FIELD)
annotation class Global
```

When only a single annotation without arguments is present, it may be placed on the same line as the declaration.
```kt
@Volatile var disposable: Disposable? = null

@Test fun selectAll() {
    // …
}
```

## Implicit property/return types

Can omit types for property/return values if they are scalars

**When writing a library, retain the explicit type declaration when it is part of the public API.**

```kt
override fun toString(): String = "Hey"
// becomes
override fun toString() = "Hey"

private val ICON: Icon = IconLoader.getIcon("/icons/kotlin.png")
// becomes
private val ICON = IconLoader.getIcon("/icons/kotlin.png")
```

## Naming

### Package names
Package names are all lowercase.

### Type/Interface names

Class/Interface names are written in PascalCase.

### function names

Function names are written in `camelCase` and are typically verbs or verb phrases. 
For example, `sendMessage` or `stop`.

Exception (Components):
Functions annotated with `@Composable` that return Unit are `PascalCased` and named as nouns, 
as if they were types.
e.g.
```kt
@Composable
fun NameTag(name: String) {
    // …
}
```

### Backing properties

When a backing property is needed, its name should exactly match that of the real property except prefixed with an underscore.

```kt
private var _table: Map? = null // backing property of table

val table: Map
    get() {
        if (_table == null) {
            _table = HashMap()
        }
        return _table ?: throw AssertionError()
    }
```

### Type variables

Each type variable is named in one of two styles:

* A single capital letter, optionally followed by a single numeral (such as `E`, `T`, `X`, `T2`)
* A name in the form used for classes, followed by the capital letter T (such as `RequestT`, `FooBarT`)
