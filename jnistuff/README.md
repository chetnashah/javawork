



A decent overview is JavaOne talk :
https://www.youtube.com/watch?v=_cFwDnKvgfw

## It is possible to call native code from Java (Mostly used case):

On java side :
  declare the methode to be called wit modifier `native`
  e.g.
```
class ABC {
    public native void doSomethingInNative();
}
```
But shared library exposing method `doSomethingInNative` must be loaded first.
In order to do that, Use `System.LoadLibrary("libXyz.so")`

### How will System.loadlibrary look for my xyz.so ?

1. default search path
2. Environment LD_LIBRARY_PATH=`dir with xyz.so`
3. CMD line -Djava.library.path=`dir with xyz.so`
      
Next step is to compile your java code using javac
```
javac MyApp.java
javah -jni myApp // generates headers to be used in native proj
```  

## It is possible to call Java code from native (rarely used):


### LD_LIBRARY_PATH

`LD_LIBRARY_PATH` is the predefined environmental variable in Linux/Unix which sets the path which the linker should look in to while linking dynamic libraries/shared libraries.

`LD_LIBRARY_PATH` contains a colon separated list of paths and the linker gives priority to these paths over the standard library paths `/lib` and `/usr/lib`. The standard paths will still be searched, but only after the list of paths in `LD_LIBRARY_PATH` has been exhausted.

### library mgmt

Each class loader manages its own set of native libraries. The same JNI native library cannot be loaded into more than one class loader. 
Doing so causes `UnsatisfiedLinkError` to be thrown

The VM calls `JNI_OnLoad` when the native library is loaded (for example, through System.loadLibrary). JNI_OnLoad must return the JNI version needed by the native library.

Any `FindClass` calls made from `JNI_OnLoad` will resolve classes in the context of the class loader that was used to load the shared library

### registerNatives

By using registerNatives (or rather, the JNI function `RegisterNatives`), you can name your C functions whatever you want

```cxx
static JNINativeMethod methods[] = {
    {"hashCode",    "()I",                    (void *)&JVM_IHashCode},
    {"wait",        "(J)V",                   (void *)&JVM_MonitorWait},
    {"notify",      "()V",                    (void *)&JVM_MonitorNotify},
    {"notifyAll",   "()V",                    (void *)&JVM_MonitorNotifyAll},
    {"clone",       "()Ljava/lang/Object;",   (void *)&JVM_Clone},
};

    (*env)->RegisterNatives(env, cls,
                            methods, sizeof(methods)/sizeof(methods[0]));

```

The advantages of RegisterNatives are that you get up-front checking that the symbols exist, plus you can have smaller and faster shared libraries by not exporting anything but JNI_OnLoad. The advantage of letting the runtime discover your functions is that it's slightly less code to write.

To use RegisterNatives:

1, Provide a `JNIEXPORT jint JNI_OnLoad(JavaVM* vm, void* reserved)` function.
2. In your `JNI_OnLoad`, register all of your native methods using `RegisterNatives`.

```cxx
JNIEXPORT jint JNI_OnLoad(JavaVM* vm, void* reserved) {
    JNIEnv* env;
    if (vm->GetEnv(reinterpret_cast<void**>(&env), JNI_VERSION_1_6) != JNI_OK) {
        return JNI_ERR;
    }

    // Find your class. JNI_OnLoad is called from the correct class loader context for this to work.
    jclass c = env->FindClass("com/example/app/package/MyClass");
    if (c == nullptr) return JNI_ERR;

    // Register your class' native methods.
    static const JNINativeMethod methods[] = {
        {"nativeFoo", "()V", reinterpret_cast<void*>(nativeFoo)},
        {"nativeBar", "(Ljava/lang/String;I)Z", reinterpret_cast<void*>(nativeBar)},
    };
    int rc = env->RegisterNatives(c, methods, sizeof(methods)/sizeof(JNINativeMethod));
    if (rc != JNI_OK) return rc;

    return JNI_VERSION_1_6;
}
```

### JNI on C vs C++

C-style JNI looks like `(*env)->SomeJNICall(env, param1 ...)`

C++ style JNI looks like `env->SomeJNICall(param1 ...)`


If I recall correctly, in C, Java constructs are simply pointers. Thus, in your code, "(*env)->" is dereferencing pointers to give you access to the underlying methods.

For C++, "env" is actually an object - a different entity than a C pointer. (And JNI can actually provide real objects for your C++ code to manipulate, since C++ actually supports objects.) So "env->" has a different meaning in C++, it means "call the method that is contained in the object pointed to by "env".

