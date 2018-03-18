package com.com.onlinejudges.leetcode;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class RepeatedSubstringPattern459 {
    public static void main(String[] args) {
        System.out.println(repeatedSubstringPattern("bb"));
    }

    public static boolean repeatedSubstringPattern(String s) {
        Set<Integer> divisorLens = getDivisors(s.length());
        for (Integer len :
                divisorLens) {
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < s.length() / len; i++) {
                sb.append(s.substring(0,len));
            }
            if (sb.toString().equals(s)) {
                return true;
            }
        }
        return false;
    }

    public static Set<Integer> getDivisors(int n){
        Set<Integer> s = new HashSet<Integer>();
        for(int i=n/2; i>=1; i--) {
            if(n%i==0){
                s.add(i);
            }
        }
        return s;
    }
}
