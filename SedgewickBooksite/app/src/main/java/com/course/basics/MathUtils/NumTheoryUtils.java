package com.course.basics.MathUtils;

import java.util.*;

public class NumTheoryUtils {
    public static void main(String[] args) {
        System.out.println(getDivisors(100));
        System.out.println(isPrime(77));
        System.out.println(gcd(10, 80));
    }


    /**
     * IN O(n log log n),
     * Get a seive array of primes upto N
     * @param n
     * @return ArrayList by seive where arr.get(i) = true if i is prime.
     */
    public static List<Boolean> seiveErastothenesPrimes(int N) {
        ArrayList<Boolean> primeList = new ArrayList<Boolean>(N+1);
        for(int i=0;i<N+1;i++){
            primeList.add(Boolean.TRUE);
        }

        primeList.set(0, Boolean.FALSE);
        primeList.set(1, Boolean.FALSE);

        // outer loop goes from 2 to sqrt(N)
        for(int i=2; (long)i*i<=N; i++) {
            if(primeList.get(i) == Boolean.TRUE){
                // inner loop loops via multiples till the end.
                for(int j=i+i; j <= N; j += i){
                    primeList.set(j, Boolean.FALSE);
                }
            }
        }
        return primeList;
    }

    /**
     * O(sqrt(n)) algorithm that returns a set of divisors of passed integer
     * @param n
     * @return Set of integers that are all divisors from 1 upto n inclusive
     */
    public static Set<Integer> getDivisors(int n){
        Set<Integer> s = new HashSet<>();
        for (int i = 1; i <= Math.sqrt(n) ; i++) {
            if(n % i == 0) {
                s.add(i);
                s.add(n/i);
            }
        }
        return s;
    }

    public static boolean isPrime(int n) {
        for (int i = 2; i <= Math.sqrt(n); i++) {
            if (n % i == 0) {
                return false;
            }
        }
        return true;
    }

    public static int gcd(int a, int b) {
        if (b == 0) {
            return a;
        }
        return gcd(b, a%b);
    }

}

