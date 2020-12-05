package com.course.basics;

import java.util.function.Predicate;
public class BinarySearch {
    public static void main(String args[]) {
        int[] arr = new int[]{0,1,2,3,4,5,6,7};

        Predicate<Integer> pred = (i)->(i>=0);
        System.out.println(binary_search_last_false(arr, pred));
        System.out.println(binary_search_first_true(arr, pred));
    }

    // in case of bunch of false followed by trues, returns the index of last false element
    static int binary_search_last_false(int[] arr, Predicate<Integer> pred){
        int lo=0;
        int hi=arr.length - 1;
        while(lo<hi){
            int mid = lo + (int)Math.ceil((hi - lo)/2.0);
            if(pred.test(arr[mid]) == false) {
                lo = mid;
            } else {
                hi = mid - 1;
            }
        }
        if(lo == 0 && pred.test(arr[lo])) {
            System.out.println("did not find any falsy index!!");
        }
        return lo;
    }

    // in case of bunch of false followed by trues, returns the index of first true element
    static int binary_search_first_true(int[] arr, Predicate<Integer> pred) {
        int lo = 0;
        int hi = arr.length - 1;
        while (lo < hi) {
            int mid = lo + (hi - lo) / 2;
            if(pred.test(arr[mid])) {
                hi = mid;
            } else {
                lo = mid + 1;
            }
        }
        if(lo == (arr.length - 1) && pred.test(arr[lo]) == false){
            System.out.println("did not find first truthy index");
        }
        return lo;
    }
}
