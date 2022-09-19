

## How it works

Dependency resolution is a process that consists of two phases, which are repeated until the dependency graph is complete:

* When a new dependency is added to the graph, **perform conflict resolution** to determine which version should be added to the graph.

* When a specific dependency, that is a module with a version, is identified as part of the graph, **retrieve its metadata so that its dependencies can be added in turn.**



## Version conflict resolution

Gradle will consider all requested versions, wherever they appear in the dependency graph. 
**Out of these versions, it will select the highest one.**

