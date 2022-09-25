
## Lifecycle

There is a one-to-one relationship between a Project and a build.gradle file. During build initialisation, Gradle assembles a Project object for each project which is to participate in the build, as follows:

1. Create a Settings instance for the build.
2. Evaluate the `settings.gradle` script, if present, against the `Settings` object to configure it.
3. Use the configured Settings object to create the `hierarchy of Project instances`.
4. Finally, **evaluate each Project by executing its build.gradle file, if present, against the project**. **The projects are evaluated in breadth-wise order, such that a project is evaluated before its child projects**. This order can be overridden by calling `Project.evaluationDependsOnChildren()` or by adding an explicit evaluation dependency using `Project.evaluationDependsOn(java.lang.String)`.




## Common methods

### afterEvaluate

**Adds a closure to be called immediately after this project has been evaluated.** The project is passed to the closure as a parameter. Such a listener gets notified when the build file belonging to this project has been executed. A parent project may for example add such a listener to its child project. Such a listener can further configure those child projects based on the state of the child projects after their build files have been run.

### allProjects({})

Configures this project and each of its sub-projects.

### apply({})

Applies zero or more plugins or scripts.

### beforeEvaluate

Adds a closure to be called immediately before this project is evaluated. The project is passed to the closure as a parameter.

### evaulationDependsOn

Declares that this project has an evaluation dependency on the project with the given path.

### copy

Copies the specified files. The given closure is used to configure a CopySpec, which is then used to copy the files. Example:

### exec

Executes an external command. The closure configures a ExecSpec.

### file

Resolves a file path relative to the project directory of this project. This method converts the supplied path based on its type.

### fileTree(baseDir)

Creates a new ConfigurableFileTree using the given base directory. The given baseDir path is evaluated as per Project.file(java.lang.Object).

### files(paths)

Returns a `ConfigurableFileCollection` containing the given files. You can pass any of the following types to this method:

### findProject(path)

Locates a project by path. If the path is relative, it is interpreted relative to this project.

### getTasksByName(name,recursive)

Returns the set of tasks with the given name contained in this project, and optionally its subprojects. NOTE: This is an expensive operation since it requires all projects to be configured.

### hasProperty(propName)

Determines if this project has the given property. See here for details of the properties which are available for a project.

### property(propertyName)

Returns the value of the given property

### relativePath(path)

Returns the relative path from the project directory to the given path. The given path object is (logically) resolved as described for Project.file(java.lang.Object), from which a relative path is calculated.

### subProjects(action)

Configures the sub-projects of this project

### sync

Synchronizes the contents of a destination directory with some source directories and files. The given action is used to configure a SyncSpec, which is then used to synchronize the files.

### task(name)

Creates a Task with the given name and adds it to this project. Calling this method is equivalent to calling Project.task(java.util.Map, java.lang.String) with an empty options map.

### task(name, configureClosure)

Creates a Task with the given name and adds it to this project. Before the task is returned, the given closure is executed to configure the task.


## script blocks for configuration

https://docs.gradle.org/current/dsl/org.gradle.api.Project.html#N15709


## Common properties (on project object)

### buildDir

The build directory of this project. The build directory is the directory which all artifacts are generated into. The default value for the build directory is `projectDir/build`

### dependencies

The dependency handler of this project. The returned dependency handler instance can be used for adding new dependencies. For accessing already declared dependencies, the configurations can be used.

### extensions

Allows adding DSL extensions to the project. Useful for plugin authors.

### parent

The parent project of this project, if any.

### path

The path of this project. The path is the fully qualified name of the project.

### project

Returns this project. This method is useful in build files to explicitly access project properties and methods. For example, using project.name can express your intent better than using name. This method also allows you to access project properties from a scope where the property may be hidden, such as, for example, from a method or closure.

### projectDir

The directory containing the project build file (i.e. build.gradle)

### rootDir

root project's directory.

### subprojects

The set containing the subprojects of this project.

### TaskContainer tasks

The tasks of this project, which is instance of `TaskContainer`.

## Searching for dynamic properties

A project has 5 property 'scopes', which it searches for properties. You can access these properties by name in your build file, or by calling the project's Project.property(java.lang.String) method. The scopes are:

1. The **Project object itself**. This scope includes any property getters and setters declared by the Project implementation class. For example, Project.getRootProject() is accessible as the rootProject property. The properties of this scope are readable or writable depending on the presence of the corresponding getter or setter method.

2. The **extra properties** of the project. Each project maintains a map of extra properties, which can contain any arbitrary name -> value pair. Once defined, the properties of this scope are readable and writable. See extra properties for more details.

3. The extensions added to the project by the plugins. **Each extension is available as a read-only property with the same name as the extension.**

4. The **convention properties** added to the project by the plugins. A plugin can add properties and methods to a project through the project's Convention object. The properties of this scope may be readable or writable, depending on the convention objects.

5. The **tasks of the project. A task is accessible by using its name as a property name**. The properties of this scope are read-only. For example, a task called compile is accessible as the compile property.

6. The extra properties and convention properties are inherited from the project's parent, recursively up to the root project. The properties of this scope are read-only.


## Common properties added by java plugin on project

### archiveBaseName

The base name to use for archive files.

### base 

The BasePluginExtension added by the java plugin.

### java

The JavaPluginExtension added by the java plugin.

### libsDirectory

The directory to generate JAR and WAR archives into.

### sourceSets

The source sets container.

### testReportDir

Returns a file pointing to the root directory to be used for reports.

### testResultsDir	

Returns a file pointing to the root directory of the test results.




