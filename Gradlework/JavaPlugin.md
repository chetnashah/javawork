
## Conventions

* It defines the directory `src/main/java` as the default source directory for compilation.

* The output directory for compiled source code and other artifacts (like the JAR file) is `build`.

## Exercises

https://github.com/gradle/build-tool-training-exercises

## Where to put generated sources

Put them in `build/generated/sources`

## We would have a task that put code in folder mentioned above

```groovy
tasks.named("generateMlCode") {
    //copy from somewhere
    // to build/generated/sources/mlcode
}
```

## How can main source set use generated sources?

```groovy
sourceSets {
    main {
        java {
            srcDir(tasks.named("generateMlCode")) // implicitly runs this dependent task, we use the task's output.
        }
    }
}
```


## What is part of sourceset?

```
[main]
   srcDirs:
      src/main/resources
      src/main/java
      src/main/kotlin
   outputs:
      build/classes/java/main
      build/classes/kotlin/main
   impl dependency configuration:
      implementation
   compile task:
      compileJava
   compile classpath:
      joda-time/joda-time/2.11.1/175a59c4c86fef3800c40ef37f61425a31976f13/joda-time-2.11.1.jar
      com.google.guava/guava/31.1-jre/60458f877d055d0c9114d9e1a2efb737b4bc282c/guava-31.1-jre.jar
      org.jetbrains.kotlin/kotlin-stdlib-jdk8/1.7.10/d70d7d2c56371f7aa18f32e984e3e2e998fe9081/kotlin-stdlib-jdk8-1.7.10.jar
      org.jetbrains.kotlin/kotlin-stdlib-jdk7/1.7.10/1ef73fee66f45d52c67e2aca12fd945dbe0659bf/kotlin-stdlib-jdk7-1.7.10.jar
      org.jetbrains.kotlin/kotlin-stdlib/1.7.10/d2abf9e77736acc4450dc4a3f707fa2c10f5099d/kotlin-stdlib-1.7.10.jar
      org.jetbrains.kotlin/kotlin-stdlib-common/1.7.10/bac80c520d0a9e3f3673bc2658c6ed02ef45a76a/kotlin-stdlib-common-1.7.10.jar
      com.google.guava/failureaccess/1.0.1/1dcf1de382a0bf95a3d8b0849546c88bac1292c9/failureaccess-1.0.1.jar
      com.google.guava/listenablefuture/9999.0-empty-to-avoid-conflict-with-guava/b421526c5f297295adef1c886e5246c39d4ac629/listenablefuture-9999.0-empty-to-avoid-conflict-with-guava.jar
      com.google.code.findbugs/jsr305/3.0.2/25ea2e8b0c338a877313bd4672d3fe056ea78f0d/jsr305-3.0.2.jar
      org.checkerframework/checker-qual/3.12.0/d5692f0526415fcc6de94bb5bfbd3afd9dd3b3e5/checker-qual-3.12.0.jar
      com.google.errorprone/error_prone_annotations/2.11.0/c5a0ace696d3f8b1c1d8cc036d8c03cc0cbe6b69/error_prone_annotations-2.11.0.jar
      com.google.j2objc/j2objc-annotations/1.3/ba035118bc8bac37d7eff77700720999acd9986d/j2objc-annotations-1.3.jar
      org.jetbrains/annotations/13.0/919f0dfe192fb4e063e7dacadee7f8bb9a2672a9/annotations-13.0.jar

[test]
   srcDirs:
      src/test/resources
      src/test/java
      src/test/kotlin
   outputs:
      build/classes/java/test
      build/classes/kotlin/test
   impl dependency configuration:
      testImplementation
   compile task:
      compileTestJava
   compile classpath:
      build/classes/java/main
      build/classes/kotlin/main
      build/resources/main
      joda-time/joda-time/2.11.1/175a59c4c86fef3800c40ef37f61425a31976f13/joda-time-2.11.1.jar
      com.google.guava/guava/31.1-jre/60458f877d055d0c9114d9e1a2efb737b4bc282c/guava-31.1-jre.jar
      org.jetbrains.kotlin/kotlin-stdlib-jdk8/1.7.10/d70d7d2c56371f7aa18f32e984e3e2e998fe9081/kotlin-stdlib-jdk8-1.7.10.jar
      org.jetbrains.kotlin/kotlin-test-junit5/1.7.10/1977faecddc1c312be4775e6110d3072f553d89d/kotlin-test-junit5-1.7.10.jar
      org.jetbrains.kotlin/kotlin-test/1.7.10/4d644a88cc0a386712d4b1c1b4ca748203421e07/kotlin-test-1.7.10.jar
      org.junit.jupiter/junit-jupiter-params/5.8.2/ddeafe92fc263f895bfb73ffeca7fd56e23c2cce/junit-jupiter-params-5.8.2.jar
      org.junit.jupiter/junit-jupiter-api/5.8.2/4c21029217adf07e4c0d0c5e192b6bf610c94bdc/junit-jupiter-api-5.8.2.jar
      org.junit.platform/junit-platform-commons/1.8.2/32c8b8617c1342376fd5af2053da6410d8866861/junit-platform-commons-1.8.2.jar
      org.junit.jupiter/junit-jupiter/5.8.2/5a817b1e63f1217e5c586090c45e681281f097ad/junit-jupiter-5.8.2.jar
      org.jetbrains.kotlin/kotlin-stdlib-jdk7/1.7.10/1ef73fee66f45d52c67e2aca12fd945dbe0659bf/kotlin-stdlib-jdk7-1.7.10.jar
      org.jetbrains.kotlin/kotlin-stdlib/1.7.10/d2abf9e77736acc4450dc4a3f707fa2c10f5099d/kotlin-stdlib-1.7.10.jar
      org.jetbrains.kotlin/kotlin-stdlib-common/1.7.10/bac80c520d0a9e3f3673bc2658c6ed02ef45a76a/kotlin-stdlib-common-1.7.10.jar
      com.google.guava/failureaccess/1.0.1/1dcf1de382a0bf95a3d8b0849546c88bac1292c9/failureaccess-1.0.1.jar
      com.google.guava/listenablefuture/9999.0-empty-to-avoid-conflict-with-guava/b421526c5f297295adef1c886e5246c39d4ac629/listenablefuture-9999.0-empty-to-avoid-conflict-with-guava.jar
      com.google.code.findbugs/jsr305/3.0.2/25ea2e8b0c338a877313bd4672d3fe056ea78f0d/jsr305-3.0.2.jar
      org.checkerframework/checker-qual/3.12.0/d5692f0526415fcc6de94bb5bfbd3afd9dd3b3e5/checker-qual-3.12.0.jar
      com.google.errorprone/error_prone_annotations/2.11.0/c5a0ace696d3f8b1c1d8cc036d8c03cc0cbe6b69/error_prone_annotations-2.11.0.jar
      com.google.j2objc/j2objc-annotations/1.3/ba035118bc8bac37d7eff77700720999acd9986d/j2objc-annotations-1.3.jar
      org.jetbrains/annotations/13.0/919f0dfe192fb4e063e7dacadee7f8bb9a2672a9/annotations-13.0.jar
      org.opentest4j/opentest4j/1.2.0/28c11eb91f9b6d8e200631d46e20a7f407f2a046/opentest4j-1.2.0.jar
      org.apiguardian/apiguardian-api
```

