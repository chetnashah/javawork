The **JNI Invocation Interface** is a part of the Java Native Interface (JNI) that enables native (non-Java) applications, such as C or C++ programs, to create and manage a Java Virtual Machine (JVM) instance. This interface allows native programs to embed and interact with Java code by invoking and executing Java methods from within the native environment.

Normally, Java applications run within the JVM launched by the `java` command-line tool. However, the JNI Invocation Interface enables the reverse scenario—where the JVM is embedded into a native application and controlled programmatically.

### Key Features of the JNI Invocation Interface
1. **Starting the JVM**:
   The invocation interface provides functions to start the JVM (create an instance of the JVM from native code).

2. **Interacting with Java Code**:
   Native code can invoke Java methods, access Java objects, and use Java features through the JNI Environment (`JNIEnv`) provided after initializing the JVM.

3. **Shutting Down the JVM**:
   The interface also provides facilities to destroy the JVM when the application no longer needs it.

4. **Embedding Java in Native Applications**:
   Useful for integrating Java libraries or components into existing native codebases, such as GUI applications, game engines, or system-level software.

---

### Common Functions in the Invocation Interface
The primary functions provided by the JNI Invocation Interface (declared in `jni.h`) are as follows:

1. **`JNI_CreateJavaVM`**:
   This function initializes and starts a JVM instance. It takes parameters to configure the JVM and returns a `JNIEnv` pointer, which is used to interact with Java code.

   ```c
   jint JNI_CreateJavaVM(JavaVM **pvm, void **penv, void *args);
   ```

   - `pvm`: A pointer to the JVM instance.
   - `penv`: A pointer to the JNI environment (`JNIEnv`) used to call Java methods and access Java objects.
   - `args`: JVM initialization arguments (e.g., classpath, heap size).

2. **`JNI_GetDefaultJavaVMInitArgs`**:
   This function retrieves the default initialization arguments for the JVM. It allows you to customize the JVM startup configuration.

   ```c
   jint JNI_GetDefaultJavaVMInitArgs(void *args);
   ```

3. **`JNI_GetCreatedJavaVMs`**:
   This function retrieves all currently created JVMs in the process. It is useful in scenarios where multiple JVMs are embedded.

   ```c
   jint JNI_GetCreatedJavaVMs(JavaVM **vmBuf, jsize bufLen, jsize *nVMs);
   ```

---

### Example Usage
Below is a minimal example of using the JNI Invocation Interface to create a JVM, invoke a Java method, and shut down the JVM from C/C++ code.

```c
#include <jni.h>
#include <stdio.h>

int main() {
    JavaVM *jvm;        // Pointer to the JVM
    JNIEnv *env;        // Pointer to native interface
    JavaVMInitArgs vm_args; // JVM initialization arguments
    JavaVMOption options[1];

    // Set the classpath for the JVM
    options[0].optionString = "-Djava.class.path=.";
    vm_args.version = JNI_VERSION_1_8; // Minimum Java version
    vm_args.nOptions = 1;
    vm_args.options = options;
    vm_args.ignoreUnrecognized = JNI_FALSE;

    // Create the JVM
    jint res = JNI_CreateJavaVM(&jvm, (void **)&env, &vm_args);
    if (res < 0) {
        printf("Failed to create JVM\n");
        return 1;
    }

    // Find the Java class
    jclass cls = (*env)->FindClass(env, "HelloWorld");
    if (cls == NULL) {
        printf("Class not found\n");
        (*jvm)->DestroyJavaVM(jvm);
        return 1;
    }

    // Find the static method to be called
    jmethodID mid = (*env)->GetStaticMethodID(env, cls, "sayHello", "()V");
    if (mid == NULL) {
        printf("Method not found\n");
        (*jvm)->DestroyJavaVM(jvm);
        return 1;
    }

    // Call the static method
    (*env)->CallStaticVoidMethod(env, cls, mid);

    // Destroy the JVM
    (*jvm)->DestroyJavaVM(jvm);

    return 0;
}
```

### Explanation of the Example
1. **Setting JVM Options**:
   - The `-Djava.class.path=.` option sets the classpath for locating Java classes.

2. **Creating the JVM**:
   - `JNI_CreateJavaVM` initializes the JVM and provides access to the `JNIEnv` interface.

3. **Finding the Class and Method**:
   - `FindClass` locates the Java class (`HelloWorld`).
   - `GetStaticMethodID` retrieves the method ID for the static method `sayHello`.

4. **Invoking the Java Method**:
   - `CallStaticVoidMethod` calls the static method.

5. **Destroying the JVM**:
   - `DestroyJavaVM` shuts down the JVM to release resources.

---

### When to Use the JNI Invocation Interface
- **Embedding Java in Native Applications**:
  If you have a native application written in C/C++ but need to use Java libraries or components, the JNI Invocation Interface provides a way to do so.

- **Interoperability**:
  When you need to integrate system-level functionality written in native code with the portability and flexibility of Java.

- **Custom JVM Management**:
  In cases where you need more control over the JVM lifecycle than a typical Java application allows.

---

### Limitations
1. **Single JVM per Process**:
   Most JVM implementations (e.g., HotSpot) only allow a single JVM instance per process.

2. **Performance Overhead**:
   There is some overhead in transitioning between native and Java code.

3. **Complexity**:
   JNI code is more complex and error-prone compared to purely Java-based programming.