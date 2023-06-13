

https://www.youtube.com/watch?v=_SQM-10TB4k&list=PLWz5rJ2EKKc_J88-h0PhCO_aV0HIAs9Qk&index=2

## Two main classes

1. Worker (sub)class - This class contains the code that needs to be executed in the background.
2. WorkRequest class - This class actually schedules your work request and make it run

## Types of workrequest

1. `OneTimeWorkRequest`: Things which can only be done once.
2. `PeriodicWorkRequest`: For recurring work.

## Worker class

- `doWork()` method: where you put the code to be executed in the background thread.
- `onStopped()` method: called when the system has determined that the worker should stop its work.

```kt
class UploadPhotoWorker : Worker() {
    override fun doWork(): WorkerResult {
        return try {
            // uploadPhoto()     // Replace this function with logn running task.
            WorkerResult.SUCCESS
        } catch (ex: Exception) {
            WorkerResult.FAILURE
        }
    }
}


// with some input data parsing
class MyWroker : Worker(){
    ovverride fun doWork() : Result {
       return try {
              val firstValue = inputData.getString(FIRST_KEY, "Default String")
              val secondValue = inputData.getInt(SECOND_KEY, -1)
              Result.success()
       } catch (e: Exception) {
              Result.failure()
       }
    }
}
```

## Scheduling work

```kt
val workManager = WorkManager.getInstance()
val uploadPhotoRequest = OneTimeWorkRequest.Builder(UploadPhotoWorker::class.java)
            .build()
workManager.enqueue(uploadPhotoRequest)
```

## Input

`Data` objects: To transfer the data around different workers we need to pass the  `Data` object. When creating a Worker we need to set the `Data` as an input and get the result back as a Data object.

For large objects pass URI instead of the object itself.

```kt
// with some input data to the worker
val workRequest = OneTimeWorkRequest.Builder(MyWroker::class.java)
                  .setInputData(createInputData())
                  .build()
WorkManager
         .getInstance()
         .enqueue(workRequest)
```

## Output

Set output data via `Result.success`
```kt
class MyWorker : Worker() {
    override fun doWork(): Result {
        return try {
            val firstValue = inputData.getString(FIRST_KEY, "Default String") 
            val secondValue = inputData.getInt(SECOND_KEY, -1)
            val outputData = createOutputData("Hello There From Output", 56)
            Result.success(outputData)
        } catch (e: Exception) {
            val outputData = createOutputData("Error occurred from output", -100)
            Result.failure(outputData)
        }
    }

    private fun createOutputData(firstData: String, secondData: Int): Data {
        return Data.Builder()
                .putString(FIRST_KEY, firstData)
                .putInt(SECOND_KEY, secondData)
                .build()
    }
}
```