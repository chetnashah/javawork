package com.onlinejudges.acmuva;

import java.util.Scanner;

/**
 * Created by jayshah on 11/4/17.
 */
public class U11683 {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        int A, C;
        A = sc.nextInt();
        C = sc.nextInt();
        while(A !=0 ){
            solve(sc, A, C);
            A = sc.nextInt();
            C = sc.nextInt();
        }
    }

    private static void solve(Scanner sc, int a, int c) {
        int[] bars = new int[c];
        for(int i=0;i<c;i++) {
            bars[i] = sc.nextInt();
        }
        System.out.println(bars);
        // check for drops in bars and increase counts
        
    }

}
