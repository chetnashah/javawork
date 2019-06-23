package com.com.onlinejudges.codeforces;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class A987 {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        String[] colors = { "purple", "green", "blue", "orange", "red", "yellow"};
        String[] gems = { "Power", "Time", "Space", "Soul", "Reality", "Mind"};
        int[] seen = new int[6];

        int N = sc.nextInt();
        List<String> list = new ArrayList<String>();
        for(int i=0;i<N;i++) {
           String color = sc.next();
           for(int j = 0; j<6; j++) {
               if (color.equals(colors[j])) {
                   seen[j] = 1;
               }
           }
        }
        for(int i=0;i<seen.length;i++) {
            if (seen[i] != 1) {
                list.add(gems[i]);
            }
        }
        System.out.println(list.size());
        for(String item: list) {
            System.out.println(item);
        }

    }
}
