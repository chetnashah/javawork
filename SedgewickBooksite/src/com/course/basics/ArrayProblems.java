package com.course.basics;

import java.util.Arrays;
import java.util.Comparator;
import java.util.stream.Collectors;

public class ArrayProblems {
    public static void main(String[] args) {
        Integer[] i1 = {9, 90, 901};
        Integer[] i2 = {54, 546, 548, 60};
        String a1 = concatenateToGetMin(i1);// shud be 901909
        String a2 = concatenateToGetMax(i2); // shud be 6054854654
        String a3 = concatenateToGetMax(i1); // shud be 990901
        String a4 = concatenateToGetMin(i2); // shud be 5454654860
    }

    static String concatenateToGetMin(Integer[] arr) {
        // one way: brute force all arrangements and maintain max/min
        // solution: use a comparator, that compares two and puts first which results in bigger value

        Arrays.sort(arr, new Comparator<Integer>() {
            @Override
            public int compare(Integer o1, Integer o2) {
                int ret = 0;
                String o1s = String.valueOf(o1);
                String o2s = String.valueOf(o2);
                String o1so2s = o1s.concat(o2s);
                String o2so1s = o2s.concat(o1s);
                return o1so2s.compareTo(o2so1s);
            }
        });
        System.out.println(Arrays.toString(arr));
        return "0";
    }

    static String concatenateToGetMax(Integer[] arr) {
        // one way: brute force all arrangements and maintain max/min
        // manual : pick number with biggest prefix, if all prefix same,
        // find one that has has digit larger than first one of prefix
        // solution: use a comparator, that compares two and puts first which results in bigger value

        Arrays.sort(arr, new Comparator<Integer>() {
            @Override
            public int compare(Integer o1, Integer o2) {
                int ret = 0;
                String o1s = String.valueOf(o1);
                String o2s = String.valueOf(o2);
                String o1so2s = o1s.concat(o2s);
                String o2so1s = o2s.concat(o1s);
                return o2so1s.compareTo(o1so2s);
            }
        });
        System.out.println(Arrays.toString(arr));
        return "0";
    }
}
