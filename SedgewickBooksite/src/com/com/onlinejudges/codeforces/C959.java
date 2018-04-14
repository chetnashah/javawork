package com.com.onlinejudges.codeforces;



import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class C959 {
    static class Pair {
        Integer first;
        Integer second;

        public Pair(Integer first, Integer second) {
            this.first = first;
            this.second = second;
        }
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int N = sc.nextInt();
        printSection1(N);
        printSection2(N);
    }

    private static void printSection1(int n) {
        if (n < 8) {
            System.out.println(-1);
        } else {
            List<Pair> prList = new ArrayList<>();
            prList.add(new Pair(1,3));
            prList.add(new Pair(1,2));
            prList.add(new Pair(2, 4));
            prList.add(new Pair(2, 5));
            prList.add(new Pair(3,6));
            prList.add(new Pair(4,7));

            for(int i=8;i<=n;i++) {
                prList.add(new Pair(4, n));
            }

            for(Pair pair: prList) {
                System.out.printf("%d %d\n", pair.first, pair.second);
            }

        }


    }

    private static void printSection2(int n) {
        for(int i=2;i<=n;i++) {
            System.out.printf("%d %d\n", 1, i);
        }
    }
}
