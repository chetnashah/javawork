package com.books.effectivejava3rdEd;

class Football {
    int radius;
    int airPressure;
    String color;

    @Override
    public String toString() {
        return "Football{" +
                "radius=" + radius +
                ", airPressure=" + airPressure +
                ", color='" + color + '\'' +
                '}';
    }
}

public class Sports {
    // static factory method
    // a useful named, controlled way of making objects
    // also gives you control over instance creation unlike constructors
    // also you can return any subtype of return type
    static Football getFootBall(){
        return new Football();
    }
}


class Main {
    public static void main(String[] args) {
        Football f = Sports.getFootBall();
        System.out.println(f);
    }
}
