
### Gradle project

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

1. Initialization - settings.gradle runs
2. Configuration - build.gradle runs, task dependency resolved
3. Execution - task actions are run according to dependency graph

afterEvaluate {

}
is a useful debugging place that executes after configuration but before exection to inspect state.


### What is ext in gradle builds?

ext is shorthand for project.ext, and is used to define extra properties for the project object. (It's also possible to define extra properties for many other objects.) When reading an extra property, the ext. is omitted (e.g. println project.springVersion or println springVersion). 

### What is the role of gradle.properties file?

populate properties to a project just like an ext block does, but if ext block is present with same property names, than ext block will take more priority/weightage over properties in gradle.properties.

### What is a gradle plugin?

Gradle plugin is just reusable peice of build logic, usually tasks etc.

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