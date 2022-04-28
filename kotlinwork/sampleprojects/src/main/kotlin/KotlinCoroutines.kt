import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlin.concurrent.thread


fun main(){
    println("Main running! from thread name = ${Thread.currentThread().name}")
    print(GlobalScope)
    GlobalScope.launch {
        print(this)
        delay(100)
        runBlocking {
            println("inner runblocking running on thread = ${Thread.currentThread().name}")
        }
        println("Fake coroutine work inside thread name = ${Thread.currentThread().name}")
    }

    runBlocking {
        delay(100)
        println("Fake coroutine work inside runBlocking name = ${Thread.currentThread().name}")
    }
    Thread.sleep(1000)
}