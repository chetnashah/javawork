

## Repository

A repository **hosts a set of modules**, each of which may provide `one or many releases (components) indicated by a module version`. 

The repository can be based on a binary repository product (e.g. Artifactory or Nexus) or a directory structure in the filesystem

## Artifact

A file or directory produced by a build, such as a JAR, a ZIP distribution, or a native executable.

## Component

**Any single version of a module.**

For external libraries, the term component refers to one published version of the library.


## Dependency

A dependency is a `pointer to another piece of software required` to build, test or run a module.

## Module

A piece of software that evolves over time e.g. Google Guava. Every module has a name. Each release of a module is optimally represented by a module version. 

### Module metadata

Releases of a module provide metadata. Metadata is the data that describes the module in more detail e.g. information about the location of artifacts or required transitive dependencies. Gradle offers its own metadata format called Gradle Module Metadata (.module file) but also supports Maven (.pom) and Ivy (ivy.xml) metadata. See the section on understanding Gradle Module Metadata for more information on the supported metadata formats.


## Platform

A platform is a set of modules aimed to be used together.


