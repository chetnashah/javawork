
The Java Platform plugin brings the ability to declare platforms for the Java ecosystem. A platform can be used for different purposes:

1. a description of modules which are published together (and for example, share the same version)

2. a set of recommended versions for heterogeneous libraries. A typical example includes the Spring Boot BOM

3. sharing a set of dependency versions between subprojects

A platform is a special kind of software component which doesn’t contain any sources: it is only used to reference other libraries, so that they play well together during dependency resolution.

## Usage

```
plugins {
    `java-platform`
}
```