package com.langer;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 */
public class Main {

    public static void main(String[] args) {
        System.out.println("Hi world");
        Pair<String, Long> sl = new Pair<>("111",1111l);// type arguments omitted on right because they can be auto inferred
        // Pair<String, Long> is a concrete parametrized type

        // what is a wildcard?
        // in its simplest form it is "?" and stands for all types/unknown type
        Pair<?,?> pp = new Pair<>("Well",0l);

        // Q> can I create an array whose component type is concrete parametrized type ?
        //e.g. an array of Pair<String,String>

        //Pair<String,String>[] parr = new Pair<String,String>[];
        // not allowed because cannot throw an arraystore exception because not able to check types
        // at runtime due to type erasure
        //https://docs.oracle.com/javase/tutorial/java/generics/restrictions.html#createArrays
        // so use Collections instead for collection of concrete parametrized types

        // need for covariance with collections
        Person p = new Person();
        p.setAge(22);
        p.setName("Chet");

        Employee e = new Employee();
        e.setName("Hell");
        e.setAge(10);
        e.setDepartment("Sales");

        // age printer will print all persons
        List<Person> personList = new ArrayList<>();
        personList.add(p);
        agePrinter(personList);
        namePrinter(personList);

        // age printer should be flexible enough to accept list of employees since all employees are persons
        List<Employee> employeeList = new ArrayList<>();
        employeeList.add(e);
        agePrinter(employeeList);
        namePrinter(employeeList);

    }

    // uses type parameter for flexibility
    private static <T extends Person> void agePrinter(List <T> personList) {
        for(T person: personList) {
            System.out.println("class = "+person.getClass());
            System.out.println(person.getAge());
        }
    }

    // uses wildcard for flexibility
    private static void namePrinter(List <? extends Person> personList) {
        for (Person person: personList) {
            System.out.println("class = "+person.getClass());
            System.out.println(person.getName());
        }
    }

    /**
     * each ? stands for seperate family of all types
     * @param pr
     */
    public static void printPair(Pair<?,?> pr){
        System.out.println("fst : "+pr.getFirst() + " snd : "+pr.getSecond());
    }

}

class Employee extends Person {
    String department;

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    @Override
    public String toString() {
        return "Employee{" +
                "department='" + department + '\'' +
                ", name='" + name + '\'' +
                ", age=" + age +
                '}';
    }
}

class Person {
    String name;
    int age;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    @Override
    public String toString() {
        return "Person{" +
                "name='" + name + '\'' +
                ", age=" + age +
                '}';
    }
}

// a generic type pair
class Pair<X,Y> {
    private X first;
    private Y second;

    public Pair(X first, Y second) {
        this.first = first;
        this.second = second;
    }

    public X getFirst() {
        return first;
    }

    public void setFirst(X first) {
        this.first = first;
    }

    public Y getSecond() {
        return second;
    }

    public void setSecond(Y second) {
        this.second = second;
    }
}

