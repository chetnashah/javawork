
https://docs.gradle.org/current/userguide/designing_gradle_plugins.html

You can implement a Gradle plugin in any language you like, provided the implementation ends up compiled as JVM bytecode

## Three major types

1. core plugins - ship with gradle itself e.g. `java-library`
2. community plugins - plugins hosted on gradle plugin portal. https://plugins.gradle.org/
3. convention plugins - custom plugins written along with project to organize build logic.

## Plugin repositories

By default Gradle will use the [Gradle plugin portal](https://plugins.gradle.org/) to look for plugins.


## Script plugins

Plain old build scripts with different name.
hard to well maintain and hard to test

## Binary plugins

Should be used whenever logic needs to be reused or shared across independent projects.
Properly structuring code into class and packages, cacheable and versioning.



## How to check if plugin-id refers to buildSrc Plugin? (Check definiton of id in buildSrc gradle)

**It is possible that a plugin id refers to a buildSrc plugin, if the plugin is not published on any repository.**

Plugin present in the `buildSrc` can be referred by `id` declared in that plugin.

e.g.
Plugin Definition:
```kt
// buildSrc/build.gradle
plugins {
    id 'java-gradle-plugin' // needed for authoring plugins
}

// defining this plugin metadata/identifiers
gradlePlugin {
    plugins {
        myPlugins {
            id = 'my-plugin'
            implementationClass = 'my.MyPlugin'
        }
    }
}
```

usage in a project via id
```kt
// app/build.gradle
plugins {
    id 'my-plugin' // referring to a plugin inside buildSrc by id
}
```

## What are extensions introduced by plugins?

`Extensions` are way to add additional properties and methods on an object (usually project). You can also have extension on plugin to configure plugin.

Example - Java Plugin adds an extension named `sourceSets`, that allows us to configure source sets for our project. That is, usually it is a block to configure the task/project via extension block.

