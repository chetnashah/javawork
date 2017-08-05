* What are default methods?

default methods are methods in an interface which should have an implementation.

* What is a functional interface?

A functional interface is an interface that has exactly one single abstract method that is not in plain Object.

e.g. 
```
interface Runnable {
    public void run();
}
```

A functional interface's method's type signature will usually take up as the type of lambda expression.

### Well known functional interfaces:

```Java
main ones inside java.util.function

public interface Predicate<T> {
    boolean test(T t);
}

public interface Consumer<T> {
    void accept(T t);
}

public interface Supplier<T> {
    T get();
}

Represents a function that takes one argument and produces a result
public interface Function<T,R> {
    R apply(T t);
}
```