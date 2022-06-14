
## Quick way to generate data classes from json response

Use json to kotlin class plugin

![img1](images/jsonkotlindataclasscode.png)
![img2](images/kotlindataresponse.png)
![img3](images/jsontokotlinclassplugin.png)

## Add kotlin interfaces for all remote API

recommended to create an api folder for this

## Create YourAPISErvice (an interface)

## Create a retrofit object with BaseURL and converterFactory

## Exposing retrofit instance

The call to `create()` function on a Retrofit object is expensive and the app needs only one instance of Retrofit API service. So, you expose the service to the rest of the app using object declaration.

```kt
private const val BASE_URL = "https://android-kotlin-fun-mars-server.appspot.com"

private val retrofit = Retrofit.Builder()
        .addConverterFactory(ScalarsConverterFactory.create())
        .baseUrl(BASE_URL)
        .build()

object MarsApi {
    val retrofitService : MarsApiService by lazy {
        retrofit.create(MarsApiService::class.java) 
    }
}

interface MarsApiService {
    @GET("photos")
    suspend fun getPhotos(): String
}
```

## fetch data call from a viewmodel

```kt
private fun getMarsPhotos() {
    viewModelScope.launch {
        val listResult = MarsApi.retrofitService.getPhotos()
    }
}
```

## Data classes with custom name

```kt
data class MarsPhoto(val id: String, @Json(name = "img_src") val imgSrc: String)
```

## Coroutine integration

All the interface methods annoted with GET/POST must be made suspend fun i.e.
```kt
public interface MyApi {
    @GET("/v2/top-headlines")
    suspend fun getBreakingNews(){
        
    }
}
```

In the usage site
```kt
GlobalScope.launch(Dispatchers.IO) {
    val news = myapi.getBreakingNews()
    for (item in news) {
        println(item)
    }
}
```

In case you dont want to use suspend functions and returning a `Call` object, then you can call `.await()` on the return value

## Setting up retrofit instance lazily in a singleton

we get api instance from retrofit instance by providing api class:
e.g.
`val newsApi = retrofitInstance.create(NewsAPI::class.java)`

```kt
package com.androiddevs.mvvmnewsapp.api

import com.androiddevs.mvvmnewsapp.util.Constants.Companion.BASE_URL
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class RetroFitInstance {
    companion object {
        private val retrofit by lazy {
            val loggingInterceptor = HttpLoggingInterceptor()
            loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY)

            // we create okhttp client with custom interceptor
            val client = OkHttpClient.Builder()
                .addInterceptor(loggingInterceptor)
                .build()

            // setup retrofit instance with baseurl, okhttpclient and serialize/desieralize settings
            Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .client(client)
                .build()
        }

        val api by lazy {
            // we get api instance from retrofit instance by providing api class
            retrofit.create(NewsAPI::class.java)
        }
    }
}
```

## Using moshi with retrofit

```kt
private const val BASE_URL = "https://android-kotlin-fun-mars-server.appspot.com"

val moshi = Moshi.Builder()
    .add(KotlinJsonAdapterFactory())
    .build()

private val retrofit = Retrofit.Builder()
        .addConverterFactory(MoshiConverterFactory.create(moshi))
        .baseUrl(BASE_URL)
        .build()

object MarsApi {
    val retrofitService : MarsApiService by lazy {
        retrofit.create(MarsApiService::class.java)
    }
}

interface MarsApiService {
    @GET("photos")
    suspend fun getPhotos(): String
}
```