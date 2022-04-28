import kotlin.concurrent.thread

fun main(){
    println("Main running! from thread name = ${Thread.currentThread().name}")
    thread {
        println("Running from a separate thread name = ${Thread.currentThread().name}")
    }
}