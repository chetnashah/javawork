package com.com.onlinejudges.algoexpert;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class TwoSum {
    public static void main(String[] args){
        System.out.println(Arrays.toString(twoNumberSum(new int[]{3, 5, -4, 8, 11, 1, -1, 6}, 10)));
    }
    public static int[] twoNumberSum(int[] array, int targetSum) {
        int[] empty = new int[0];
        Map<Integer, Integer> arrMap = new HashMap<Integer, Integer>();
        for (int i = 0; i < array.length; i++) {
            arrMap.put(array[i], array[i]);
        }
        for (int j = 0; j < array.length; j++) {
            int targetVal = targetSum - array[j];
            if (arrMap.get(targetVal) != null && targetVal != array[j]) {
                if (targetVal > array[j]){
                    return new int[]{array[j], targetVal};
                } else {
                    return new int[]{targetVal, array[j]};
                }
            }
        }
        return empty;
    }
}


