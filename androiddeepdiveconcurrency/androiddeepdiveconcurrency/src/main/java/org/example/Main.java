package org.example;

public class Main {
    private static final Runnable job = new Runnable() {
        @Override
        public void run() {
            synchronized (this) {
                System.out.println("running = !"+Thread.currentThread());
            }
        }
    };

    public static void main(String[] args) {
        System.out.println(" starting: "+Thread.currentThread());
        new Thread(job).start();
        synchronized (job) {
            System.out.println("finishing main = !"+Thread.currentThread());
        }
    }
}