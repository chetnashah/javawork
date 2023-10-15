
introduces the concept of an API exposed to consumers. A library is a Java component meant to be consumed by other components. It’s a very common use case in multi-project builds, but also as soon as you have external dependencies.

## Declaring a Java Library

```
// build.gradle.kts
plugins {
    `java-library`
}
```

## Configuration graph

https://docs.gradle.org/current/userguide/java_library_plugin.html#sec:java_library_configurations_graph