package com.company.j8t;

import java.util.Iterator;
import java.util.Spliterator;
import java.util.function.Consumer;

/**
 * Created by Jay Shah on 05-07-2017.
 * Generic Iterable stack, of fixed capacity
 */
public class FixedCapacityStack<T> implements Iterable<T> {

    private T[] a;
    private int N;

    public FixedCapacityStack(int capacity) {
        /*
        This is one of the suggested ways of implementing a generic collection in Effective Java; Item 26.
        However this triggers a warning because it is potentially dangerous, and should be used with caution.
        Worth mentioning that wherever possible,
        you'll have a much happier time working with Lists rather than arrays if you're using generics
         */
        a = (T [])new Object[capacity]; // generic array creation is not allowed, so make object array and cast
        N = 0;
    }

    @Override
    public Iterator<T> iterator() {
        return null;
    }

    @Override
    public void forEach(Consumer<? super T> action) {

    }

    @Override
    public Spliterator<T> spliterator() {
        return null;
    }
}
