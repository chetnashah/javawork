package com.com.onlinejudges.codeforces;

import java.util.Scanner;

public class C987 {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int N = sc.nextInt();
        int bestDeal = Integer.MAX_VALUE;
        int[] sizes = new int[N];
        int[] prices = new int[N];

        int[] dealAt = new int[N];
        for(int i=0;i<N; i++) {
            int num = sc.nextInt();
            sizes[i] = num;
        }
        for(int i=0;i<N; i++) {
            int num = sc.nextInt();
            prices[i] = num;
        }

        // deals for first two decided
        dealAt[0] = prices[0];
        dealAt[1] = prices[1];
    }
}
