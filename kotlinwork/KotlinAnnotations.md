https://proandroiddev.com/how-kotlin-annotations-work-8d06798a32d2

## Annotation processors are compiler plugins

There are three main APIs for creating annotation processors: 
1. Annotation Processor Tool (APT)  - JAVA only, `javac` based, corresponds to the `annotationProcessor` dependency configuration in Gradle.
2. Kotlin Annotation Processor Tool (kapt) - It’s a compiler plugin built on top of APT and supports both Kotlin and Java source code. It corresponds to the kapt dependency configuration. KotlinPoet is a popular library for generating Kotlin source code during processing.
3. Kotlin Symbol Processing (KSP). 

Processors created with `APT` and `kapt` extend the same base `AbstractProcessor` class, whereas KSP has a separate `SymbolProcessor` class.



## Annotations are a means of attaching metadata to code

Annotations are a means of attaching metadata to code. To declare an annotation, put the annotation modifier in front of a class:
```
annotation class CustomAnnotation
```
We can annotate it with meta-annotations to provide additional details:

```kotlin
@Target(AnnotationTarget.FUNCTION, AnnotationTarget.TYPE_PARAMETER)
@Retention(AnnotationRetention.SOURCE)
annotation class CustomAnnotation
```
`@Target` specifies the possible kinds of elements which can be annotated with the annotation (such as classes, functions, parameters), and `@Retention` specifies whether the annotation is stored in binary output and whether it’s visible for reflection at runtime. In its default value `RUNTIME`, both are true.

