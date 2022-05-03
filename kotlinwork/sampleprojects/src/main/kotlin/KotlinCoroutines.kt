import kotlinx.coroutines.*
import kotlinx.coroutines.Dispatchers.Main
import java.util.concurrent.CompletableFuture
import java.util.concurrent.Future
import kotlin.concurrent.thread
import kotlinx.coroutines.future.await
import java.util.*
import kotlin.random.Random.Default.nextInt

interface SampleInterface {
    public fun add(): Int {
        return 2 + 2
    }
}

public fun SampleInterface.subs(): Int {
    return 0
}


fun main() = runBlocking {
    println("Main running! from thread name = ${Thread.currentThread().name}")
    print(GlobalScope)

    println("runBlocking code: thread of execution = ${Thread.currentThread().name}")

    val ljob = launch(Dispatchers.IO) {
        println(" inside launch lambda thread of execution = ${Thread.currentThread().name}")
    }

    ljob.join()

    println("done")
}

