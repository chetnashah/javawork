package com.com.onlinejudges.topcoder.SRM832;

import java.util.Arrays;

public class FridayTheThirteenth {
    public static int[] count(int firstDay, int isLeap) {
        int[] ans = new int[12];
        int[] daysInMonthRegular = new int[] {31,28,31,30,31,30,31,31,30,31,30,31};
        int[] daysInMonthLeap = new int[] {31,29,31,30,31,30,31,31,30,31,30,31};
        int cnt = 0;
        for(int i=0;i<12;i++) { // for each month
            int thirteenthOfThisMonth = firstDay + 13 % 7;
            if(thirteenthOfThisMonth == 5) {
                ans[cnt++] = 1;
            } else {
                ans[cnt++] = 0;
            }
            firstDay = (firstDay + ((isLeap == 1) ? daysInMonthLeap[i] : daysInMonthRegular[i])) % 7;
        }
        return ans;
    }

    public static void main(String[] args) {
        System.out.println(Arrays.toString(count(6, 0)));
    }
}
