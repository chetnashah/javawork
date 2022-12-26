
## All classes implicitly inherit from **Any**

```kt
class Example // Implicitly inherits from Any
```

`Any` has three methods:
1. `equals()`
2. `hashCode()`
3. `toString()`


## All classes are **final** by default

**By default, Kotlin classes are final, to make them extendable make it `open class Base`**

## Construction of base/derived classes

Always: **derived class must be initialized first (constructors, member init included)**

If derived class has a primary constructor, **base class must be initialized in that primary constructor according to its parameters**

```kt
class Derived(p: Int): Base(p)
```

If derived class has no primary constructor, secondary constructors must delegate to base constructors,

```kt
class MyView: View {
    constructor(ctx: Context): super(ctx) // delegate to superclass constructor first
    constructor(ctx: Context, attrs:AttributeSet) : super(ctx, attrs)
}
```

Another normal case: no constructor in derived, but present in base.

```kt
fun main() {
    val d = Derived()
}

open class Base(p: Int) {
    init { println("Base init")}
}

class Derived : Base(2) {
    init { println (" derived init: hi heloo")}
    val k = 2
}
```