## How to get source set info using tasks?

```groovy
tasks.register("sourceSetsInfo") {
    doLast {
        val projectPath = layout.projectDirectory.asFile.toPath()
        val gradleHomePath: Path = gradle.gradleUserHomeDir.toPath()
        val cachePath: Path = Paths.get(gradleHomePath.toString(), "caches/modules-2/files-2.1/")


        sourceSets.forEach {
            val sourceSet = it
            println()
            println("[" + sourceSet.name + "]")

            println("   srcDirs:")
            sourceSet.allSource.srcDirs.forEach {
                println("      " + projectPath.relativize(it.toPath()))
            }

            println("   outputs:")
            sourceSet.output.classesDirs.files.forEach {
                println("      " + projectPath.relativize(it.toPath()))
            }
            println("   impl dependency configuration:")
            println("      " + sourceSet.implementationConfigurationName)

            println("   compile task:")
            println("      " + sourceSet.compileJavaTaskName)

            println("   compile classpath:")
            sourceSet.compileClasspath.files.forEach {
                if (it.toString().contains(".gradle/")) {
                    println("      " + cachePath.relativize(it.toPath()))
                } else {
                    println("      " + projectPath.relativize(it.toPath()))
                }
            }
        }
    }
}
```

## Source sets introduce their own dependency configuration

e.g.


## Java versions for task vs compiling source code

can be different
java_home can be jdk 8, but we can use jdk11 for compiling source code.

```
gradlew -q javaToolchains
```

specify toolcahin via

```
java {
    toolchain {
        Languageversion.set(of(11)) // 
    }
}
```

## What are (dependency) configurations

Collection of dependencies, used by other tasks etc of similar type as configuration.

`configurations` is a property available at a `project` level by default, which represents a collection of configurations

sourceset creation via `sourcesets.create('XYZ')` with name "XYZ" will create `compileXYZJava` task and `compileXYZKotlin`.

