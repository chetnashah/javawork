
Why is defining equals(), hashcode() important?
It will define how your value classes will behave with well known collections.
e.g. Set, HashMap etc.

Equals contract:

equals() should be defined for value classes(e.g. Person, Car) etc.
for value equality semantics.
The equals methods compares two objects in question(self and other).
1. equals() method should be equivalence relation, i.e. with self and other,
	they should be reflexive, symmetric and transitive.
2. consistent: multiple invocations of x.equals(y) must return same result, provided
	no object is modified.
3. For any non-null reference value , x.equals(null) should return false.

HashCode contract:
hashCode() method returns an integer and is supported for benifit of hasing based collection classes such as HashTable, HashMap, HashSet.(typical use of hashCode() will be for bucketing, once bucketing is done, equals() would be used for actually finding element)
1. always override hashcode when you override equals
2. two Objects with value equality should return same hashcode


### Classloaders

Classes are introduced in Java environment when they are refereneced by name in class that is already running.

Methods classloader implement in order to do their stuff(i.e find class for given name) is
``` java
Class r = loadClass(String className, boolean resolveIt);
```

The Java virtual machine has hooks in it to allow a user-defined class loader to be used in place of the primordial one. Furthermore, since the user class loader gets first crack at the class name, the user is able to implement any number of interesting class repositories, not the least of which is HTTP servers -- which got Java off the ground in the first place.

A user class loader gets the chance to load a class before the primordial class loader does. Because of this, it can load the class implementation data from some alternate source, which is how the AppletClassLoader can load classes using the HTTP protocol.

In our simple class loader, if the primordial class loader couldn't find the class, we loaded it from our private repository. What happens when that repository contains the class `java.lang.FooBar` ? There is no class named `java.lang.FooBar`, but we could install one by loading it from the class repository. This class, by virtue of the fact that it would have access to any package-protected variable in the `java.lang` package, can manipulate some sensitive variables so that later classes could subvert security measures. Therefore, one of the jobs of any class loader is to protect the system name space.

``` java
if (className.startsWith("java.")) 
    throw newClassNotFoundException(); 
```

1. Bootstrap ClassLoader (primordial classloader) is responsible for loading standard JDK class files from rt.jar and it is parent of all class loaders in Java.

2. Extension ClassLoader delegates class loading request to its parent, Bootstrap and if unsuccessful, loads class form jre/lib/ext directory or any other directory pointed by java.ext.dirs system property. Extension ClassLoader in JVM is implemented by  sun.misc.Launcher$ExtClassLoader. 

3. Third default class loader used by JVM to load Java classes is called System or Application class loader and it is responsible for loading application specific classes from CLASSPATH environment variable, -classpath or -cp command line option


### Exceptions

An exception is an event which occurs during execution of a program that disrupts normal flow of program's instruction.

#### Throwing of an exception

When a problem occurs in a method, method creates an exception object(contianing error info, state of execution etc.) and hands it off to runtime system. This is known as raising/throwing of an exception.

#### Exception handler

When problem occurs, the runtime system searches call stack from most recent method called, to find a method which has declared a block of code known as exception handler (usually via catch), and runtime passes the exception to that handler.
A method without an exception handler just forwards the exception to it's caller.

#### 3 types of exceptions

1. Checked exception (needs try/catch or throw) - These are exceptional conditions that a well-written application should anticipate and recover from. Like not finding file, not connecting to the internet etc. E.g. `IOException, FileNotFoundException etc.`.
2. Error (unchecked exception) - These are exceptional conditions that are external to the application, and that the application usually cannot anticipate or recover from. Usually programs will exit in case of Error e.g. `java.io.IOError`.
3. Runtime Exception (unchecked exception) - se are exceptional conditions that are internal to the application, and that the application usually cannot anticipate or recover from. These usually indicate programming bugs, such as logic errors or improper use of an API. e.g are `NullPointerException, IllegalArgumentException, ArithmeticException, IndexOutOfBoundsException`. A possible reason because a NullPointerException is a runtime exception is because every method can throw it, so every method would need to have a "throws NullPointerException"

#### Rationale for Checked exceptions

Any Exception that can be thrown by a method is part of the method's public programming interface. Those who call a method must know about the exceptions that a method can throw so that they can decide what to do about them. These exceptions are as much a part of that method's programming interface as its parameters and return value.
Runtime exceptions can occur anywhere in a program, and in a typical one they can be very numerous. Having to add runtime exceptions in every method declaration would reduce a program's clarity.

#### try-with-resources statement & earlier closing resources in finally

The try-with-resources statement is a try statement that declares one or more resources. A **resource** is an object that must be closed after the program is finished with it. The try-with-resources statement ensures that each resource is closed at the end of the statement. Any object that implements `java.lang.AutoCloseable`, which includes all objects which implement `java.io.Closeable`, can be used as a resource.

The following example reads the first line from a file. It uses an instance of BufferedReader to read data from the file. BufferedReader is a resource that must be closed after the program is finished with it:

``` java
static String readFirstLineFromFile(String path) throws IOException {
    // BufferedReader is AutoClosable
	try (BufferedReader br =
                   new BufferedReader(new FileReader(path))) {
        return br.readLine();
    }
}
```

Prior to Java SE 7, you can use a finally block to ensure that a resource is closed regardless of whether the try statement completes normally or abruptly.