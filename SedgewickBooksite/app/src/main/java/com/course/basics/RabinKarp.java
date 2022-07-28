package com.course.basics;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

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

        System.out.println(getMatches("hi", "abhhhellohehhowareyou"));
    }

    /**
     * Return list of all index occurences of pattern s in text t.
     * @param s
     * @param t
     * @return
     */
    static List<Integer> getMatches(String s, String t){
        preComputePrimesArr(Math.max(s.length(), t.length()));

        // calculate the pattern hash
        long h_s = 0;
        for(int i=0;i<s.length();i++) {
            h_s += (s.charAt(i)- 'a' +1) * prime_pows[i] % MOD;
        }

        System.out.println("h_s = " + h_s);

        // hash of all text prefixes, upto and including index i
        long[] h_tprefix = new long[t.length()];
        h_tprefix[0] = prime_pows[0] * (t.charAt(0)-'a'+1) % MOD;
        for(int i=1;i<t.length();i++) {
            h_tprefix[i] = h_tprefix[i-1] + (t.charAt(i) - 'a' +1) * prime_pows[i] % MOD;
        }

//        System.out.println("h_tprefix = " + Arrays.toString(h_tprefix));

        List<Integer> occurunces = new ArrayList<>();
        // check hash of window of length s.length() starting at current char i
        for(int i=0;i<t.length()-s.length()+1;i++) {
            // getting (hp[0..R] - hp[0..L]) gives hash[L..R] * p[i]
            long curr_h = (h_tprefix[i+s.length()-1] + MOD - ((i>0)?h_tprefix[i-1]: 0)) % MOD;
            if(curr_h == h_s * prime_pows[i] % MOD) {// compare with hash of pattern h_s
                occurunces.add(i);
            }
        }

        return occurunces;
    }

    public static void preComputePrimesArr(int N) {
        prime_pows = new long[N];
        prime_pows[0] = 1; // p^0 = 1
        for(int i=1;i<N;i++) {
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
