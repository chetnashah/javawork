

## TaskContainer

A TaskContainer is responsible for managing a set of Task instances.

You can obtain a TaskContainer instance by calling Project.getTasks(), or using the tasks property in your build script.

### Useful methods on TaskContainer

### 

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


