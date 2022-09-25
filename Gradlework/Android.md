

## Print all variants

```groovy
task printVariantNames() {
    doLast {
        android.applicationVariants.all { variant ->
            println variant.name
        }
    }
}
```

## 

The `applicationVariants` property is only available for the `com.android.application` plug-in. 

A `libraryVariants` property is available in Android libraries.
 A `testVariants` property is available in both.

## Install all debug variants on single device

```groovy
task installDebugFlavors() {
    android.applicationVariants.all { v ->
        if (v.name.endsWith('Debug')) {
            String name = v.name.capitalize()
            dependsOn "install$name"
        }
    }
}
```

Now running `installDebugFlavors` task runs all installation of debug apks


## AndroidComponents extension

Each component has a type, like `application` or `library` and will have a dedicated extension with methods that are related to the particular component type.



## Callback on variants being determined

```groovy
  androidComponents.onVariants { variant ->
    // Callback after variants are built. Apparently it's read-only access at this point, but outputs are available here
    println(variant.outputs)
  }
```