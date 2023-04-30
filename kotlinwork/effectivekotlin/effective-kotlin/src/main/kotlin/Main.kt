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

 fun ordinaryFunction(block: () -> Unit) {
    println("hi!")
}

fun foo2() {
    listOf(1, 2, 3, 4, 5).forEach {
        if (it == 3) return // non-local return directly to the caller of foo()
        print(it)
    }
    println("this point is unreachable")
}

fun <T> addAnswer(list: List<Any>) {
    list.forEach { println("it = $it") }
}


fun foo() {
    ordinaryFunction {
        return@ordinaryFunction // ERROR: cannot make `foo` return here
    }
}

suspend fun main(args: Array<String>) {
    val ls = mutableListOf("af","Adf")
addAnswer(ls)
//    println("Hello World!")
//
//    var b1 = BankAccount()
//    b1.deposit(22.0)
//
//    var b2 = BankAccount()
//    b2.deposit(29.0)
//
//    val kk= b1..b2
//
//    println(kk)
//
//    for(k in kk) {
//        println("k = ${k}")
//    }
//
//    abc()

    val l: LockIt = LockIt();
    lockMe(l, { println("Hello body!")})


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