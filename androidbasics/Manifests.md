
## Inspecting SDK behaviors

https://medium.com/androiddevelopers/getting-to-know-the-behaviors-of-your-sdk-dependencies-f3dfed07a311

## Android gradle ApplicationId always overrides Manfiest's package declaration

`ApplicationId` - This ID uniquely identifies your app on the device and in the Google Play Store. **Once you publish your app, you should never change the application ID**

Your application ID is defined by the `applicationId` property in your module's `build.gradle` file.

getPackageName gives the same applicationId which is created at the final moment from the gradle file and it overrides the AndroidManifest package. So the final AndroidManifest contains the same applicationId.

The package name is just to organize your code.

The applicationId, on the other hand, is used to identify your app in the Play Store. You will change this only if you plan to generate another app based on same code.



## Namespace 

Every Android module has a namespace, which is used as the Java or Kotlin package name for its generated R and BuildConfig classes.

**For a simpler workflow, keep your namespace the same as your application ID, as they are by default.**

Your namespace is defined by the `namespace` property in your module's `build.gradle` file,

```groovy
android {
    namespace 'com.example.myapp'
    ...
}
```

While building your app into the final application package (APK), the Android build tools use the namespace as the namespace for your app's generated R class, which is used to access your app resources. For example, in the preceding build file, the R class is created at com.example.myapp.R.

