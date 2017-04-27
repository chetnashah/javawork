
package com.annotationstuff.example;

import javax.annotation.Generated;

@Generated("com.google.auto.value.processor.AutoValueProcessor")
 final class AutoValue_Animal extends Animal {

  private final String name;
  private final int numLegs;

  AutoValue_Animal(
      String name,
      int numLegs) {
    if (name == null) {
      throw new NullPointerException("Null name");
    }
    this.name = name;
    this.numLegs = numLegs;
  }

  @Override
  String name() {
    return name;
  }

  @Override
  int numLegs() {
    return numLegs;
  }

  @Override
  public String toString() {
    return "Animal{"
        + "name=" + name + ", "
        + "numLegs=" + numLegs
        + "}";
  }

  @Override
  public boolean equals(Object o) {
    if (o == this) {
      return true;
    }
    if (o instanceof Animal) {
      Animal that = (Animal) o;
      return (this.name.equals(that.name()))
           && (this.numLegs == that.numLegs());
    }
    return false;
  }

  @Override
  public int hashCode() {
    int h = 1;
    h *= 1000003;
    h ^= this.name.hashCode();
    h *= 1000003;
    h ^= this.numLegs;
    return h;
  }

}
