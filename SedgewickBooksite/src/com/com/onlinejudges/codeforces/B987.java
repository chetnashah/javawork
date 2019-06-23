package com.com.onlinejudges.codeforces;

import java.util.Scanner;

public class B987 {
    public static void main(String[] args) {
        int a,b;
        Scanner sc = new Scanner(System.in);
        a = sc.nextInt();
        b = sc.nextInt();

        // 1 <= x, y <= 10^9

        if (a == b) {
            System.out.println("=");
            return;
        }
        double first = Math.pow(a, 1.0/a); // this is a math trick
        double second = Math.pow(b, 1.0/b);
        System.out.println(first+" "+second);
        if (first < second) {
            System.out.println("<");
        } else {
            System.out.println(">");
        }

    }
}
