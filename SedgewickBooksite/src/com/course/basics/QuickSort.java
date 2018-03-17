package com.course.basics;

import java.util.Arrays;

public class QuickSort {

    private static final boolean DBG = true;

    // returns the final index where the first element should be going
    public static int partition(Comparable[] a, int lo, int hi) {
        int m = lo;
        int i = m + 1;// pointer starting after the first element
        int j = hi;// pointer starting from end
        while (j >= i) { // until pointers cross
            if (a[i].compareTo(a[m]) <= 0) {// move i to right if strictly greater
                i++;
                continue;
            }
            if (a[j].compareTo(a[m]) >= 0) {// move j to left if current el strictly less
                j--;
                continue;
            }
            // when both pointers are unable to move
            if (a[j].compareTo(a[m]) < 0 && (a[i].compareTo(a[m]) > 0)) {
                exch(a, i, j);
            }
        }
        // now j is same as i or less than i
        // i is the position where first element should go.
        exch(a, i, m);
        return i;
    }

    // exchanges array values at given indexes
    public static void exch(Comparable[] a, int i1, int i2) {
        Comparable c = a[i2];
        a[i2] = a[i1];
        a[i1] = c;
    }

    public static void sort(Comparable[] a, int lo, int hi) {
        if (lo >= hi) {
            return;
        }
        // first do the work, then recurse
        int p = partition(a, lo, hi);
        if (DBG) {
            System.out.println(" p = "+p + " lo = " + lo + " hi = "+ hi);
        }
        sort(a, lo, p-1);// note the p-1, the pth element is in right place now
        sort(a, p+1, hi);
    }

    public static void sort(Comparable[] a) {
        sort(a, 0, a.length - 1);
    }

    public static void main(String[] args) {

        Integer[] arr = new Integer[6];
        arr[0] = 8;
        arr[1] = 1;
        arr[2] = 6;
        arr[3] = 2;
        arr[4] = 9;
        arr[5] = 3;

        System.out.println(Arrays.toString(arr));
        sort(arr);

        System.out.println(Arrays.toString(arr));
    }
}
