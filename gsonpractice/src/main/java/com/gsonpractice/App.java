/*
 * This Java source file was generated by the Gradle 'init' task.
 */
package com.gsonpractice;

import com.google.gson.Gson;

public class App {
    int k = 22;
    int j;
    Integer m = null;
    String str;
    public String getGreeting() {
        return "Hello world.";
    }

    @Override
    public String toString() {
        return "App{" +
                "k=" + k +
                ", j=" + j +
                ", m=" + m +
                ", str=" + str  +
                '}';
    }

    public static void main(String[] args) {
        System.out.println(new App().getGreeting());

        Gson gson = new Gson();
        String s = gson.toJson(22);
        String s2 = gson.toJson(null);

        System.out.println(s);
        System.out.println(s2.length());
        System.out.println(gson.toJson(new App()));

        App aa = gson.fromJson("{ \"j\": null, \"k\": 1}", App.class);
        System.out.println(aa);

    }
}
