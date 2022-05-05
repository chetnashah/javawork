
## classes can be simply nested (default behvaior is static inner classes of java)

`Nested` is a static class nested inside `Outer` class
So basically `OUter` only serves as a namespace for convinience and Inner will **not have access to Outer's state**. 

```kotlin
class Outer {
    private val bar: Int = 1
    class Nested { // cannot access Outer state in here, since this is a static inner class
        fun foo() = 2
    }
}

val demo = Outer.Nested().foo() // == 2
```

## All combinations of class and interface nesting are possible and fine

```kotlin
interface OuterInterface {
    class InnerClass
    interface InnerInterface
}

class OuterClass {
    class InnerClass
    interface InnerInterface
}
```

## inner classes

A nested class **marked as inner can access the members of its outer class**. Inner classes carry a reference to an object of an outer class.

```kotlin
class Outer {
    private val bar: Int = 1
    inner class Inner {
        fun foo() = bar
    }
}

val demo = Outer().Inner().foo() // == 1
```

