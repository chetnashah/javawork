package com.com.onlinejudges.topcoder.SRM733;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Pt250 {
    public static void main(String[] args) {
        System.out.println(Arrays.toString(findTriple(7, 2, 5)));
    }

    public static int[] findTriple(int x0, int x1, int x2) {
        int[][] allperms = {{0, 1, 2}, {0, 2, 1}, {1, 0, 2}, {1, 2, 0}, {2, 1, 0}, {2, 0, 1}};
        int[] orig = new int[] {x0, x1, x2};
        int[] trip = null;
        double min = Double.MAX_VALUE;
        for(int i=0;i<allperms.length;i++) {
            double ans = calculateFormula(orig[allperms[i][0]],orig[allperms[i][1]], orig[allperms[i][2]]);
            if (ans < min) {
                trip = allperms[i];
                min = ans;
            }
        }
        System.out.println(min);
        return trip;
    }

    public static double calculateFormula(int a, int b, int c) {
        return Math.abs(a*1.0/b - c);
    }
}
