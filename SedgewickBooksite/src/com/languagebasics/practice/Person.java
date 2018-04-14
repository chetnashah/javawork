package com.languagebasics.practice;

import java.io.*;

public class Person implements Serializable {
    String name;
    Integer id;

    public Person(String name, Integer id) {
        this.name = name;
        this.id = id;
    }

    @Override
    public String toString() {
        return "Person{" +
                "name='" + name + '\'' +
                ", id=" + id +
                '}';
    }

    public static void doWritingSerialization(Person p1){
        try {
            FileOutputStream fout = new FileOutputStream("fout.txt");
            ObjectOutputStream outputStream = new ObjectOutputStream(fout);
            outputStream.writeObject(p1);
            outputStream.flush();
            outputStream.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void dowReadingSerilizatonStuff() {
        try {
            FileInputStream fin = new FileInputStream("fout.txt");
            ObjectInputStream oin = new ObjectInputStream(fin);
            Person p = (Person) oin.readObject();
            System.out.println("de serialized person = "+ p);
            fin.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

    }

    public static void main(String[] args) {
        Person p1 = new Person("chet", 111);
        doWritingSerialization(p1);
        dowReadingSerilizatonStuff();
    }
}
