

## What task gets executed when you sync gradle:

```
prepareKotlinBuildScriptModel or prepareBuildScriptModel
```

## How to add a task to all projects

**This is a faster way to add tasks across mmany projects in oneshot**

`allprojects` closure gets individual proj as closure param.

```
allprojects { proj ->
    task('hello').doLast {
        println("im hello task for project: "+proj)
    }
}
```

## How to target the task that report tasks?
e.g. only show tasks of certain kind

```groovy
tasks.named<TaskReportTask>("tasks") {
     displayGroup = "My build group"
}
```
now doing `./gradlew tasks` will only output tasks under group "My build group"

`Note`: `displayGroup` can help us categorize tasks into local tasks, ci tasks etc by forming individual groups for them and categorizing tasks for them

```
// build.gradle
def mybuildgroup = "My build group"
tasks.named("tasks") {
    displayGroup = mybuildgroup
}

tasks.named("build") {
    group = mybuildgroup
}

tasks.named("check") {
    group = mybuildgroup
}
```
Running `./gradlew tasks return`
```
$./gradlew tasks
My build group tasks
--------------------
build - Assembles and tests this project.
check - Runs all checks.
```

## Create a project level task that runs samee task on all subprojects

```groovy
task.register("qualityCheckAll") {
     dependsOn(subprojects.map { ":${it.name}:qualityCheck" }) // return list of mapped subproject task
}
```

## TaskContainer (`project.tasks` property)

A TaskContainer is responsible for managing a set of Task instances.

You can obtain a TaskContainer instance by calling Project.getTasks(), or using the tasks property in your build script.

### Useful methods on TaskContainer

### How to print all tasks of a project

```
tasks.forEach {
    println(it.name)
}
```

### Refer a task by name 

```groovy
tasks.named("build") {
    println(it.name)
}
``` 

## Task execution graph


https://docs.gradle.org/current/javadoc/org/gradle/api/execution/TaskExecutionGraph.html

Sits on gradle object of the project, Can be retrieved from `project.gradle.taskGraph`

### Imp callbacks

1. `whenReady` - Adds a closure to be called when this graph has been populated.

### Imp methods

1. `taskGraph.getDependencies​(Task task)` - get dependencies of given taks
2. `taskGraph.hasTask(Task task)` or `taskGraph.hasTask(String path)` - check if task exists in build graph
3. `addTaskExecutionGraphListener​(TaskExecutionGraphListener listener)` - Adds a listener to this graph, to be notified when this graph is ready.


## Enhanced (typed) tasks

Most Gradle plugins use enhanced tasks. 
**With enhanced tasks, you don’t need to implement the task behaviour as you do with simple tasks. You simply declare the task and configure the task using its properties.** In this way, enhanced tasks let you reuse a piece of behaviour in many different places, possibly across different builds.

`Typed tasks`: The behaviour and properties of an enhanced task are defined by the task’s class. When you declare an enhanced task, you specify the type, or class of the task.

e.g. of builtin enhanced tasks are copy task, exec task.

### Where to define custom task type/class?

1. build script (build.gradle) - the task class is not visible outside the build script.
2. `buildSrc` project - You can put the source for the task class in the `rootProjectDir/buildSrc/src/main/groovy` directory (or `rootProjectDir/buildSrc/src/main/java` or `rootProjectDir/buildSrc/src/main/kotlin`). scope = The task class is visible to every build script used by the build. However, it is not visible outside the build, and so you cannot reuse the task class outside the build it is defined in.
3. As a JVM/jar library - reusable everywhere





## View the task dependency tree

https://www.youtube.com/watch?v=gTYHEcSbdes

https://gitlab.com/barfuin/gradle-taskinfo

basically add this plugin:
`id 'org.barfuin.gradle.taskinfo' version '1.0.5'`

and excute tiTree along with your actual task
e.g.
`./gradlew tiTree build`

