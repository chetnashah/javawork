package com.course.basics;

import java.util.Arrays;

public class MergeSort {

    private static final boolean DBG = false;

    // merges given array from hi to lo, assuming lo-mid is sorted and mid-hi is sorted
    // aux is only used in this function
    public static void merge(Comparable[] a, Comparable[] aux, int lo, int hi) {

        if(DBG) {
            System.out.println("merge, lo = "+lo+" hi = "+hi);
        }
        // copy all data from original to aux from lo to hi
        for(int i=lo; i<=hi; i++) {
            aux[i] = a[i];
        }

        // k is pointer in a, while i and j are pointer into aux
        int i = lo, mid = lo + (hi - lo) /2 + 1, j = mid;
        for(int k = lo; k <= hi; k++) {
            if (DBG) {
                System.out.println(" i = "+i+" j = "+j+" k = "+k);
            }
            if (j > hi) {
                a[k] = aux[i];
                i++;
            } else if (i > mid){
                a[k] = aux[j];
                j++;
            } else if(aux[i].compareTo(aux[j]) > 0){
                a[k] = aux[j];
                j++;
            } else {
                a[k] = aux[i];
                i++;
            }
        }
        if (DBG) {
            System.out.println("-------");
        }
    }

    // aux is passed but not used
    public static void sort(Comparable[] a, Comparable[] aux, int lo, int hi) {
        if (lo >= hi) {// single element size no need to do anything
            return;
        }
        // now lo and high atleast will be different, i.e atleast two elements involved
        int mid = lo + (hi - lo) /2;
        // merge sort does the recursion first, and then the real work.
        sort(a, aux, lo, mid); // sort first half, results put back in a
        sort(a, aux, mid+1, hi); // sort second half, results put back in a
        merge(a, aux, lo, hi); // merge the two sorted halves present in a, using a auxillary space
    }

    // main entry point for sort
    public static void sort(Comparable[] arr){
        // NOTE: auxillary always ever created once.
        // before use it is overriten by actual data in merge.
        Comparable[] aux = new Comparable[arr.length];
        sort(arr, aux, 0, arr.length - 1);
    }

    public static void main(String[] args) {
        Integer[] arr = new Integer[4];
        arr[0]=1;
        arr[1]=5;
        arr[2]=2;
        arr[3]=4;
        sort(arr);


        System.out.println(Arrays.toString(arr));
    }
}
