package com.com.onlinejudges.codeforces;

import java.util.Scanner;

public class D959 {
    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);
        int N = sc.nextInt();
        int[] a = new int[N];
        int[] ans = new int[N];
        for(int i=0;i<N;i++) {
            a[i] = sc.nextInt();
        }

        int[] seive = new int[10000];
        for(int i=0;i<N;i++) {
            int num = a[i];
            if(seive[num] == -1){
                continue;
            }
            for(int k=num;num*k< 10000; k++){
                seive[num*k] = -1;
            }

        }
        for(int i=0;i<N;i++) {
            if(seive[a[i]] != -1) {
                ans[i] = a[i];// don't accept as is find a smaller one also when we have found larger one for first mistake
                seive[a[i]] = -1;
            } else {
                int next = findNextCoprime(a[i], seive);
                ans[i] = next;
            }
        }
        for(int i=0;i<N;i++) {
            System.out.printf("%d ", ans[i]);
        }
    }

    static int findNextCoprime(int x, int[] seive) {
        int k=x+1;
        while(seive[k] < 0) {
            k++;
        }
        return k;
    }

    static int gcd(int a, int b)
    {
        // base case
        if (a == b)
            return a;

        // a is greater
        if (a > b)
            return gcd(a-b, b);
        return gcd(a, b-a);
    }

    // Function to return LCM of two numbers
    static int lcm(int a, int b)
    {
        return (a*b)/gcd(a, b);
    }

}