The other difference, I believe, is that many of the C-JNI functions require that one of your parameters be the "JNIEnv *env". So in C you might have to say (*env)->foo(env, bar). With c++, the second reference to "env" is not necessary, so you can instead say "env->foo(bar)"



### What does java side look like?

```java
public class JavaToC {

    // implemented in C/C++
    public native void getHelloFromC();

    // load library
    static {
        System.loadLibrary("HelloWorld");// note no "lib" keyword needednote 
    }

    public static void main(String[] args) {
        new JavaToC().getHelloFromC();
    }
}
```
**Note** - no `lib` keyword needed,
Java will automatically search as per OS e.g.
`libHelloWorld.so` on linux/unix
and `HelloWorld.dll` on windows

### c++ side of things

`JNIEnv* env, jobject thiz` get passed into every method by default.
the real arguments follow after these two parameters.

cryptic method signatures are auto generated by `javah` and ides

#### first create class

Java side:
```java
private native String getLine(String prompt);
```

Make it into a class first
```
javac Prompt.java
```

`javah` will read `Prompt.class` and generate `Prompt.h`
```
javah -jni Prompt
```

```
//Prompt.h
JNIEXPORT jstring JNICALL
     Java_Prompt_getLine(JNIEnv *, jobject, jstring);
```

### JNI Primitive types mapping

`boolean` <-> `jboolean`
`byte` <-> `jbyte`
`char` <-> `jchar`
`int` <-> `jint`
`long` <-> `jlong`
`void` <-> `void`

### jobject


An `Object` from java is mapped as `jobject`
An `String` from java is mapped as `jstring`
An `Class<T>` from java is mapped to `jclass`

`Arrays`: `jarray` for generic array but for primitive
arrays, one would have `jintarray`, `jshortarray`, .. etc

Foor array of objects it would be `jobjectarray`.


### JNIEnv

`JNIEnv` is a god object, an interface to JVM and ticket
to working with Java objects.

### JNI method signature format

Note that the JNI uses the method signature to denote the return type of a Java method. The signature `(I)V`, for example, denotes a Java method that takes one argument of type int and has a return type void. The general form of a method signature argument is:

`(argument-types)return-type`

generate these using 
`javap -s -p ClassName`, `-s` stands for signatures
e.g.
```
Compiled from Prompt.java
class Prompt extends java.lang.Object 
    /* ACC_SUPER bit set */
{
    private native getLine (Ljava/lang/String;)Ljava/lang/String;
    public static main ([Ljava/lang/String;)V
    <init> ()V
    static <clinit> ()V
}
```


### field descriptor format
`boolean` - `Z`
`byte` - `B`
`char` - `C`
`double` - `D`
`int` - `I`
`long` - `L`
`void` - `V`
`Reference` - `L<full/qualified/name>`
`Array` - `[+Signature`
`( arg-types ) ret-type`	`method type`


### Can you have static native methods?

`Native` methods can be `static` or non-`static` just like regular java methods.

`non-static` methods receive `jobject thiz` 
`static` methods receive `jclass clazz`.

### Common use cases

#### Access Java strings

`jstring` cannot be directly used where `char *` or `std::string` are being used, otherwise it will result in a VM crash.

JNI native methods should use JNI functions
to convert java strings to native strings and vice-versa.

1. convert `jstring` to `const char *`:  `GetStringUTFChars` converts the built-in Unicode representation of a Java string into a UTF-8 string.
```cpp
    const char *str = (*env)->GetStringUTFChars(env, prompt, 0);
    printf("%s", str);
    (*env)->ReleaseStringUTFChars(env, prompt, str);
```
When your native code is finished using the UTF-8 string, it must call `ReleaseStringUTFChars`. Failing to call this results in a memory leak.

2. Converting C style string `const char *` to `jstring` - Use `NewStringUTF`
```cpp
    ...
    char buf[128];
    scanf("%s", buf);
    return (*env)->NewStringUTF(env, buf);
```

Recall from Accessing Java Arrays that in C you can fetch a Java string from an array of strings and directly assign the result to a jstring, as follows:
```cxx
jstring jstr = (*env)->GetObjectArrayElement(env, arr, i);
```
In C++, however, you need to insert an explicit conversion of the Java string to `jstring`:
```cxx
jstring jstr = (jstring)env->GetObjectArrayElement(arr, i);
```
You must make this explicit conversion because `jstring` is a subtype of `jobject`, which is the return type of `GetObjectArrayElement`.


