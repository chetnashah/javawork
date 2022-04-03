

### Platform types

Any reference in Java may be null, which makes Kotlin's requirements of strict null-safety impractical for objects coming from Java. Types of Java declarations are treated in Kotlin in a specific manner and called platform types. Null-checks are relaxed for such types, so that safety guarantees for them are the same as in Java.

* `T!` means `T` or `T?`,
* **Note: You cannot create directly values of `Type!` in kotlin, only returned at a Java framework/sdk/jar**

#### Nullability annotations e.g. `@Nullable` etc

Java types that have nullability annotations are represented not as platform types, 
but as actual nullable or non-null Kotlin types.

e.g.
```
JetBrains (@Nullable and @NotNull from the org.jetbrains.annotations package)
JSpecify (org.jspecify.nullness)
Android (com.android.annotations and android.support.annotations)
JSR-305 (javax.annotation, more details below)
FindBugs (edu.umd.cs.findbugs.annotations)
Eclipse (org.eclipse.jdt.annotation)
Lombok (lombok.NonNull)
RxJava 3 (io.reactivex.rxjava3.annotations)
```


