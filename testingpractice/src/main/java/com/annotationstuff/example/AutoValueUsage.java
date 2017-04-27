package com.annotationstuff.example;

import com.google.auto.value.AutoValue;

/**
 * Definitive reference at https://github.com/google/auto/blob/master/value/userguide/index.md
 * Also needs https://plugins.gradle.org/plugin/net.ltgt.apt
 * and check for settings -> Annotation Processors -> output module content root
 * Then on building generated directory will be created, right click it and
 * mark directory as generated sources root
 */
public class AutoValueUsage {

    public static void main(String[] args) {
        Animal dog = Animal.create("Sheefu",4);
        System.out.println(dog);

        Person p1 = Person.builder().setName("Chetna").setAge(60).build();
        System.out.println(p1);
    }
}


// create value class as an abstract class
// with @AutoValue annotation
@AutoValue
abstract class Animal {
    static Animal create(String name, int numLegs) {
        return new AutoValue_Animal(name, numLegs);
    }

    abstract String name();
    abstract int numLegs();
}

//Using AutoValue to create builders for value classes
@AutoValue
abstract class Person {
    abstract String name();
    abstract int age();

    static Builder builder() {
        return new AutoValue_Person.Builder();
    }

    @AutoValue.Builder
    abstract static class Builder {
        abstract Builder setName(String value);
        abstract Builder setAge(int value);
        abstract Person build();
    }
}