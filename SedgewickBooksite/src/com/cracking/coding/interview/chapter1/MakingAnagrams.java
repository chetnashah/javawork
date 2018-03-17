package com.cracking.coding.interview.chapter1;
import java.io.*;
import java.util.*;
import java.text.*;
import java.math.*;
import java.util.regex.*;
public class MakingAnagrams {
    public static int numberNeeded(String first, String second) {
        int[] firstcount = new int[26];
        int[] secondcount = new int[26];

        for(int i=0; i<first.length(); i++) {
            firstcount[first.charAt(i) - 'a']++;
        }

        for(int i=0; i<second.length(); i++) {
            secondcount[second.charAt(i) - 'a']++;
        }

        int totalDeletions = 0;
        for(int j=0; j<26; j++) {
            totalDeletions += Math.abs(firstcount[j] - secondcount[j]);
        }

        return totalDeletions;
    }

    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        String a = in.next();
        String b = in.next();
        System.out.println(numberNeeded(a, b));
    }
}
