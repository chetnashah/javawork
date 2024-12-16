package com.com.onlinejudges.leetcode;
import java.util.Arrays;
class MaxFactorScore {
    long gcd(long a, long b) {
        if(b == 0) {
            return a;
        }
        return gcd(b, a%b);
    }
    long lcm(long a, long b) {
        return (a * b) / gcd(a,b);
    }

    public static void main(String[] args) {
        int[] arr = {6, 14, 20};
        MaxFactorScore mfs = new MaxFactorScore();
        System.out.println(mfs.maxScore(arr));
    }
    // 840?
    // we can calculate product except self
    public long maxScore(int[] nums) {
        if(nums.length == 1) {
            return nums[0] * nums[0];
        }
        long[] gcdLeft = new long[nums.length];
        long[] gcdRight = new long[nums.length];
        long[] lcmLeft = new long[nums.length];
        long[] lcmRight = new long[nums.length];
        long[] ansArr = new long[nums.length];

        // calculate gcd of all elements on the left,
        // calculate lcm of all elements on the left
        for(int i=0;i<nums.length;i++) {
            if(i == 0) {
                gcdLeft[i] = nums[i];
                lcmLeft[i] = nums[i];
            } else {
                gcdLeft[i] = gcd(gcdLeft[i-1], nums[i]);
                lcmLeft[i] = lcm(lcmLeft[i-1], nums[i]);
            }
        }
        // calculate gcd of all elements on the right
        // calculate lcm of all elements on the right
        for(int i=nums.length-1; i>=0;i--) {
            if(i == nums.length -1) {
                gcdRight[i] = nums[i];
                lcmRight[i] = nums[i];
            } else {
                gcdRight[i] = gcd(gcdRight[i+1], nums[i]);
                lcmRight[i] = lcm(lcmRight[i+1], nums[i]);
            }
        }
        System.out.println("gcdLeft = " + Arrays.toString(gcdLeft));
        System.out.println("gcdRight = " + Arrays.toString(gcdRight));
        System.out.println("lcmLeft = " + Arrays.toString(lcmLeft));
        System.out.println("lcmRight = " + Arrays.toString(lcmRight));

        // calculate final gcd of remaining elemnts
        // calculate final lcm of remaining elments
        // record the max of such value
        for(int i=0;i<nums.length;i++) {
            if(i==0) {
                ansArr[i] = gcdRight[1] * lcmRight[1];
            } else if (i==nums.length-1) {
                ansArr[i] = gcdLeft[nums.length-2] * lcmLeft[nums.length -2];
            } else {
                ansArr[i] = gcd(gcdLeft[i-1], gcdRight[i+1])
                        * lcm(lcmLeft[i-1], lcmRight[i+1]);
            }
            System.out.println("ansArr["+i+"] = "+ansArr[i] + " ");
        }
        long bestAns = 0;
        for(int i=0;i<ansArr.length;i++) {
            bestAns = Math.max(bestAns, ansArr[i]);
        }
        return bestAns;
        // return ans
    }
}