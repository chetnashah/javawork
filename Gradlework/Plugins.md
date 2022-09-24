
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

