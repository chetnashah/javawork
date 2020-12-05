package com.com.onlinejudges.topcoder;

import java.util.*;

public class Solution {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        String s = new String("cba");
        System.out.println(getmin(s));
    }

        public static String getmin(String s){
            int n = s.length();// n between 3 to 50
            // generate new n-charachter string

            // choose offset: int x
            // choose prime number p < n, where p is length of a step
            // append from get t[x],t[x+p], upto n chars to get
            // for all such p's and x's return lexicographically smallest string.
            Integer[] primeslessthan= {2, 3, 5, 7, 11, 13, 17, 19, 23, 29, 31, 37, 41, 43, 47};
            Set<Integer> pset = new HashSet<Integer>(Arrays.asList(primeslessthan));
            PriorityQueue<String> finalSet = new PriorityQueue<String>();

            for(int x=0; x<n; x++){
                for(int p=2; p<50 && p < n; p++){
                    if(pset.contains(p)) {
                        StringBuilder sb = new StringBuilder();
                        for(int i=0;i<n;i++){
                            sb.append(s.charAt((x+i*p)%n));
                        }
                        finalSet.add(sb.toString());
                    }
                }
            }
            return finalSet.peek();
        }
}
