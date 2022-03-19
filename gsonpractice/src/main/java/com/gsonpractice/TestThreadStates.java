package com.gsonpractice;

import java.io.IOException;

public class TestThreadStates {
    public static void main(String[] args) throws InterruptedException {
        Thread.currentThread().setName("Main Test Thread");
        Thread t = new Thread(new Runnable() {
            @Override
            public void run() {
                while(true) {
                    try {
//                    int i=0;
//                    int j = 1;
//                    for(;i<10000000;i++) {
//                        j = j+1+i;
//                    }
//                        System.out.print("a");
                        System.in.read();// System.in is an InputStream
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
        t.setName("looping-thread");
        t.start();
        while(true) {
            System.out.println("State of t is  "+ t.getState());
//            int i=0;
//            int j = 1;
//            for(;i<10000;i++) {
//                j = j+1+i;
//                System.out.print(j);
//            }

            Thread.sleep(550);
        }
    }
}
