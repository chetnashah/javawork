package com.onlinejudges.acmuva;

import java.util.Scanner;

/**
 */
public class U11332 {

    public static void main(String[] args){
        Scanner sc = new Scanner(System.in);
        int scanned = sc.nextInt();

        while(scanned != 0) {
            System.out.println(sumDigits(scanned));
            scanned = sc.nextInt();
        }
    }

    private static int sumDigits(int scanned) {
        if(scanned < 10) {
            return scanned;
        }
        int digSum = 0;
        while(scanned > 0) {
            digSum += scanned % 10;
            scanned = scanned/10;
        }
        return sumDigits(digSum);
    }

}
