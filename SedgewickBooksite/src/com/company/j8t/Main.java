package com.company.j8t;


import stdlibfork.StdIn;
import stdlibfork.StdOut;
import stdlibfork.Stopwatch;

public class Main {

    public static void main(String[] args) {
	// write your code here
        int count = 0;
        double sum = 0.0;

        Stopwatch stopwatch = new Stopwatch();
        while(!StdIn.isEmpty()){
            double value = StdIn.readDouble();
            sum += value;
            count++;
        }
        double avg = sum/count;
        StdOut.println("Average is "+avg);
        System.out.println("time taken : "+ stopwatch.elapsedTime());
    }
}