#### working with java arrays in native methods

continue from here:

You cannot directly use `jintarray` indexing.
You have to get a decayed pointer i.e. `jint *`,
by getting `jint *body = (*env)->GetIntArrayElements(env, arr, 0);`

To get array length/size by using `GetArrayLength` which returns `jsize`, 
`jsize len = (*env)->GetArrayLength(env, arr);`

`ReleaseIntArrayElements` will "unpin" the Java array if it has been pinned in memory.

JNI Functions for Accessing Arrays
Function	Array Type
GetBooleanArrayElements	boolean
GetByteArrayElements	byte
GetCharArrayElements	char
GetShortArrayElements	short
GetIntArrayElements	int
GetLongArrayElements	long
GetFloatArrayElements	float
GetDoubleArrayElements	double

JNI Functions for Releasing Arrays
Function	Array Type
ReleaseBooleanArrayElements	boolean
ReleaseByteArrayElements	byte
ReleaseCharArrayElements	char
ReleaseShortArrayElements	short
ReleaseIntArrayElements	int
ReleaseLongArrayElements	long
ReleaseFloatArrayElements	float
ReleaseDoubleArrayElements	double

`Array of objects`-
The JNI provides a separate set of functions to access elements of object arrays. You can use these functions to get and set individual object array elements.
Note:  You cannot get all the object array elements at once.
`GetObjectArrayElement` returns the object element at a given index.
`SetObjectArrayElement` updates the object element at a given index.

#### invoke java methods from native code

You will have to get `jmethodID` in order to call method,

Getting a `jclass` from `jobject`:
```c
jclass cls = (*env)->GetObjectClass(env, obj);
jmethodID mid = (*env)->GetMethodID(env, cls, "callback", "(I)V");
(*env)->CallVoidMethod(env, obj, mid, depth);
```

Your native method then calls the JNI function `GetMethodID`, which performs a lookup for the Java method in a given class. 
The lookup `GetMethodID` needs the `name` of the method as well as the `method signature`.


If the method does not exist, `GetMethodID` returns zero (0). 
An immediate return from the native method at that point causes a `NoSuchMethodError` to be thrown in the Java application code.

To call static methods of java from native, use `GetStaticMethodID`

your native method calls the JNI function `CallVoidMethod`. The CallVoidMethod function invokes an instance method that has void return type. You pass the object, method ID, and the actual arguments to CallVoidMethod.

For object returning methods, the call would be `CallObjectMethod`

**General idea behind `Call<Type>Method` routines**: `Type` is the return type:

`Call<type>Method` Routines
Programmers place all arguments that are to be passed to the method immediately following the methodID argument. The CallMethod routine accepts these arguments and passes them to the Java method that the programmer wishes to invoke.

`Call<type>MethodA` Routines
Programmers place all arguments to the method in an args array of jvalues that immediately follows the methodID argument. The CallMethodA routine accepts the arguments in this array, and, in turn, passes them to the Java method that the programmer wishes to invoke.

`Call<type>MethodV` Routines
Programmers place all arguments to the method in an args argument of type va_list that immediately follows the methodID argument. The CallMethodV routine accepts the arguments, and, in turn, passes them to the Java method that the programmer wishes to invoke.

### Accessing java member variables

You can get and set both instance and class member variables.

Use `GetFieldID` and `GetStaticFieldID` to get field IDs, which takes in fieldname and field type.
To actually get the field value,using fieldIDs, use `Get<Type>Field`.

```java
class FieldAccess {
  static int si;
  String s;

  private native void accessFields();
  public static void main(String args[]) {
    FieldAccess c = new FieldAccess();
    FieldAccess.si = 100;
    c.s = "abc";
    c.accessFields();
    System.out.println("In Java:");
    System.out.println("  FieldAccess.si = " + FieldAccess.si);
    System.out.println("  c.s = \"" + c.s + "\"");
  }
  static {
    System.loadLibrary("MyImpOfFieldAccess");
  }
}
```

