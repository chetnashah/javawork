
### Useful shortcuts

Use `Cmd-N` in gradle file to search for dependency first and then add.

### Kotlin with Gradle

https://www.youtube.com/watch?v=KN-_q3ss4l0

### Key classes/Interfaces:

1. Script Interace - represents the `build.gradle` file instance (https://docs.gradle.org/current/dsl/org.gradle.api.Script.html)
2. Project Interface - represents a project via `build.gradle`.
3. Gradle Interface.
4. Settings Interface
5. Task Interface
6. Action Interface


### Project evaluation events

1. `beforeEvaluate`
2. `afterEvaluate`

### Task graph events
1. `whenTaskAdded`
2. `whenReady`
3. `beforeTask`
4. `afterTask`

### Gradle FileCollection

A `FileCollection` represents a collection of file system locations which you can query in certain ways. A file collection is can be used to define a classpath, or a set of source files, or to add files to an archive.

There are no methods on this interface that allow the contents of the collection to be modified. However, there are a number of sub-interfaces, such as `ConfigurableFileCollection` that allow changes to be made.

A file collection may contain task outputs. The file collection tracks not just a set of files, but also the tasks that produce those files. When a file collection is used as a task input property, Gradle will take care of automatically adding dependencies between the consuming task and the producing tasks.

### Script interface

You always have `logger` available in your build script.

`apply`: Configures the delegate object for this script using plugins or scripts. The given closure is used to configure an ObjectConfigurationAction which is then used to configure the delegate object.

`build.gradle` build script delegates to project Object.
`settings.gradle` build script delegates to Seggings Object which in turn has access to bunch of Project objects.
`Gradle`: TOp level object. - Can be used in files like `init.gradle` where no other context like Project etc. has started yet. https://docs.gradle.org/current/dsl/org.gradle.api.invocation.Gradle.html#org.gradle.api.invocation.Gradle:gradle
Project and SEttings object have access to the gradle object.

### gradle domain object

Lives on the project.
Can be accessed in script like this:
`project.gradle.someMethods`.

### Gradle Task

A Task represents a single atomic piece of work for a build, such as compiling classes or generating javadoc.

A Task contains a list of actions to be performed

You can also use the task keyword in your build file:

The task configure closure has access to the task. One can do
things like configure task description etc. in the configure closure.
```
 task myTask
 task myTask { configure closure }
 task myTask(type: SomeType)
 task myTask(type: SomeType) { configure closure }
```

```groovy
task Hello {
    // sets property of the task
    description "This is a description"
}
println Hello.descrition
```

If task already exists in scope, you can also configure just using
their name and closure
e.g.
```
taskName {
    println "this task's name is $name"
    description "What a new description"
}
```

Each task has a name, which can be used to refer to the task within its owning project, and a fully qualified path, which is unique across all tasks in all projects. The path is the concatenation of the owning project's path and the task's name

A Task has 4 'scopes' for properties. You can access these properties by name from the build file or by calling the property(String) method. You can change the value of these properties by calling the setProperty(String, Object) method.


1. The Task object itself. This includes any property getters and setters declared by the Task implementation class. The properties of this scope are readable or writable based on the presence of the corresponding getter and setter methods.

2. The extensions added to the task by plugins. Each extension is available as a read-only property with the same name as the extension.

3. The convention properties added to the task by plugins. A plugin can add properties and methods to a task through the task's Convention object. The properties of this scope may be readable or writable, depending on the convention objects.

4. The extra properties of the task. Each task object maintains a map of additional properties. These are arbitrary name -> value pairs which you can use to dynamically add properties to a task object. Once defined, the properties of this scope are readable and writable.

### Gradle configurations

A `Configuration` represents a group/scope of artifacts and their dependencies.
e.g. `compile` configuration, or `implementation` configuration.


Configuration is an instance of a `FileCollection` that contains all dependencies (see also `getAllDependencies()`) but not artifacts. If you want to refer to the artifacts declared in this configuration please use `getArtifacts()` or `getAllArtifacts()`.

Configurations are a fundamental part of dependency resolution in Gradle. In the context of dependency resolution, it is useful to distinguish between a consumer and a producer. Along these lines, configurations have at least 3 different roles:

1. to declare dependencies

2. as a consumer, to resolve a set of dependencies to files

3. as a producer, to expose artifacts and their dependencies for consumption by other projects (such consumable configurations usually represent the variants the producer offers to its consumers)


Configuration role    can be resolved  can be consumed
Bucket of dependencies     false   false
Resolve for certain usage  true  false
Exposed to consumers       false   true
Legacy, don’t us           true    true



A configuration that can be resolved is a configuration for which we can compute a dependency graph, because it contains all the necessary information for resolution to happen. 
That is to say we’re going to compute a dependency graph, resolve the components in the graph, and eventually get artifacts. 

A configuration which has `canBeResolved` set to false is not meant to be resolved. Such a configuration is there only to declare dependencies. The reason is that depending on the usage (compile classpath, runtime classpath), it can resolve to different graphs. It is an error to try to resolve a configuration which has `canBeResolved` set to false. To some extent, this is similar to an abstract class (`canBeResolved=false`) which is not supposed to be instantiated, and a concrete class extending the abstract class (`canBeResolved=true`). A resolvable configuration will extend at least one non-resolvable configuration (and may extend more than one).

### Gradle plugins

Applying a plugin means actually executing the plugin’s `Plugin.apply(T)` on the Project you want to enhance with the plugin. Applying plugins is idempotent. That is, you can safely apply any plugin multiple times without side effects.

There are two general types of plugins in Gradle, `script plugins` and `binary plugins`.

`Script plugins`: Script plugins are additional build scripts that further configure the build and usually implement a declarative approach to manipulating the build. They are typically used within a build although they can be externalized and accessed from a remote location.

```groovy
/* 
Script plugins are automatically resolved and can be applied from a script on the local filesystem or at a remote location.
*/
apply from: 'other.gradle'
```


`Binary plugins`: Binary plugins are classes that implement the Plugin interface and adopt a programmatic approach to manipulating the build. Binary plugins can reside within a build script, within the project hierarchy or externally in a plugin jar. **You apply plugins by their plugin id, which is a globally unique identifier, or name, for plugins**

You apply plugins by their plugin `id`, which is a globally unique identifier, or name, for plugins. Core Gradle plugins are special in that they provide short names, such as 'java' for the core JavaPlugin.
```groovy
plugins {
    id 'java'
}

plugins {
    id 'com.jfrog.bintray' version '0.4.1'
}
```

A plugin often starts out as a script plugin (because they are easy to write) and then, as the code becomes more valuable, it’s migrated to a binary plugin that can be easily tested and shared between multiple projects or organizations.

Applying a plugin means actually executing the plugin’s `Plugin.apply(T)` on the Project you want to enhance with the plugin. Applying plugins is idempotent. That is, you can safely apply any plugin multiple times without side effects.



#### Convention plugin

More often that not, projects end up in one of these situations:

1. Many random `*.gradle(.kts)` scripts that not everyone understands.
2. Very complicated `subprojects` and `allprojects` blocks.
3. A `buildSrc` directory that houses much of the build logic. (not great because it invalidates build cache every time you make changes to anything inside it, effectively causing a clean build with every change)

Convention Plugins are Gradle’s way of sharing your build logic 
between submodules and addressing the above concerns. - https://docs.gradle.org/current/samples/sample_convention_plugins.html





#### Java Plugin

has useful tasks like `build`, `clean`, `test`.

Build artifacts are placed under `build` directory.
Expects convention like `src/main/java` for prod/app code, 
`src/test/java` for test code which form what is known as SourceSet.




#### Extra task properties

You can add your own properties to a task. To add a property named myProperty, set ext.myProperty to an initial value. From that point on, the property can be read and set like a predefined task property.

```groovy
task myTask {
    ext.myProperty = "myValue"
}

task printTaskProperties {
    doLast {
        println myTask.myProperty
    }
}
```

```sh
> gradle -q printTaskProperties
myValue
```

#### TaskContainer

A `TaskContainer` is responsible for managing a set of `Task` instances.

You can obtain a `TaskContainer` instance by calling `Project.getTasks()`, or using the tasks property in your build script.

#### Task Actions

A `Task` is made up of a sequence of `Action` objects. When the task is executed, each of the actions is executed in turn, by calling `Action.execute(T)`. You can add actions to a task by calling `doFirst(Action)` or `doLast(Action)`.

#### Task dependencies and execution

Callback available is `whenReady`.
`project.gradle.taskGraph.allTasks` is only ready after `whenReady`, because Task graph is populated in configuration phase.

Here is the correctway:
```groovy
project.gradle.taskGraph.whenReady {
    taskGraph ->
        println "whenReady closure cb!"
        println taskGraph.allTasks
}
```

Another callbacks are `TaskGraph.beforeTask` and `TaskGraph.afterTask` which are called before and after every task. This is allowed to use during config phase, no need for whenReady. 

`TaskGraph` has other useful propeties like
`allTasks` and `hasTask(taskName)` etc. which are recommended to use inside whenReady only.

```groovy
task doStartProcess {
    doLast {
        println "$name - Now starting process - OK"
    }
}

task doStep2 {
    doLast {
        println "$name - Performed doStep2 OK"
    }
}

task doSomethingInTheMiddle {
    doLast {
        println "$name - performed middleStep OK"
    }
}

task doFinished (dependsOn: ['doSomethingInTheMiddle', 'doStep2']) {
    doLast {
        println "$name performed DONE OK"
    }
}

println ">>>> ${project.tasks.findAll { task -> task.name.startsWith('do') } }"

if (project.hasProperty('doSomethingDependsOnDoStep2')) { // conditional task dependency
    doSomethingInTheMiddle.dependsOn(doStartProcess, tasks.findAll { task -> task.name.startsWith('doStep2')})
}
```
### ScriptHandler

A ScriptHandler allows you to manage the compilation and execution of a build script. You can declare the classpath used to compile and execute a build script. This classpath is also used to load the plugins which the build script uses.

You can obtain a ScriptHandler instance using `Project.getBuildscript()` or `Script.getBuildscript()`.


### Gradle project

Project object is the default context in the build.gradle file.

There is 1 to 1 relationship between a gradle project and a build.gradle file. 
Anything that is not defined in a buildscript is delegated to project object.

Standard properties include : 
1. project - the Project instance
2. name - name of project directory
3. path - absolute path of project
4. description - string description of project
5. projectDir - the File dir containing buildscript
6. buildDir - projectDir/build
7. group,version - strings specifying group and version.



extra properties can be added in an ext block
e.g.
``` gradle
ext {
    emailNotification="build@master.org"
}
```

### What is .gradle directory for?

For incremental build support partial builds (cached build related stuff), it is ok to delete it if needed.

### What is local.properties file for?

gradle gets the info from this file to locate the android sdk.

### What is gradle wrapper?

gradle wrapper is extra scripts along with project that manage the task of installing gradle to machine if it is not present. This is the recommended way to make your gradle project and should be commited to source control.

### What are gradle repositories?
Repositories are places to look for artifacts, or places to publish artifacts. They can be mainly of two types
1. Local file based - flatDir (less recommended)
2. Local repository based - local maven
3. external repository based - mavencentral or Jcenter.(highly recommended)

### How are dependencies grouped in gradle?
Gradle dependency groups are also known as 'configuration' which are mainly: compile, runtime, testCompile and testRuntime

### Gradle Dependency Configuraiton
Every dependency declared for a Gradle project applies to a specific scope.
For example some dependencies should be used for compiling source code whereas others only need to be available at runtime. Gradle represents the scope of a dependency with the help of a Configuration.
Some examples are: `testRuntime`, `implementation` etc.

### Well known configurations

#### `implementation`
The dependencies required to compile the production source of the project which are not part of the API exposed by the project. For example the project uses Hibernate for its internal persistence layer implementation.

#### `api`
The dependencies required to compile the production source of the project which are part of the API exposed by the project. For example the project uses Guava and exposes public interfaces with Guava classes in their method signatures.

#### `testImplementation`
The dependencies required to compile and run the test source of the project. For example the project decided to write test code with the test framework JUnit.




### How many types of dependency does gradle have?

1. external - living in a repository
e.g. 
``` gradle
    compile 'com.google.guava:guava:18.0
```

2. file based - dependency on files (e.g. jar files) living in a folder
e.g.
``` gradle
    compile fileTree(dir: 'libs', include: '*.jar')
```

3. project based - a dependency on a gradle project (one that contains a build.gradle)
e.g.
``` gradle
    compile project('sisterproject')
```

### How are transitive dependency conflict resolved in gradle?
By default gradle resolves conflict by using newest version.

### Multi project builds

By multi-project, we mean using other modules which have a build.gradle file hence the name multi-project build instead of mult-module build since there are multiple build.gradle files.
To define a multi project build you need to make a settings.gradle file.

By default android starts with a multi-project build in a freshly created application, with a sub-project corresponding to app/build.gradle

#### How many types of tasks are there?
There are two types of tasks:
1. Typed tasks // do not use doLast follow documentation of typed tasks instead.
2. Adhot tasks (ones with doLast in them)

### How gradle tasks work?

Note: just defining a task won't run it by itself, you have to put it somewhere in the build-pipline via dependsOn or explicitly run it from command line.

Before a task is executed for the first time, Gradle takes a snapshot of the inputs. This snapshot contains the paths of input files and a hash of the contents of each file. Gradle then executes the task. If the task completes successfully, Gradle takes a snapshot of the outputs. This snapshot contains the set of output files and a hash of the contents of each file. Gradle persists both snapshots for the next time the task is executed.

Each time after that, before the task is executed, Gradle takes a new snapshot of the inputs and outputs. If the new snapshots are the same as the previous snapshots, Gradle assumes that the outputs are up to date and skips the task. If they are not the same, Gradle executes the task. Gradle persists both snapshots for the next time the task is executed.

Gradle also considers the code of the task as part of the inputs to the task. When a task, its actions, or its dependencies change between executions, Gradle considers the task as out-of-date

### Gradle build lifecycle

All `.gradle` files under `$USER_HOME_DIR/.gradle/init.d` will be executed first.
If you setup some `ext` properties in these gradle files, they are available to all the projects etc. e.g. you can define a `timestamp` an other shared global util methods.

1. Initialization - settings.gradle and/or init.gradle runs - determination of projects to be taking part in 
the build. Project instances created for each of the projects.
2. Configuration - build.gradle runs, You will be able to see `Configure Project:` on cli, task dependency resolved
3. Execution - task actions are run according to dependency graph

afterEvaluate {

}
is a useful debugging place that executes after configuration but before exection to inspect state.


### What is ext in gradle builds?

ext is shorthand for project.ext, and is used to define extra properties for the project object. (It's also possible to define extra properties for many other objects.) When reading an extra property, the ext. is omitted (e.g. println project.springVersion or println springVersion). 

### What is the role of gradle.properties file?

populate properties to a project just like an ext block does, but if ext block is present with same property names, than ext block will take more priority/weightage over properties in gradle.properties.

A bunch of key-value pairs on each line, like an `ini` file, something similar to setting
env variables for your project.

Remember all gradle property values are read in as strings, so need to explicitly convert to intger wherver needed.
e.g
```properties
// gradle.properties
VERSION_CODE=2
```
```groovy
//build.gradle
android {
    defaultConfig {
        versionCode project.VERSION_CODE.toInteger()
    }
}
```

### Where can gradle properties come from?

The can come from (last one wins/overrides previous ones):
1. `<prooject-root>/gradle.properties`
2. `<user-home>/gradle.properties`
3. command line using `-D` or `-P`
4. within code itself (via `project.prop=val` or `ext.prop=val` or `project.ext.prop=val`)

Gradle doesn’t provide any information on the source of a property.
Use `SomeObject.hasProperty(propname)` to avoid runtime exceptions.

Setting a project property via a system property
`org.gradle.project.foo=bar`
Setting a project property via an environment variable
`ORG_GRADLE_PROJECT_foo=bar`


### What is a gradle plugin?

Gradle plugin is just reusable peice of build logic, usually tasks and extensions to configure plugins/tasks.

Two ways to apply them:
1. by class name e.g. `apply plugin: GreetPlugin`
2. by plugin dsl based on `id` e.g.
```groovy
plugins {
    id 'my-plugin'
}
```

### What is most common convention for java builds in gradle?
sources in src/main/java where src lives in project directory(same level as build.gradle)

and build outputs in 
build/ dir.

### What are build types and product flavors/variants in gradle?

build types are usually developer centric e.g. debug, release
where as product flavors/variants are visible to end users
e.g. free/paid etc.

We can add our own build types using a buildTypes block e.g.
``` gradle
android {
    buildTypes {
        staging
    }
}
```

The dimensionality is in particular order
: 
1. src/test/androidTest
2. productFlavor
3. buildTypes
4. otherTypes

e.g. 
mainFreeDebugMdpi
or
testPaidReleaseHdpi etc.

### gradle function syntax specifics

Now we will go through different ways to call method which accepts closure:

``` gradle
method accepts 1 parameter - closure 
myMethod(myClosure)

if method accepts only 1 parameter - parentheses can be omitted 
myMethod myClosure

I can create in-line closure 
myMethod {println 'Hello World'}

method accepts 2 parameters 
myMethod(arg1, myClosure)

or the same as '4', but closure is in-line 
myMethod(arg1, { println 'Hello World' })

if last parameter is closure - it can be moved out of parentheses 
myMethod(arg1) { println 'Hello World' }

At this point I really have to point your attention to example #3 and #6. Doesn't it remind you something from gradle scripts?
```

Summary : Each closure has a delegate object, which groovy uses to lookup (free)variable & method references, this is usally known as environment, context etc. in other functional languages, where usually it is lexical, but in groovy it can be explicitly set.

### Writing your own plugin

https://www.youtube.com/watch?v=LPzBVtwGxlo&list=RDCMUCVHFbqXqoYvEWM1Ddxl0QDg&index=8

https://www.youtube.com/watch?v=TfdiiYHWFTw

https://www.youtube.com/watch?v=dLERr-MNPQo

Usually you will :
1. create tasks of tis plugin
2. Create extensions
3. hook this plugins task to build chain (if you want to implicitly hook tasks).


`buildSrc` way: Create a new directory known as `buildSrc` where you put your custom tasks/plugins code. This project is automatically included in build.

standalone project way: publish a standalone project and then apply via id, this way we must import plugin as dependency or via `includeBuild`, or via `pluginManagement`.

#### Defining plugin via class (in groovy) and applied via 'apply'

```groovy
class MyPlugin implements Plugin<Project> {
    @Override
    void apply(Project project) {
        // do something with the project, maybe add some tasks that correspond to MyPlugin?
        project.task("playme") {
            doLast {
                println "played something::"
            }
        }
    }
}
```

another one

```groovy
// build.gradle
class GreetingPlugin implements Plugin<Project> {
    void apply(Project project) {
        project.task('hello') {
            doLast {
                println 'Hello from the GreetingPlugin'
            }
        }
    }
}

// Apply the plugin
apply plugin: GreetingPlugin // applying plugin by class name
```

#### Making plugins configurable

Most plugins offer some configuration options for build scripts and other plugins to use to customize how the plugin works. **Plugins do this using extension objects**.

The Gradle `Project` has an associated `ExtensionContainer` object that contains all the settings and properties for the plugins that have been applied to the project.

**An extension object is simply an object with Java Bean properties that represent the configuration.**

```groovy
abstract class GreetingPluginExtension {
    abstract Property<String> getMessage()

    GreetingPluginExtension() {
        message.convention('Hello from GreetingPlugin')
    }
}

class GreetingPlugin implements Plugin<Project> {
    void apply(Project project) {
        // Add the 'greeting' extension object
        def extension = project.extensions.create('greeting', GreetingPluginExtension)
        // Add a task that uses configuration from the extension object
        project.task('hello') {
            doLast {
                println extension.message.get()
            }
        }
    }
}

apply plugin: GreetingPlugin

// Configure the extension
greeting.message = 'Hi from Gradle'
```

### buildSrc

A directory in your code where you can move all common build logic (e.g. tasks definition etc) and 
share this build logic between many modules.

### Gradle custom plugin as standalone project

we can publish it and share it with others.

Use `java-gradle-plugin` for development assistance of gradle plugins.

For this particular project, one will need following gradle build file: `build.gradle`:
```groovy
plugins {
    id 'java-gradle-plugin' // development purposes, use groovy-gradle-plugin if project in groovy
    id 'maven-publish' // to publish
}

gradlePlugin {
    plugins {
        simplePlugin { // what is this?
            id = 'org.example.greeting' // id defined here explicitly
            implementationClass = 'org.example.GreetingPlugin' // gradle needs this to find class for given id
        }
    }
}
```

specify plugin-id: Your plugin id should be a combination of components that reflect namespace (a reasonable pointer to you or your organization) and the name of the plugin it provides.

If you are interested in publishing your plugin to be used by the wider Gradle community, **you can publish it to the Gradle Plugin Portal**.
If you have published your plugin to custom non-standard location, not the gradle plugin portal then `pluginManagement` block will be needed in `settings.gradle` of the consuming application.

### Precompiled script plugins

Script plugins are basically just plain old Gradle build scripts with a different name.

Precompiled script plugins are compiled into class files and packaged into a jar. 
**For all intents and purposes, they are binary plugins and can be applied by plugin ID, tested and published as binary plugins**

To apply a precompiled script plugin, you need to know its `ID which is derived from the plugin script’s filename (minus the .gradle extension).`

For example, the script `src/main/groovy/java-library-convention.gradle` would have a plugin ID of `java-library-convention`. 

Likewise, `src/main/groovy/my.java-library-convention.gradle` would result in a plugin ID of `my.java-library-convention`


In addition to plugins written as standalone projects, Gradle also allows you to provide build logic written in either Groovy or Kotlin DSLs as precompiled script plugins. You write these as `*.gradle` files in `src/main/groovy` directory or `*.gradle.kts` files in `src/main/kotlin` directory.

An example of how to have this:
1. create a `buildSrc` project
2. This project's `build.gradle` must contain a dev plugin: `groovy-gradle-plugin`
3. Create a file: `buildSrc/src/main/groovy/java-library-convention.gradle`.
4. Contents of `java-library-convention.gradle`:
```groovy
/**
Note that this will actually apply the plugins to the main project, i.e. the one that applies the precompiled script plugin */
plugins {
    id 'java-library'
    id 'checkstyle'
}

java {
    sourceCompatibility = JavaVersion.VERSION_11
    targetCompatibility = JavaVersion.VERSION_11
}

checkstyle {
    maxWarnings = 0
    // ...
}

tasks.withType(JavaCompile) {
    options.warnings = true
    // ...
}

dependencies {
    testImplementation("junit:junit:4.13")
    // ...
}
```
5. finally use this custom plugin by id i.e. filename minus (.gradle)
IN other projects:
```groovy
plugins {
    id 'java-library-convention'
}
```