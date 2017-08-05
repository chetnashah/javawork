// decent intro at 
// https://leanpub.com/groovytutorial/read#choo

// in groovy everything is object

// running with groovyConsole
println "hello world"

// make variables with def

def x = 1 // optional types, make variable with initial value
println x

// variables made with def are mutable
x = false
println x

// like java, null values for declaration without init
def ff
println ff

// variables in string should be prepended with $
println "value of x = $x"

// arrays/vectors and maps have both syntax []
def technologies = []
technologies.add("Grails")
// left shift adds and returns the list
technologies << "Groovy"
println technologies

def users = [k1: "oho", k2: "Aha"]
println users

// collections forEach
// collection.each { lambda }
technologies.each { println "Tech : $it" }

// collection membership using in
// in operator is used to determine if item is in
// a list or a key is in map

assert 6 in [2,3,4,6,7]
def grades = ["Math": 2, "Eng": 4, "Sci": 42]
assert "Eng" in grades


// defining simple methods
def myMethod (aa, bb) {
    return aa + bb
}

println myMethod(4,5)
println myMethod("Chetna","cc")

// invisible variables and getters
def getSomeValue() {
  return "got it"
}

// dereferencing undeclared property someValue
// results n getter being called
assert "got it" == someValue



def myName = "Chetna"

// any expression can be interpolated inside
// sting using ${}
println "Hello, ${myName.toUpperCase()}"

// Groovy cannnot have empty blocks like following
// {
//    def count = 0
//    assert count === 0
// }
// instead you have to give it a label e.g.
// A labeled block
Block1: {
  def count = 0
  assert count == 0
}

// Most common uses of curly braces are in
// 1. if statements
if (true) {
  println "hi true"
}
// 2. classes
class Person {

}
// 3. methods
def doStuff() {
  println "doing stuff"
}

// 4. closure declarations
{ nm ->
    println "Hi $nm"
} 

/*
A closure is a either named function stored in a variable
or a nameless function (lambda) passed directly to a method
*/


// groovy closures
def helloWorld = {
    println "Hi world"
}

// helloWorld holds reference to closure
// and you can execute it using
// helloWorld()

helloWorld()

// passing parameters in closures
def power = { int z, int y ->
    return Math.pow(z,y)
}

println power(2,3)
