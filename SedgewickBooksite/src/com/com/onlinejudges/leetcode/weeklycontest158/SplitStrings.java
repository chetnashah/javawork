package com.com.onlinejudges.leetcode.weeklycontest158;
public class SplitStrings {
        public int balancedStringSplit(String s) {
            int cntL = 0;
            int cntR = 0;
            int cntSame = 0;
            for (int i = 0; i<s.length(); i++) {
                char c = s.charAt(i);
                if (c == 'L') {
                    cntL++;
                } else if (c == 'R') {
                    cntR++;
                }
                if (cntL == cntR) {
                    cntSame++;
                }
            }
            return cntSame;
        }
}
