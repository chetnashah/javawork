

Read: http://www.bracha.org/mirrors.pdf

## 

Annotation processing is a standard feature of the Java SE platform, using standard APIs. **Annotation processors are executed by javac while compiling code, and may even create additional files to be compiled.** Despite the name, annotation processors are not restricted to just processing annotations, and may be used to analyze any classes involved in a compilation, whether found in source form or compiled class form, and whether or not they contain any annotations. An annotation processor may also access the documentation comments for declarations found in source files: it may access the comment as either raw text or as a parsed DocCommentTree.

**Annotation processing occurs at a specific point in the timeline of a compilation, after all source files and classes specified on the command line have been read, and analyzed for the types and members they contain, but before the contents of any method bodies have been analyzed.**



## Where can annotations be applied?

Annotations can be applied to declarations: declarations of `classes`, `fields`, `methods`, and other program elements.

Before the Java SE 8 release, annotations could only be applied to declarations,
As of the Java SE 8 release, annotations can also be applied to the use of types. This form is known as `type annotations`.
e.g. ` myString = (@NonNull String) str;`

With the judicious use of type annotations and the presence of pluggable type checkers, you can write code that is stronger and less prone to error.

https://checkerframework.org/

## Define annotations with `@Interface AnnotationName`

Example:
```java
@interface ClassPreamble {
   String author();
   String date();
   int currentRevision() default 1;
   String lastModified() default "N/A";
   String lastModifiedBy() default "N/A";
   // Note use of array
   String[] reviewers();
}
```


For runtime Annotation processing, it is simple to use `java.lang.reflect` apis to get `Class` meta data, and get annotations with (runtime retention). e.g. Retrofit uses this idea for its `@GET`, `@POST` etc on methods.

For compile time annotation processing, `javax.lang.model.*` APIs along with processing Rounds.

## Annotations That Apply to Other Annotations

### @Retention

@Retention `@Retention` annotation specifies how the marked annotation is stored:

* `RetentionPolicy.SOURCE` – The marked annotation is retained only in the source level and is ignored by the compiler.
* `RetentionPolicy.CLASS` – The marked annotation is retained by the compiler at compile time, but is ignored by the Java Virtual Machine (JVM).
* `RetentionPolicy.RUNTIME` – The marked annotation is retained by the JVM so it can be used by the runtime environment.

### `@Target`

@Target `@Target` annotation marks another annotation to restrict what kind of Java elements the annotation can be applied to. A target annotation specifies one of the following element types as its value:

* ElementType.ANNOTATION_TYPE can be applied to an annotation type.
* ElementType.CONSTRUCTOR can be applied to a constructor.
* ElementType.FIELD can be applied to a field or property.
* ElementType.LOCAL_VARIABLE can be applied to a local variable.
* ElementType.METHOD can be applied to a method-level annotation.
* ElementType.PACKAGE can be applied to a package declaration.
* ElementType.PARAMETER can be applied to the parameters of a method.
* ElementType.TYPE can be applied to any element of a class.

### `@Repeatable`

@Repeatable `@Repeatable` annotation, introduced in Java SE 8, indicates that the marked annotation can be applied more than once to the same declaration or type use.

## Important types in javax.lang.model

https://youtu.be/IYceJO8WPE4?t=1894

types represent nodes in the Abstract Syntax Tree of a compiler that calls your annotation processor.

## javax.lang.model.AnnotatedConstruct

Represents a construct that can be annotated. A construct is either an `Element` or a `TypeMirror`. 

Annotations on an element are on a declaration, whereas annotations on a type are on a specific use of a type name. 

The terms directly present, present, indirectly present, and associated are used throughout this interface to describe precisely which annotations are returned by the methods defined herein.


## Element extends AnnotatedConstruct

Represents a program element such as a `package`, `class`, or `method`. Each element represents a static, language-level construct (and not, for example, a runtime construct of the virtual machine).

example of executable element -> method
Example of variable element -> field, params
example of type element -> classes, interfaces

Enclosing of elements:
`A class or interface is considered to enclose the fields, methods, constructors, and member types that it directly declares.` 

`A package encloses the top-level classes and interfaces within it, but is not considered to enclose subpackages.`

models the static structure of the program - think of all you see in the package explorer of Eclipse

## TypeElement extends Element

**Represents a class or interface program element.** Provides access to information about the type and its members. Note that an enum type is a kind of class and an annotation type is a kind of interface.

