import java.util.*

class Sample {
}

fun abc() {

    var a= arrayOfNulls<Int>(22);
    println(a)
    val arr = Array<Int>(10, { i -> i *2 });
    val arr2 = Array<Int>(10,)  { i -> i *2 }

    println(Arrays.toString(arr))

    println(3 in arrayOf(1,2,3))

    println(Arrays.toString(arr2))

}