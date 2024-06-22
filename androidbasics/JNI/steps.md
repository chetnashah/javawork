
1. Define `native` methods in `.java` file
2. Comple `.java` -> `.class` file.
3. Generate `.h` file from `.class` file using `javah` command.
4. implement the native methods in `.cpp` file.
5. Create a shared library from the implementation file that creates a `.so` file.
6. Load the shared library in the `.java` file using `System.loadLibrary()` method in a static block.
7. Call the native methods in the `.java` file.

```java
public class HelloJNI {
    static {
        System.loadLibrary("hello"); // Load native library at runtime
    }

    // Declare a native method sayHello() that receives no arguments and returns void
    private native void sayHello();

    // Test Driver
    public static void main(String[] args) {
        new HelloJNI().sayHello();  // Invoke the native method
    }
}
```

```c++
#include <jni.h>
#include <iostream>
#include "HelloJNI.h"

JNIEXPORT void JNICALL Java_HelloJNI_sayHello(JNIEnv *env, jobject thisObj) {
    std::cout << "Hello from C++!" << std::endl;
    return;
}
```

```bash
javac HelloJNI.java
javah -jni HelloJNI
g++ -shared -fPIC -I"$JAVA_HOME/include" -I"$JAVA_HOME/include/linux" -o libhello.so HelloJNI.cpp
java -Djava.library.path=. HelloJNI
```