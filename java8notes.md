* What are default methods?

default methods are methods in an interface which should have an implementation.

* What is a functional interface?

A functional interface is an interface that has exactly one single abstract method that is not in plain Object.
It is also common to use lambda in all interfaces with single methods, 
e.g. onClickListeners etc. which you can see even in Kotlin.

e.g. 
```
interface Runnable {
    public void run();
}
```

A functional interface's method's type signature will usually take up as the type of lambda expression.

### Well known functional interfaces:

```Java
// main ones inside java.util.function

public interface Predicate<T> {
    boolean test(T t);
}

public interface Consumer<T> {
    void accept(T t);
}

public interface Supplier<T> {
    T get();
}

// Represents a function that takes one argument and produces a result
public interface Function<T,R> {
    R apply(T t);
}

// java.util.Comparator
public interface Comparator<T> {
    int compare(T o1, T o2);
}
```