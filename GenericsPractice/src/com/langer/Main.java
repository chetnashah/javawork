package com.langer;

/**
 */
public class Main {

    public static void main(String[] args) {
        System.out.println("Hi world");
        Pair<String, Long> sl = new Pair<>("111",1111l);// type arguments omitted on right because they can be auto inferred
        // Pair<String, Long> is a concrete parametrized type

        // what is a wildcard?
        // in its simplest form it is "?" and stands for all types/unknown type
        Pair<?,?> pp = new Pair<>("Well",0l);


        // Q> can I create an array whose component type is concrete parametrized type ?
        //e.g. an array of Pair<String,String>

        //Pair<String,String>[] parr = new Pair<String,String>[];
        // not allowed because cannot throw an arraystore exception because not able to check types
        // at runtime due to type erasure
        //https://docs.oracle.com/javase/tutorial/java/generics/restrictions.html#createArrays
        // so use Collections instead for collection of concrete parametrized types
    }

    /**
     * each ? stands for seperate family of all types
     * @param pr
     */
    public static void printPair(Pair<?,?> pr){
        System.out.println("fst : "+pr.getFirst() + " snd : "+pr.getSecond());
    }

}

// a generic type pair
class Pair<X,Y> {
    private X first;
    private Y second;

    public Pair(X first, Y second) {
        this.first = first;
        this.second = second;
    }

    public X getFirst() {
        return first;
    }

    public void setFirst(X first) {
        this.first = first;
    }

    public Y getSecond() {
        return second;
    }

    public void setSecond(Y second) {
        this.second = second;
    }
}

