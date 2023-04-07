import kotlin.concurrent.thread
import kotlinx.coroutines.*

fun tryThreadConflict() {
    var num = 0
    for (i in 1..1000) {
        thread {
            Thread.sleep(10)
            num += 1
        }
    }
    Thread.sleep(5000)
    print(num) // unlikely to be 1000 due to race conditions
}

fun incCounterWithLock() {
    var lock = Any()
    var num = 0
    for (i in 1..1000) {
        thread {
            Thread.sleep(10)
            synchronized(lock) {
                num += 1
            }
        }
    }
    Thread.sleep(4000)
    print(num)
}

suspend fun tryCoroutineIncCounter() {
    var num = 0;
     coroutineScope {
        for (i in 1..1000) {
            launch {
                delay(10)
                num +=1
            }
        }
     }
    print(num)
}

suspend fun main(args: Array<String>) {
    println("Hello World!")


    // Try adding program arguments via Run/Debug configuration.
    // Learn more about running applications: https://www.jetbrains.com/help/idea/running-applications.html.
//    println("Program arguments: ${args.joinToString()}")

//    tryThreadConflict()

//    runBlocking {
//        tryCoroutineIncCounter()
//    }

    incCounterWithLock()

//    val acct = BankAccount()
//    acct.deposit(32.0)
//    acct.withdraw(12.0)
//    println(acct.balance)
//    acct.withdraw(10.0)
//    println(acct.balance)
//    acct.withdraw(23.0)


}