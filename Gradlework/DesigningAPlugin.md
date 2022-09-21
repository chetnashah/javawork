
https://youtu.be/F3DF6bQo6jk?t=545

## Classes involved

Plugin class
Task class
Extension class

![plugin classes](images/gradlepluginclasses.png)

## Plugins to use: Use `java-gradle-plugin` for development assistance of gradle plugins

The Java Gradle Plugin development plugin can be used to assist in the development of Gradle plugins.

https://docs.gradle.org/current/userguide/java_gradle_plugin.html

```groovy
plugins {
    id 'groovy'
    id 'java-gradle-plugin'
}
```

## Dependencies

Using spock for testing = https://spockframework.org/spock/docs/2.1/introduction.html

## Making plugin configurable using "Extensions"

Most plugins offer some configuration options for build scripts and other plugins to use to customize how the plugin works. Plugins do this using extension objects. 

The Gradle Project has an associated `ExtensionContainer` object that contains all the settings and properties for the plugins that have been applied to the project. 

You can provide configuration for your plugin by adding an extension object to this container. **An extension object is simply an object with Java Bean properties that represent the configuration.**

example:

```groovy
// externsion is an interface defining property names coming from build script config file
interface GreetingPluginExtension {
    Property<String> getMessage()
    Property<String> getGreeter()
}

class GreetingPlugin implements Plugin<Project> {
    void apply(Project project) {
        def extension = project.extensions.create('greeting', GreetingPluginExtension)
        project.task('hello') {
            doLast {
                println "${extension.message.get()} from ${extension.greeter.get()}"
            }
        }
    }
}

apply plugin: GreetingPlugin

// Configure the extension using a DSL block
greeting {
    message = 'Hi'
    greeter = 'Gradle'
}
```