

A collection of methods.

All methods are `public abstract` by default.

## Java 8 changes

### Adding static method to interfaces

**you can define static methods in interfaces (as of Java 8)**

If you add static methods, then programmers would regard them as utility methods, not as essential, core methods.

This makes it easier for you to organize helper methods in your libraries; you can keep static methods specific to an interface in the same interface rather than in a separate class.

https://stackoverflow.com/questions/512877/why-cant-i-define-a-static-method-in-a-java-interface

Why static method override is not supported? It doesn't make sense.
**There is no point in "overriding" a static method since one can always specify the class that contains the desired version**

```java
public interface TimeClient {
    // ...
    // treat this as util method
    static public ZoneId getZoneId (String zoneString) {
        try {
            return ZoneId.of(zoneString);
        } catch (DateTimeException e) {
            System.err.println("Invalid time zone: " + zoneString +
                "; using default time zone instead.");
            return ZoneId.systemDefault();
        }
    }

    default public ZonedDateTime getZonedDateTime(String zoneString) {
        return ZonedDateTime.of(getLocalDateTime(), getZoneId(zoneString));
    }    
}
```

## default implementation

When you extend an interface that contains a default method, you can do the following:

1. Not mention the default method at all, which lets your extended interface inherit the default method.
2. Redeclare the default method, which makes it abstract.
3. Redefine the default method, which overrides it.

Default methods enable you to add new functionality to existing interfaces and ensure binary compatibility with code written for older versions of those interfaces. 

**In particular, default methods enable you to add methods that accept lambda expressions as parameters to existing interfaces.**

![def methods](images/defaultmethods.png)

### Calling default method that is overriden in class via `InterfaceName.super.defaultMethodName()`

A class can override a default interface method and call the original method by using `InterfaceName.super.defMethodName()`
**Note - plain `super.defaultMethodName()` results in an error!, you must use `InterfaceName.super.defaultMethodName()` or directly `defaultMethodName()` if method name is unique**

e.g.
```java
// interface defines a default method
interface A {
    default void foo() {
        System.out.println("A.foo");
    }
}

class B implements A {
    public void afoo() {
        // how to invoke A.foo() here?
        A.super.foo();
    }
}
```

Another example (with method overriding):
```java
interface One {
    default void method() {
        System.out.println("One");
    }
}

interface Two {
    default void method () {
        System.out.println("One");
    }
}

class Three implements One, Two {
    @Override  // since we override only way to access is InterfaceName.super.defaultMethod()
    public void method() {
        One.super.method(); // invoke interface default method via `InterfaceName.super.defaultMethod()`
    }
}
```

## Functional interface / lambdas

A `Functional Interface` is an interface with `Single Abstract Method`.

A functional interface is any interface that contains only one abstract method. (A functional interface may contain one or more default methods or static methods.) Because a functional interface contains only one abstract method, you can omit the name of that method when you implement it. 

e.g.
```java
interface Predicate<T> {
    boolean test(T t);
}
```

## Why is `Interface Comparator<T>` a functional interface event though it has two abstract methods: `equals` and `compare`?

If an interface declares an abstract method overriding one of the public methods of `java.lang.Object` i.e. `equals`, that also does not count toward the interface's abstract method count since any implementation of the interface will have an implementation from `java.lang.Object` or elsewhere.

And since equals is one of those methods, the "abstract method count" of the interface is still `1`.

Wherever a `Comparator` can be passed, e.g. in sort methods, you can pass a lambda because it is a functional interface
e.g.
```java
Arrays.sort(people, (p1, p2) -> Integer.compare(p1[0], p2[0]));
```



