
https://docs.gradle.org/current/userguide/designing_gradle_plugins.html

You can implement a Gradle plugin in any language you like, provided the implementation ends up compiled as JVM bytecode

## Plugin repositories

By default Gradle will use the [Gradle plugin portal](https://plugins.gradle.org/) to look for plugins.


## Script plugins

Plain old build scripts with different name.
hard to well maintain and hard to test

## Binary plugins

Should be used whenever logic needs to be reused or shared across independent projects.
Properly structuring code into class and packages, cacheable and versioning.

