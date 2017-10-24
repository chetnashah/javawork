
### Naming
Usually each java projects are identified by three basic components:
* group/groupId - unique among organizaation or project e.g. 'org.codehaus.mojo' or 'org.antlr'
* name/artifact-id - The name that project is known by e.g. 'commons-io' or 'antlr-runtime'
* version - the version of artifact.

Combined together they look something like, (groupId:artifactId:version) -> 
'org.hibernate:hibernate-core:3.6.7.Final'

#### Repository
A place for publishing/fetching libraries and dependencies. Can be online or local.
Well known online repositories for java/android artifacts are maven central, and jCenter.


### Understanding JAR Manifest

JAR manifest is a special file that can contain info about files packaged in a JAR file.

When you create a JAR file, it automatically receives a default manifest file. Note: There can be only one manifest file in an archive. and it always has pathname META-INF/MANIFEST.MF, the data in this file is of format ->
Attribute: Value

Setting application entry point in JAR:
Add a Main-Class attribute, with classname having psvm i.e.
Main-Class: classname

Running jar file from cmd:
``` sh
java -jar JAR_name
```

