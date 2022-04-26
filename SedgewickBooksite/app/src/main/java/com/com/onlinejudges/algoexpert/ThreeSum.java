package com.com.onlinejudges.algoexpert;

import java.util.ArrayList;
import java.util.List;
import java.util.Arrays;

class Program {
    public static void main(String[] args){
        ArrayList<Integer[]> expected = new ArrayList<Integer[]>();
        expected.add(new Integer[] {1, 2, 3});
        ArrayList<Integer[]> output = Program.threeNumberSum(new int[] {1, 2, 3}, 6);
        System.out.println(output);
    }
    public static ArrayList<Integer[]> threeNumberSum(int[] array, int targetSum) {
        // Write your code here.
        ArrayList<Integer[]> retArr = new ArrayList<Integer[]>();
        Arrays.sort(array);
        for(int i = 0; i < array.length - 1; i++){
            int cN = array[i];
            int lp = i+1;
            int rp = array.length - 1;
            while(rp > lp) {
                System.out.println("enter:" + lp +" " + rp);
                if (array[lp] + array[rp] + cN == targetSum){
                    retArr.add(new Integer[]{cN, array[lp], array[rp]});
                }
                if (array[lp] + array[rp] < (targetSum - cN))  {
                    lp++;
                } else {
                    rp--;
                }
                System.out.println("exit:" + lp +" " + rp);
            }
        }
        System.out.println(retArr);
        return retArr;
    }
}