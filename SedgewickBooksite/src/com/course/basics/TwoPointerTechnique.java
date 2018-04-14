package com.course.basics;

import java.util.Arrays;

/**
 * Two pointer technique allows to partition array into two parts in O(n)
 * given a predicate, first part satisfying the predicate, and second part not satisfying
 *
 * The resulting partitioned array is not stable.
 */
public class TwoPointerTechnique {

    interface Criterion{
        boolean check(Integer integer);
    }

    public static void main(String[] args) {
        Integer[] arr = {1, 2, 3, 4, 5, 6, 7, 8, 9};
        TwoPointerTechnique t = new TwoPointerTechnique();
        Criterion isEven = new Criterion() {
            @Override
            public boolean check(Integer integer) {
                return integer % 2 == 0;
            }
        };
        t.reorder(arr, isEven);
        System.out.println(Arrays.toString(arr));

        Integer[] arr2 = {2, 5, 7, -2, 3, -4, 5, 1, -3};
        Criterion isPositive = new Criterion() {
            @Override
            public boolean check(Integer integer) {
                return integer > 0;
            }
        };
        t.reorder(arr2, isPositive);
        System.out.println(Arrays.toString(arr2));
    }

    public void reorder(Integer[] nums, Criterion criterion) {
        int l = 0;// where satisfying items start
        int u = nums.length - 1;
        while(l < u) {// stop when meet
            // left partition satisfies predicate
            while (l < u && criterion.check(nums[l])){
                l++;
            }
            while (l < u && !criterion.check(nums[u])) {
                u--;
            }
            if (l < u) {// swap contents if stuck
                int temp = nums[l];
                nums[l] = nums[u];
                nums[u] = temp;
            }
        }
    }


}
