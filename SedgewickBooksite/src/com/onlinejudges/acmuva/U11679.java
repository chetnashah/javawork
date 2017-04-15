package com.onlinejudges.acmuva;

import java.util.Scanner;

/**
 * Created by jayshah on 9/4/17.
 */
public class U11679 {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int B,N;
        B = sc.nextInt();
        N = sc.nextInt();
        while(B!=0 || N!=0) {
            solve(sc, B,N);
            B = sc.nextInt();
            N = sc.nextInt();
        }
    }

    private static void solve(Scanner sc, int b, int n) {
        int[] reserved = new int[b];
        for(int i=0; i<b;i++) {
            reserved[i] = sc.nextInt();
        }
        for(int j=0;j<n;j++) {
            int C = sc.nextInt()-1;
            int D = sc.nextInt()-1;
            int V = sc.nextInt();
            reserved[C] -= V;
            reserved[D] += V;
        }

        boolean bailout = false;
        for(int k=0;k<b;k++) {
            if(reserved[k] < 0) {
                bailout = true;
            }
        }
        if(bailout) {
            System.out.println("N");
        } else {
            System.out.println("S");
        }
    }
}
