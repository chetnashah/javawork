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


fun main(){
    println("Main running! from thread name = ${Thread.currentThread().name}")
    print(GlobalScope)

    val job = GlobalScope.launch {
//        print(this)
        delay(100)
        runBlocking {
            println("inner runblocking running on thread = ${Thread.currentThread().name}")
        }
        println("Fake coroutine work inside thread name = ${Thread.currentThread().name}")
    }

    coroutineScope {  }

    runBlocking {
        var f = CompletableFuture.completedFuture(11)
        var ans = f.await()
        println("\nF = $ans")
        delay(10000)
        println("Fake coroutine work inside runBlocking name = ${Thread.currentThread().name}")
    } // everything beyond this point is blcoked till above coroutine finishes


    CoroutineScope(Dispatchers.Main).launch {
        println("starting job in thread : ${Thread.currentThread().name}")
        val res1 = getResult()
        println("result1 = $res1")
        val res2 = getResult()
        println("result1 = $res2")
        val res3 = getResult()
        println("result1 = $res3")
        val res4 = getResult()
        println("result1 = $res4")
        delay(100)
    }
    Thread.sleep(1000)
}

private suspend fun getResult(): Int {
    delay(100)
    return nextInt(0, 100)
}