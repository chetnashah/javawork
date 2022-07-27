package com.course.basics;

import java.util.Arrays;

public class RabinKarp {

    private static final long MOD = (long) (1e9 + 1); // large mod for reducing hash collisions
    private static final long p = 31;// prime
    private static long[] prime_pows;
    public static void main(String[] args) {

        System.out.println(computeHash("Hey"));
        System.out.println(computeHash("Hello"));
        System.out.println(computeHash("Hi"));
        System.out.println(computeHash(""));
        System.out.println(computeHash("Hello"));

        preComputePrimesArr(1000);

        System.out.println(computeHashWithPrimePows("Hey"));
        System.out.println(computeHashWithPrimePows("Hello"));
        System.out.println(computeHashWithPrimePows("Hi"));
        System.out.println(computeHashWithPrimePows(""));
        System.out.println(computeHashWithPrimePows("Hello"));

    }

    public static void preComputePrimesArr(int N) {
        prime_pows = new long[N+1];
        prime_pows[0] = 1; // p^0 = 1
        for(int i=1;i<N+1;i++) {
            prime_pows[i] = prime_pows[i-1] * p % MOD;
        }
    }

    public static long computeHashWithPrimePows(String s) {
        long hash = 0;
        for(int i=0;i<s.length();i++) {
            hash += prime_pows[i] * (s.charAt(i) - 'a' + 1) % MOD;
        }
        return hash;
    }

    public static long computeHash(String s) {
        long hash = 0;
        long p_pow = 1;
        for(int i = 0; i<s.length();i++) {
            hash += p_pow * (s.charAt(i) - 'a' + 1) % MOD;
            p_pow = p_pow * p % MOD;
        }
        return hash;
    }

}
