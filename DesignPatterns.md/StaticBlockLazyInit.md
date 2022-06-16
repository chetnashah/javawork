

## THreadsafe in java


```java
public final class Singleton {
    public static final Singleton INSTANCE;

    private Singleton(){}

    static { // lock is held here on first creation, and runs only once
        Singleton var0 = new Singleton();
        INSTANCE = var0;
    }
}
```
