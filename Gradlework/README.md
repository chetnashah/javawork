
## 6.9 and above support apple silicon

Otheriwise, you will get the following error:

```
Couldn't create watch service, not tracking changes between builds
net.rubygrapefruit.platform.NativeIntegrationUnavailableException: Native integration OsxFileEventFunctions is not supported for Mac OS X aarch64.
	at net.rubygrapefruit.platform.internal.Platform.get(Platform.java:114)
	at net.rubygrapefruit.platform.Native.get(Native.java:88)
```

