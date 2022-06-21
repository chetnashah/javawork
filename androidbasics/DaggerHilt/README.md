
##

https://www.youtube.com/watch?v=B56oV3IHMxg


## Gradle plugin needed

```
plugins {
    id 'dagger.hilt.android.plugin'
}
```

## Why is DI hard on android?

Framework classes like Activity/Fragment/Service/Broadcast receiver are instantiated by the android OS/SDK.

There is `AppComponentFactory` from API28, but not realistic solution right now.


## Annotate Application with @HiltAndroidApp

```kt
@HiltAndroidApp
class MyApp: Application()
```

## ACtivities and Fragments are annotated with @AndroidEntryPoint


```kt
@AndroidEntryPoint
class PlayerActivity: AppCompatActivity(){

    @Inject lateinit var player: MusicPlayer

    override fun onCreate() {
        super.onCreate()
        player.play("play something")
    }
}
```

## Module are a dagger concept, brought over to Hilt

