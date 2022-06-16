

```java
public class Something {
    private Something() {}

    private static class LazyHolder {
        static final Something INSTANCE = new Something();
    }

    public static Something getInstance() {
        return LazyHolder.INSTANCE;
    }
}
```