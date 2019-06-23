package com.course.basics;

import java.util.Stack;

public class StringProblems {

    public static void main(String[] args) {
        printPermutations("ABCD");

        generateAllCombinationsOfGivenString("abc");
    }

    static void printPermutations(String s) {
        printPermuteHelper(s, "");
    }

    static void printPermuteHelper(String s, String chosen) {// state is saved in chosen
//        System.out.println("s="+s+"chosne="+chosen);
        //1. stopping condition first
        if(chosen.length() == s.length()) {
            System.out.println(chosen);
            return;// don't forget return
        }

        //2. choose / explore/ unchoose
        for(int i=0;i<s.length();i++) {

            char c = s.charAt(i);
            if (!chosen.contains(String.valueOf(c))) {// choose the ones that are not choosen
                chosen = chosen + String.valueOf(c);
                printPermuteHelper(s, chosen);
                chosen = chosen.substring(0, chosen.length() - 1);
            }
        }
    }

    static void printNextLexicographicPermutation(String s) {

    }

    static boolean checkIfPalindrome(String s) {
        return true;
    }

    static String longestPalindromicSubString(String s) {
        return "";
    }

    static String[] getAllPossiblePalindromicPartitions(String s) {
        return null;
    }

    static String longestSubstringWithoutRepeatingChars(String s) {
        return "";
    }

    static String longestCommonSubstring(String a, String b) {
        return "";
    }

    static String longestCommonSubSequence(String a, String b) {
        return "";
    }

    static String longestCommonPrefix(String[] strs) {
        return "";
    }

    static int editDistance(String s1, String s2) {
        return 0;
    }

    /**
     * HARD
     * Combinations of "abc" are "a", "b", "c", "ab", "bc", "ac", "abc"
     * that is subsets removing the empty string
     * @param s
     */
    static void generateAllCombinationsOfGivenString(String s) {
        Stack<Character> result = new Stack<>();
        for(int l=1;l<=s.length();l++) {
            generateCombinationFixedLength(s, 0, l, result);
        }
    }

    private static void generateCombinationFixedLength(String s, int index, int len, Stack<Character> stack) {
//        System.out.println("s="+s+",index="+index+",len="+len+",stack="+stack);
        if(len == 0) {
            System.out.println(stack);
            return;
        }

        if(index == s.length()) {
            return;
        }

        // select index char
        stack.push(s.charAt(index));
        generateCombinationFixedLength(s, index + 1,len-1, stack);
        stack.pop();

        // not select index char
        generateCombinationFixedLength(s, index+1, len, stack);

    }

}
