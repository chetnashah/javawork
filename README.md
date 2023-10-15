

A quick and easy way to make gradle java project(with conventions) 
from command line is to use gradle init like so:

To make an application :

gradle init --type java-application

To make a library :

gradle init --type java-library

basic :

gradle init --type basic




javap is the java class file disassembler.
javap -c includes bytecode.
javap -c -verbose is more verbose


### JNI code for JDK sits in `jdk/src/java.base/share/native/libjava/`
e.g. `FileInputStream.c` which has native methods implemented 
for `FileInputStream.java`.

## JDK 17 deprecations

Use MaxMetaspaceSize in place of MaxPermSize and MetaspaceSize in place of PermSize in jdk17