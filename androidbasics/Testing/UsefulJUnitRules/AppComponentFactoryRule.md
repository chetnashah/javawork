

##

`AppComponentFactory` lets you create custom `Application`, `Activity`,`Service`, `Receiver` instances. This is useful for testing, where you can create a custom `Application` instance which returns a custom `AppComponent` instance.

## Implementation

```kt
class AppComponentFactoryRule(private val factory: AppComponentFactory) : ExternalResource() {

  override fun before() {
    check(Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
      "AppComponentFactoryRule is not supported on 'VERSION.SDK_INT < VERSION_CODES.P'"
    }
    AppComponentFactoryRegistry.appComponentFactory = factory
  }

  override fun after() {
    AppComponentFactoryRegistry.appComponentFactory = null
  }
}
```