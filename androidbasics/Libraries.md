
## Prefab vs AAR

### Prefab

Use Prefab when you are dealing with native code dependencies, especially libraries written in C or C++.

### AAR

Use AAR when you are packaging and distributing Android libraries that include both compiled code and resources, and when you want to share your libraries with other Android projects.

AAR can contain prefab artifacts

#### What does .AAR file contain?

1. (mandatory) classes and methods - `.class` and `.dex`
2. (mandatory) `R.txt` - for resources
3. (mandatory) `AndroidManifest.xml` - for permissions and other metadata
4. (mandatory) `res/` - for resources
5. `assets/` - for assets
6. `/libs/name.jar` 
7. `proguard.txt` - for proguard rules
8. `/jni/abi_name/name.so` (where abi_name is one of the Android-supported ABIs)
9. `/prefab/` for exporting native libraries
10. `/public.txt`
11. **Dependencies Metadata** - Information about the dependencies of your library, including other AARs or JARs that it relies on. This metadata helps in managing the transitive dependencies when the library is included in an Android project. (e.g. `transitive=true` with aar recursively resolves dependencies). When an AAR is published to a Maven repository, the `pom.xml` file is usually included alongside the AAR file. This allows other projects that depend on the library to retrieve the necessary information about the library and its dependencies from the Maven repository.






