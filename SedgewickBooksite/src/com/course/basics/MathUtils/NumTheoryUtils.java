package com.course.basics.MathUtils;

import java.util.HashSet;
import java.util.Set;

public class NumTheoryUtils {
    public static void main(String[] args) {
        System.out.println(getDivisors(100));
        System.out.println(isPrime(77));
        System.out.println(gcd(10, 80));
    }

    /**
     * O(sqrt(n)) algorithm that returns a set of divisors of passed integer
     * @param n
     * @return Set of integers that are all divisors from 1 upto n inclusive
     */
    public static Set<Integer> getDivisors(int n){
        Set<Integer> s = new HashSet<>();
        for (int i = 1; i <= Math.sqrt(n) ; i++) {
            if(n%i==0) {
                s.add(i);
                s.add(n/i);
            }
        }
        return s;
    }

    public static boolean isPrime(int n) {
        for (int i = 2; i <= Math.sqrt(n); i++) {
            if (n%i==0) {
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
