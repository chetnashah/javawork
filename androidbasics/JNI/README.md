
## static linking vs dynamic linking

### Common scenario - linking to a shared library (.so) and using it via System.loadLibrary()

The Java SE 8 specification has been changed to support static linking, and static linking is implemented in the JDK. This is mentioned briefly in the spec for System.loadLibrary. The sections of the JNI Specification to which it refers are here and here.

Native method signatures and data types are the same for statically and dynamically linked methods. You might have to hack on the JDK makefiles to get it to link your library statically, though.

One significant difference is the way static libraries are initialized. Dynamic libraries are initialized by calling the `JNI_OnLoad` function and are deinitialized by calling `JNI_OnUnload`. Each dynamic library can have its own version of these functions. 




### Uncommon scenario - static linking

If there are multiple statically linked libraries, clearly they can't all have functions with these same names. For a static library named libname the load/unload functions are `JNI_OnLoad_libname` and `JNI_OnUnload_libname`.

The `JNI_OnLoad_libname` function must return a value of JNI_VERSION_1_8 or higher. If it doesn't, the JVM will ignore the static library.

Basically, if you call `System.loadLibrary("foo")`, the system looks for the function `JNI_OnLoad_foo` in the running executable image, and if it's found, it assumes that the library is statically linked, and its native methods are searched for within the running image. If `JNI_OnLoad_foo` is not found, then the usual searching and loading of dynamic libraries takes place, and native methods are linked from the dynamic library so found.
