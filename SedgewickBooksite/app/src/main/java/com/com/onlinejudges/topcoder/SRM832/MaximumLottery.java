package com.com.onlinejudges.topcoder.SRM832;

import java.util.Arrays;

public class MaximumLottery {

    static double ticketPrice(int[] balls, int K) {
        Arrays.sort(balls);
        int N = balls.length;
        int totalDraws = binomialCoeff(balls.length, K);
        // System.out.println("N = " + N + " K = " + K);
        // System.out.println("totalDraws = " + totalDraws);
        int cnt = totalDraws;
        int i = 0;// last element
        int totalSum = 0;
        while(totalDraws > 0) {
            // System.out.println("i = " + i + "N-i-1 = "+ (N-i-1) +" K-i-1 = " + (K-i-1));
            int numDraws = binomialCoeff(N-i-1,K-1);
            System.out.println(numDraws);
            totalSum += balls[N-1-i] * numDraws;
            totalDraws -= numDraws;
            i++;
        }
        double ans = totalSum*1.0/cnt;
        return ans;

    }

    static int binomialCoeff(int n, int k)
    {
        int C[][] = new int[n + 1][k + 1];

        int i, j;

        // Calculate  value of Binomial
        // Coefficient in bottom up manner
        for (i = 0; i <= n; i++) {
            for (j = 0; j <= Math.min(i, k); j++) {
                // Base Cases
                if (j == 0 || j == i)
                    C[i][j] = 1;

                    // Calculate value using
                    // previously stored values
                else
                    C[i][j] = C[i - 1][j - 1] + C[i - 1][j];
            }
        }

        return C[n][k];
    }

    public static void main(String[] args) {
        System.out.println(ticketPrice(new int[]{11,12,13,14}, 3));
        System.out.println(ticketPrice(new int[]{10,20,30,40,50}, 5));
        System.out.println(ticketPrice(new int[]{1, 1, 2, 3, 5, 8, 13, 21}, 4));


        System.out.println(ticketPrice(new int[]{11, 12, 13, 14, 15, 16, 18}, 1));

    }
}