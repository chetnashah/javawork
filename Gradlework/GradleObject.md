
## The god object

Referred using `project.gradle.XXX` or `gradle.XXX` in a `build.gradle` file

https://docs.gradle.org/current/javadoc/org/gradle/api/invocation/Gradle.html

## Important properties

### includedBuilds: `Collection<IncludedBuild>`



## Important listeners/configurators

### allProjects (closure)

### buildFinished (closure)

### projectsEvaluated (closure)

### projectsLoaded (closure)

### settingsEvaluated (closure)


### rootProject

Get root project of this build

### taskGraph

Return `TaskExecutionGraph` for this build.

### includeBuild(String name)

include a build with specified name