While a `TypeElement` represents a class or interface element(static), a `DeclaredType` represents a class or interface type(Runtime?), the latter being a use (or invocation) of the former. The distinction is most apparent with generic types, for which a single element can define a whole family of types. For example, the element `java.util.Set` corresponds to the parameterized types `java.util.Set<String>` and `java.util.Set<Number>` (and many others), and to the raw type java.util.Set.



## TypeMirror extends AnnotatedConstruct

Represents a type in the Java programming language. Types include `primitive types`, `declared types` (`class` and `interface` types), `array types`, `type variables`, and the `null type`. Also represented are wildcard type arguments, the signature and return types of executables, and pseudo-types corresponding to packages and to the keyword void.

## ReferenceType extends TypeMirror

Represents a reference type. These include `class` and `interface types`, `array types`, `type variables`, and the `null type`.

## DeclaredType extends ReferenceType

Represents a declared type, either a `class type` or an `interface type`. This includes parameterized types such as `java.util.Set<String>` as well as raw types.
While a `TypeElement` represents a class or interface element, a `DeclaredType` represents a class or interface type, the latter being a use (or invocation) of the former. See TypeElement for more on this distinction.

## TypeKind

The kind of a type mirror.

Enum with following values:
* `ARRAY` - An array type.
* `BOOLEAN` -The primitive type boolean.
* `BYTE` - The primitive type byte.
* `CHAR` - The primitive type char.
* `DECLARED` - A class or interface type.
* `DOUBLE` - The primitive type double.
* `ERROR` - A class or interface type that could not be resolved.
* `EXECUTABLE` - A method, constructor, or initializer.
* `FLOAT` - The primitive type float.
* `INT` - The primitive type int.
* `INTERSECTION` - An intersection type.
* `LONG` - The primitive type long.
* `NONE` - A pseudo-type used where no actual type is appropriate.
* `NULL` - The null type.
* `OTHER` - An implementation-reserved type.
* `PACKAGE` - A pseudo-type corresponding to a package element.
* `SHORT` - The primitive type short.
* `TYPEVAR` - A type variable.
* `UNION` - A union type.
* `VOID` - The pseudo-type corresponding to the keyword void.
* `WILDCARD` -A wildcard type argument.


### Util classes Types and Elements in javax.lang.model

https://docs.oracle.com/javase/7/docs/api/javax/lang/model/util/Elements.html - get via `processingEnv.getElementUtils()`

https://docs.oracle.com/javase/7/docs/api/javax/lang/model/util/Types.html - get via `processingEvn.getTypeUtils()`.

### Converting TypeElement to TypeMirror/DeclaredType and vice versa

`List<E>` is like TypeElement, `List<String>` is like TypeMirror.

TypeMirror -> TypeElement

```java
Types typeUtils = processingEnv.getTypeUtils();
TypeElement typeElement = (TypeElement) typeUtils.asElement(typeMirror);
```

TypeElement -> TypeMirror

```java
TypeMirror typeMirror = typeElement.asType();
// or
DeclaredType typeMirror = typeUtils.getDeclaredType(typeElement);
```

Comparing type mirrors:
```java
// Use following
Types.isSameType(mirror1, mirror2);
```

### TypeElement vs TypeMirror

https://stackoverflow.com/questions/69518703/typeelement-vs-typemirror-as-data-models-for-java-annotation-processor

`TypeElement` - represents class declaration and contains all the information about the declaration itself.
annotations of the declared class or method - are available in corresponding TypeElement/ExecutableElement, but not in TypeMirror. the TypeElement of Foo will have information about type parameters, their constraints, bounds, etc.

`TypeMirror` - represents the occurance (the specific usage) of the class in the code and has all the information, which is specific to that occurance. From the occurance you always can get the declaration, but not vice versa.

## java.lang.Annotation

The common interface extended by all annotation types.


## In java.lang.Reflect (runtime?)

### Interface java.lang.reflect.AnnotatedElement

Represents an annotated element of the program currently running in this VM. This interface allows annotations to be read reflectively. All annotations returned by methods in this interface are immutable and serializable.

### java.lang.reflect.AnnotatedType extends java.lang.reflect AnnotatedElement

`AnnotatedType` represents the potentially annotated use of a type in the program currently running in this VM. The use may be of any type in the Java programming language, including an array type, a parameterized type, a type variable, or a wildcard type.

### java.lang.reflect.Type

Type is the common superinterface for all types in the Java programming language. These include `raw types`, `parameterized types`, `array types`, `type variables` and `primitive types`.

## In java.lang

### java.lang.Class extends java.lang.Object, implements java.lang.reflect.Type