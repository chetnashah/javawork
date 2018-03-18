package com.company.j8t;

// mocking functions/closures as interfaces with single abstract method
// function type is A -> B
interface Func<B,A> {
    B m(A x);  // m is the method
}

interface Pred<A> {
    boolean m(A x);
}

// a functional implementation of List
// List is a recursive parametrized type which is either nil or a head followed by a list
public class List<T> {
    T head;
    List<T> tail;

    List(T head, List<T> tail) {
        this.head = head;
        this.tail = tail;
    }

    // construct lists by consing heads onto existing lists
    static <A> List<A> cons(A h, List<A> tl) {
        return new List<A>(h,tl);
    }

    // static methods map, filter and length
    // they can be called even when list has no members
    // if they were instance methods and we called these would result in NPE
    static <A,B> List<B> map(Func<B,A> f, List<A> xs) {
        if (xs == null) {
            return null;
        }
        return new List<B>(f.m(xs.head), List.map(f,xs.tail));
    }

    static <A> List<A> filter(Pred<A> pred, List<A> xs) {
        if (xs == null) {
            return null;
        }
        if (pred.m(xs.head)) {
            return new List<A>(xs.head, List.filter(pred, xs.tail));
        }
        return List.filter(pred, xs.tail);
    }

    static int length(List<?> xs) {
        int ans = 0;
        while (xs != null) {
            ans += 1;
            xs = xs.tail;
        }
        return ans;
    }

    @Override
    public String toString() {
        if (tail == null){
            return head + "->X";
        }
        return head + "->" + tail.toString();
    }

    public static void main(String[] args) {
        List<Integer> li= List.cons(2, List.cons(3, List.cons(4, null)));
        System.out.println("li = " + li);
        List<Integer> dbls = List.map(new Func<Integer, Integer>() {
            @Override
            public Integer m(Integer x) {
                return x * 2;
            }
        }, li);

        System.out.println("dbls = "+dbls);

        List<Integer> evens = List.filter(new Pred<Integer>() {
            @Override
            public boolean m(Integer x) {
                return x%2 == 0;
            }
        }, li);

        System.out.println("evens = "+ evens);

        System.out.println(List.length(li));
    }
}
