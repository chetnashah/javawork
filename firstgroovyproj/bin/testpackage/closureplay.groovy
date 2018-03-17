package testpackage

import java.lang.reflect.Array

/**
 * taken from http://groovy-lang.org/closures.html
 * Created by  on 18/5/17.
 */

def f1 = { String x, int y ->
    println "hey ${x} the value is ${y}"
}

// if you dont pass all arguments
// you cant call it like function but curry it
def crr = f1.curry("ij")
crr 2

// or pass all arguments
f1("hello", 33)


// 2. closures as an object
// A closure is an instance of groovy.lang.Closure
def listener = { e -> println "Clicked on $e.source" }
assert listener instanceof Closure

// Closure's parameteric type is over closures return type
def doubler = {
    Integer num -> num * 2
}

println "dblr: ${doubler(33)}"

//3. calling closures
// since closures are basically methods, they can be called
// like methods
def greeter = { println "Hiii" }
greeter()

def isOdd = { int i -> i%2 == 1}
assert isOdd(3) == true
assert isOdd.call(4) == false
// function calling with space as application
// useful for single argument functions/closures
println "isOdd 3 = ${isOdd 3}"

//Closures vs lambda expressions
//groovy closures are instances of Closure class
// which are very different from lambda expressions of java 8

// Delegation is a key concept in Groovy closures,
// which s not present in java 8

// Closure owner, delegate and this
// this corresponds to enclosing class where closure is defined

println "done"

