class Generics {
}

fun <T: Number> half(t1: T): Double {
    val ls: List<Int> = listOf();
//    if(ls is List<String>) {
//        return 0.0;
//    }
    return t1.toDouble()/2.0;
}

fun <T: Comparable<T>> getMax(first: T, second: T): T {
    return if (first > second) first else second;
}

class LockIt {
    fun lock() {
        println("Locking lockit")
    }
    fun unLock() {
        println("Unlocking lockit")
    }
}

inline fun lockMe(lock: LockIt, body : () -> Unit) {
    lock.lock();
    body();
    lock.unLock();
}