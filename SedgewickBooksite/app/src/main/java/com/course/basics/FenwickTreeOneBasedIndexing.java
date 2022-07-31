package com.course.basics;

public class FenwickTreeOneBasedIndexing {
    int[] bit;// short form for binary indexed tree

    public FenwickTreeOneBasedIndexing(int[] arr) {
        bit = new int[arr.length+1];
        for(int i=0;i<arr.length;i++) {
            pointUpdate(i, arr[i]);
        }
    }

    // sum of first idx (0-indexed) elements in original arr inclusive
    public int sum(int idx) {
        int sumTotal = 0;
        for(int i = idx+1;i>=1;i = i & (i-1)) { // note indices
            sumTotal += bit[i];
        }
        return sumTotal;
    }

    // idx for pointUpdate will be given in 0-idx in original array
    // here update actually means increase or add, not replace the points value
    public void pointUpdate(int idx, int val) {
        for(int i=idx+1; i< bit.length;i += (i & -i)) {
            System.out.println("i = " + i);
            System.out.println("bit["+i+"]" + " = " + bit[i]);
            bit[i] += val;
        }
    }
}
