
package com.annotationstuff.example;

import javax.annotation.Generated;

@Generated("com.google.auto.value.processor.AutoValueProcessor")
 final class AutoValue_Person extends Person {

  private final String name;
  private final int age;

  private AutoValue_Person(
      String name,
      int age) {
    if (name == null) {
      throw new NullPointerException("Null name");
    }
    this.name = name;
    this.age = age;
  }

  @Override
  String name() {
    return name;
  }

  @Override
  int age() {
    return age;
  }

  @Override
  public String toString() {
    return "Person{"
        + "name=" + name + ", "
        + "age=" + age
        + "}";
  }

  @Override
  public boolean equals(Object o) {
    if (o == this) {
      return true;
    }
    if (o instanceof Person) {
      Person that = (Person) o;
      return (this.name.equals(that.name()))
           && (this.age == that.age());
    }
    return false;
  }

  @Override
  public int hashCode() {
    int h = 1;
    h *= 1000003;
    h ^= this.name.hashCode();
    h *= 1000003;
    h ^= this.age;
    return h;
  }

  static final class Builder extends Person.Builder {
    private String name;
    private Integer age;
    Builder() {
    }
    Builder(Person source) {
      this.name = source.name();
      this.age = source.age();
    }
    @Override
    public Person.Builder setName(String name) {
      this.name = name;
      return this;
    }
    @Override
    public Person.Builder setAge(int age) {
      this.age = age;
      return this;
    }
    @Override
    public Person build() {
      String missing = "";
      if (name == null) {
        missing += " name";
      }
      if (age == null) {
        missing += " age";
      }
      if (!missing.isEmpty()) {
        throw new IllegalStateException("Missing required properties:" + missing);
      }
      return new AutoValue_Person(
          this.name,
          this.age);
    }
  }

}
