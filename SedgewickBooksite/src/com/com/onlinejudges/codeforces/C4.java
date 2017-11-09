package com.com.onlinejudges.codeforces;

import java.util.*;
// not submitted
public class C4 {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int N = sc.nextInt();
        String[] requests = new String[N];
        for(int i=0;i<N; i++){
            requests[i] = sc.next();
        }
        solve(requests);
    }

    private static void solve(String[] requests) {
        Set<String> names = new HashSet<>();
        Map<String,Integer> repeatingNames = new HashMap<>();
        for(String s: requests) {
            if(!names.contains(s)) {
                names.add(s);
                System.out.println("OK");
            } else {
                int cnt = repeatingNames.getOrDefault(s, 0);
                repeatingNames.put(s, cnt + 1);
                System.out.println(s+repeatingNames.get(s));
            }
        }
    }
}
