package com.onlinejudges.cses;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.Scanner;
import java.util.StringTokenizer;

class Point {
    int x;
    int y;

    Point(int x, int y) {
        this.x = x;
        this.y = y;
    }

    @Override
    public String toString() {
        return "x = "+x +" y = "+y;
    }
}
public class PointLocationTest {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int t = Integer.parseInt(br.readLine());
        Point[] points = new Point[3];

        while(t-- > 0) {
            StringTokenizer st = new StringTokenizer(br.readLine());
            for(int j=0; j<3; j++) {
                int x = Integer.parseInt(st.nextToken());
                int y = Integer.parseInt(st.nextToken());
                points[j] = new Point(x, y);
            }
            System.out.println("Scanned point = "+ Arrays.toString(points));
            solve(points);
        }
        br.close();

    }

    static void solve(Point[] points) {
        long value = (long)(points[1].x - points[0].x) * (points[2].y - points[0].y) -
                (long)(points[1].y - points[0].y) * (points[2].x - points[0].x);
        if(value == 0) {
            System.out.println("TOUCH");
        } else if(value < 0) {
            System.out.println("RIGHT");
        } else {
            System.out.println("LEFT");
        }
    }
}
