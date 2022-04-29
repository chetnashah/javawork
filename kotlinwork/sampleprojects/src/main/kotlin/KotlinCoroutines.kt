import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import java.util.concurrent.CompletableFuture
import java.util.concurrent.Future
import kotlin.concurrent.thread
import kotlinx.coroutines.future.await

fun main(){
    println("Main running! from thread name = ${Thread.currentThread().name}")
    print(GlobalScope)
    GlobalScope.launch {
//        print(this)
        delay(100)
        runBlocking {
            println("inner runblocking running on thread = ${Thread.currentThread().name}")
        }
        println("Fake coroutine work inside thread name = ${Thread.currentThread().name}")
    }

    runBlocking {
        var f = CompletableFuture.completedFuture(11)
        var ans = f.await()
        println("\nF = $ans")
        delay(100)
        println("Fake coroutine work inside runBlocking name = ${Thread.currentThread().name}")
    }
    Thread.sleep(1000)
}