

## View the task dependency tree

https://www.youtube.com/watch?v=gTYHEcSbdes

https://gitlab.com/barfuin/gradle-taskinfo

basically add this plugin:
`id 'org.barfuin.gradle.taskinfo' version '1.0.5'`

and excute tiTree along with your actual task
e.g.
`./gradlew tiTree build`

will give output like:
```
> Task :tiTree
:build                                          (org.gradle.api.DefaultTask)
+--- :assemble                                  (org.gradle.api.DefaultTask)
|    `--- :jar                                  (org.gradle.api.tasks.bundling.Jar)
|         `--- :classes                         (org.gradle.api.DefaultTask)
|              +--- :compileJava                (org.gradle.api.tasks.compile.JavaCompile)
|              `--- :processResources           (org.gradle.language.jvm.tasks.ProcessResources)
`--- :check                                     (org.gradle.api.DefaultTask)
     `--- :test                                 (org.gradle.api.tasks.testing.Test)
          +--- :classes                         (org.gradle.api.DefaultTask)
          |    +--- :compileJava                (org.gradle.api.tasks.compile.JavaCompile)
          |    `--- :processResources           (org.gradle.language.jvm.tasks.ProcessResources)
          `--- :testClasses                     (org.gradle.api.DefaultTask)
               +--- :compileTestJava            (org.gradle.api.tasks.compile.JavaCompile)
               |    `--- :classes               (org.gradle.api.DefaultTask)
               |         +--- :compileJava      (org.gradle.api.tasks.compile.JavaCompile)
               |         `--- :processResources (org.gradle.language.jvm.tasks.ProcessResources)
               `--- :processTestResources       (org.gradle.language.jvm.tasks.ProcessResources)
```