C side:
```Cxx
#include <stdio.h>
#include <jni.h>
#include "FieldAccess.h"

JNIEXPORT void JNICALL 
Java_FieldAccess_accessFields(JNIEnv *env, jobject obj)
{
  jclass cls = (*env)->GetObjectClass(env, obj);
  jfieldID fid;
  jstring jstr;
  const char *str;
  jint si;

  printf("In C:\n");

  fid = (*env)->GetStaticFieldID(env, cls, "si", "I");
  if (fid == 0) {
    return;
  }
  si = (*env)->GetStaticIntField(env, cls, fid);
  printf("  FieldAccess.si = %d\n", si);
  (*env)->SetStaticIntField(env, cls, fid, 200);
  
  fid = (*env)->GetFieldID(env, cls, "s", "Ljava/lang/String;");
  if (fid == 0) {
    return;
  }
  jstr = (*env)->GetObjectField(env, obj, fid);
  str = (*env)->GetStringUTFChars(env, jstr, 0);
  printf("  c.s = \"%s\"\n", str);
  (*env)->ReleaseStringUTFChars(env, jstr, str);

  jstr = (*env)->NewStringUTF(env, "123");
  (*env)->SetObjectField(env, obj, fid, jstr);
}
```

Getting field type signatures:
`javap -s -p FieldAccess`
returns member types
```java
...
static si I
s Ljava/lang/String;
...
```


### Error handling

The JNI requires you to check for possible exceptions after calling JNI functions. The JNI also provides functions that allow your native methods to throw Java exceptions. These exceptions can then be handled either by other parts of your native code or by the Java Virtual Machine.

After the native code catches and handles an exception, it can either clear the pending exception so that the computation may continue, or it can throw another exception for an outer exception handler

### local and global references

References serve to keep the Java objects from being garbage collected. By default, the JNI creates local references because local references ensure that the Java Virtual Machine can eventually free the Java objects. 

`Local references` become invalid when program execution returns from the native method in which the local reference is created. Therefore, a native method must not store away a local reference and expect to reuse it in subsequent invocations.

A `global reference` remains valid until it is explicitly freed
```cxx
/* This code is correct. */
static jclass cls = 0;
static jfieldID fld;

JNIEXPORT void JNICALL
Java_FieldAccess_accessFields(JNIEnv *env, jobject obj)
{
    ...
    if (cls == 0) {
        jclass cls1 = (*env)->GetObjectClass(env, obj);
        if (cls1 == 0) {
            ... /* error */
        }
        cls = (*env)->NewGlobalRef(env, cls1);
        if (cls == 0) {
            ... /* error */      
        }
        fid = (*env)->GetStaticFieldID(env, cls, "si", "I");
    }
    /* access the member variable using cls and fid */
    ...
}
```
However, the native code must call `DeleteGlobalRef` when it no longer needs access to the global reference. Otherwise, the Java Virtual Machine will never unload the corresponding Java object, the Java class referenced by cls above

#### DeleteLocalRef

In most cases, the native programmer should rely on the Java Virtual Machine to free all local references after the native method returns. In certain situations, however, the native code may need to call the DeleteLocalRef function to explicitly delete a local reference. These situations are:

1. You may know that you are holding the only reference to a large Java object and you do not want to wait until the current native method returns before the garbage collector can reclaim the object. For example, in the following program segment, the garbage collector may be able to free the Java object referred to by lref when it is running inside lengthyComputation:
```
    ...
    lref = ...            /* a large Java object */
    ...                   /* last use of lref */
    (*env)->DeleteLocalRef(env, lref);
    lengthyComputation(); /* may take some time */
    return;               /* all local refs will now be freed */
}
```
2. You may need to create a large number of local references in a single native method invocation. This may result in an overflow of the internal JNI local reference table. It is a good idea to delete those local references that will not be needed. For example, in the following program segment, the native code iterates through a potentially large array arr consisting of Java strings. After each iteration, the program can free the local reference to the string element:
```
    ...
    for(i = 0; i < len; i++) {
        jstring jstr = (*env)->GetObjectArrayElement(env, arr, i);
        ...                                /* processes jstr */ 
        (*env)->DeleteLocalRef(env, jstr); /* no longer needs jstr */
    }
    ...
```

### JNI and threads

**Until a thread is attached, it has no JNIEnv, and cannot make JNI calls**


**The JNI interface pointer (JNIEnv *) is only valid in the current thread**. You must not pass the interface pointer from one thread to another, or cache an interface pointer and use it in multiple threads. The Java Virtual Machine will pass you the same interface pointer in consecutive invocations of a native method from the same thread. However, different threads pass different interface pointers to native methods.

**You must not pass local references from one thread to another**. In particular, a local reference may become invalid before the other thread has had a chance to use it. You should always convert local references to global references in situations where different threads may be using the same reference to a Java object.

**Check the use of global variables carefully**. Multiple threads might be accessing these global variables at the same time. Make sure you put in appropriate locks to ensure safety.