will give output like:
```
> Task :tiTree
:build                                          (org.gradle.api.DefaultTask)
+--- :assemble                                  (org.gradle.api.DefaultTask)
|    `--- :jar                                  (org.gradle.api.tasks.bundling.Jar)
|         `--- :classes                         (org.gradle.api.DefaultTask)
|              +--- :compileJava                (org.gradle.api.tasks.compile.JavaCompile)
|              `--- :processResources           (org.gradle.language.jvm.tasks.ProcessResources)
`--- :check                                     (org.gradle.api.DefaultTask)
     `--- :test                                 (org.gradle.api.tasks.testing.Test)
          +--- :classes                         (org.gradle.api.DefaultTask)
          |    +--- :compileJava                (org.gradle.api.tasks.compile.JavaCompile)
          |    `--- :processResources           (org.gradle.language.jvm.tasks.ProcessResources)
          `--- :testClasses                     (org.gradle.api.DefaultTask)
               +--- :compileTestJava            (org.gradle.api.tasks.compile.JavaCompile)
               |    `--- :classes               (org.gradle.api.DefaultTask)
               |         +--- :compileJava      (org.gradle.api.tasks.compile.JavaCompile)
               |         `--- :processResources (org.gradle.language.jvm.tasks.ProcessResources)
               `--- :processTestResources       (org.gradle.language.jvm.tasks.ProcessResources)
```


## Two types of tasks

1. lifecycle tasks
2. actionable tasks

* `actionable tasks` which perform an action – for example, the jar task has an action associated with it which goes and creates a jar file. These types of tasks may or may not depend on other tasks. These will do a concrete thing and driven by inputs and outputs.

* `aggregate/orchestrator/lifecycle tasks` – these tasks are there just to provide a convenient way for you to execute a grouping of tasks/functionality. For example, rather than you having to run the check and assemble tasks separately, the build task just aggregates them together. Usually they will not have input/output.


## Task actions

A `Task` is made up of a sequence of `Action` objects. 

When the task is executed, each of the actions is executed in turn, by calling `Action.execute(T)`. You can add actions to a task by calling `Task.doFirst(org.gradle.api.Action)` or `Task.doLast(org.gradle.api.Action)`.


## Task properties

A Task has 4 'scopes' for properties.

1. The `Task` object itself. This includes any property getters and setters declared by the Task implementation class. The properties of this scope are readable or writable based on the presence of the corresponding getter and setter methods.
2. The **extensions added to the task by plugins**. Each extension is available as a read-only property with the same name as the extension.
3. The **convention properties added to the task by plugins**. A plugin can add properties and methods to a task through the task's `Convention` object. The properties of this scope may be readable or writable, depending on the convention objects.
4. **The extra properties of the task. Each task object maintains a map of additional properties.** These are arbitrary name -> value pairs which you can use to dynamically add properties to a task object. Once defined, the properties of this scope are readable and writable.

## Imp properties

### actions

The sequence of Action objects which will be executed by this task, in the order of execution.

### extensions

The container of extensions.

### group

The task group which this task belongs to. The task group is used in reports and user interfaces to group related tasks together when presenting a list of tasks to the user.

### inputs/outputs

The inputs/outputs of this task.

### enabled

Returns if this task is enabled or not.

### path

The path of the task, which is a fully qualified name for the task. The path of a task is the path of its Project plus the name of the task, separated by :.

### project

The project that this task belongs to

### taskDependencies

Returns a TaskDependency which contains all the tasks that this task depends on.

### finalizedBy

Returns tasks that finalize this task.

### dependsOn

The dependencies of this task.

## Imp methods

### doFirst/doLast

Adds the given closure to the beginning of this task's action list. The closure is passed this task as a parameter when executed.

### dependsOn(paths)

Adds the given dependencies to this task. See here for a description of the types of objects which can be used as task dependencies.

### finalizedBy

Adds the given finalizer tasks for this task.

### hasProperty(propName)

Determines if this task has the given property. See here for details of the properties which are available for a task.

### onlyIf(onlyIfClosure)

Execute the task only if the given closure returns true. The closure will be evaluated at task execution time, not during configuration. The closure will be passed a single parameter, this task. If the closure returns false, the task will be skipped.


