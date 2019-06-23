package com.com.onlinejudges.algoexpert;

public class LevehnStein {
    public static int levenshteinDistance(String str1, String str2) {
        // Write your code here.
        int s1Len = str1.length();
        int s2Len = str2.length();

        // table contains empty string at start as well
        int[][] dp = new int[s1Len+1][s2Len+1];

        for(int i = 0; i < s1Len+1; i++){
            dp[i][0] = i;
        }
        for(int j = 0; j < s2Len+1; j++){
            dp[0][j] = j;
        }

        for(int i = 1; i < s1Len + 1; i++){
            for(int j = 1; j < s2Len + 1; j++){
                int subCost = 0;
                if (str1.charAt(i-1) != str2.charAt(j-1)){
                    subCost = 1;
                }
                dp[i][j] = Math.min(dp[i-1][j] + 1, Math.min(dp[i][j-1] + 1, dp[i-1][j-1] + subCost));
            }
        }

        return dp[s1Len][s2Len];
    }

    public static void main(String[] args) {
        System.out.println(levenshteinDistance("abc","abx"));
    }
}

