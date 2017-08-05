package testpackage
/**
 *  Ctrl-Shift-F10 for Running script
 * Created by on 18/5/17.
 */


println "oh hi oh hi oh hi"

First ff = new First()
println ff.hi

// methods with one or more arguments can have method application with space
// e.g.
def giveMyName(name) {
    return " Hi $name"
}

def ans = giveMyName "Chetna"
println ans

// must use parentheses for no argument method -> toUpperCase
def s = "Chetna".toUpperCase()
println s


// optional return statement
def giveRandNumk(seed) {
    return 5
}

// does same thing, return statement optional
def giveRealRandNum(seed) {
    4// last line of a method is implicit return
}

println "${giveRandNumk(0)}"
println "${giveRealRandNum(0)}"


// optional data type declaration (duck typing)
w = "Hi"               // all three valid
def ww = "HGello"       // this is better practice, works well with compiler
String www = "Ahaha"

// optional exceptional handling, groovy catches it for you
def reader = new FileReader("/foo.txt")
println reader