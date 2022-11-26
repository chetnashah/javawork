

## Creating extra properties


In the project level `build.gradle.kts`:
```
val ktorVersion by extra { "1.3.2" }
```
In the `subproject/build.gradle.kts`:
```
val ktorVersion: String by rootProject.extra

dependencies {  
   implementation("io.ktor:ktor-server-core:$ktorVersion")
}
```
For more info: Gradle docs on Extra properties.

## Declaring dependencies

Instead of 
```
   implementation "io.ktor:ktor-server-core:$ktorVersion"
```

prefer function call like syntax
```
   implementation("io.ktor:ktor-server-core:$ktorVersion")
```



