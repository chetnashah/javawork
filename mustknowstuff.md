
### equals and hashcode contract

Default implementation for equals() :
true only if same object

Default implementation for hashcode():
same only for same objects.

Often for data like classes: which should be
considered same if properties are same also known as structural/value equality e.g. points
Do following:
* Override equals to satisfy structural equality

Don't forget to override hashcode() when override
equals() because :
Hashing retrieval is a two-step process.
1. Find the right bucket (using hashCode())
2. Search the bucket for the right element (using equals())

If you implement equals (thus structural equality), but you forgot overriding hashcode, now every object has different hashcode, so semantically equal objects may end up in different hashcode/buckets. and hash collections may be inconsistent.

Alternatively for data classes: you can use facilities like
```AutoValue``` which will correctly implement the
necessary methods.

### Typical hashcode implementation for Value classes

* For references use ref.hashCode()
* For primitives like double etc. use wrapper e.g. ((Double) d).hashcode
* For array, use array.deepHashCode()
* For null, add 0
* combine all hashcode of fields using horner's method.

e.g.
``` java
public class Employee {
	int eid;
	String name;

	equals(Object o){ ... }

	hashcode() {
		int hash = 17; // start with num
		hash = 31 * hash + (Integer eid).hashCode();
		hash = 31 * hash + name.hashCode();

		return hash;
	}
}
```

### Why make your data class comparable?

Making your Class Item implements Comparable\<Item\>

It will impose a possible natural ordering on collection of your Items and 
will allow you to do following:

1. calling Collections.sort and Collections.binarySearch
2. calling Arrays.sort and Arrays.binarySearch
3. using objects as keys in a TreeMap
4. using objects as elements in a TreeSet


### Generics:

Generic item creation/instantiation:

https://stackoverflow.com/questions/7934186/java-how-to-create-an-instance-of-generic-type-t

Generic array creation :
Java arrays need to know the type of items at runtime. so it cannot be T.
/*
This is one of the suggested ways of implementing a generic collection in Effective Java; Item 26.
However this triggers a warning because it is potentially dangerous, and should be used with caution.
Worth mentioning that wherever possible,
you'll have a much happier time working with Lists rather than arrays if you're using generics
	*/
private T[] a;
a = (T [])new Object[capacity]; // generic array creation is not allowed, so make object array and cast

 

JavaBeans:
follow from http://docs.oracle.com/javase/tutorial/javabeans/writing/properties.html

All you have to do is make your class look like a bean.
As per following guidelines:

1. It should have a no-arg constructor.
2. It should be serializable(implements Serializable)
3. It should provide getter and setter methods.


Properties : To define a bean class, supply public getter and
		setter methods.

e.g. 
```
	public class FaceBean {
		private int mMouthWidth = 90;

		public int getMouthWidth () {
			return mMouthWidth;
		}

		public void setMouthWidth(int mw) {
			mMouthWidth = mw;
		}
	}
```

A builder tool like Netbeans recognizes method names and shows
mouthWidth property in its list of properties. It also recognizes
type and provides appropriate editor to set values.

Methods that return boolean should start with 'is' e.g. isRunning

### Indexed Properties
An indexed property is an array instead of single value.
In this case bean class provides a method for getting and settin
entire array. (Along with methods for getting and setting specific
element of array)
e.g.
```
	public int[] getTestGrades() {
		return mTestGrades;
	}

	public void setTestGrades(int[] tg) {
		mTestGrades = tg;
	}

	public int getTestGrades(int index) {
		return mTestGrades[index];
	}

	public void setTestGrades(int index, int grade) {
		mTestGrades[index] = grade;
	}
```

### Bound Properties

A bound property notifies listeners when its value changes.
1. The bean class includes addPropertyChangeListener() and
   removePropertyChangeListener() methods for managing the
   bean's listeners.
2. When bound property is changed, the bean sends a 
   PropertyChangeEvent to its registered listeners.

PropertyChangeEvent and PropertyChangeListener live in java.beans
package

FaceBean example with bound properties/listeners
```
import java.beans.*;
public class FaceBean {
	private int mMouthWidth = 90;
	private PropertyChangeSupport mPcs =
		new PropertyChangeSupport(this);
	
	public int getMouthWidth() {
		return mMouthWidth;
	}

	public int setMouthWidth(int mw) {
		int oldMouthWidth = mMouthWidth;
		mMouthWidth = mw;
		//fire property change with name, old and new value
		mPcs.firePropertyChange("mouthWidth",
					oldMouthWidth, mw);
	}

	public void addPropertyChangeListener(PropertyChangeListener
						listener) {
		mPcs.addPropertyChangeListener(listener);
	}

	public void removePropertyChangeListener(PropertyChangeListener								listener) {
		mPcs.removePropertyChangeListener(listener);
	}
}
```
Bound properties are useful in builders like tying up a slider
to a value

### Constrained Properties

A constrained property is a special kind of bound property. For a constrained property, the bean keeps track of a set of veto listeners. When a constrained property is about to change, the listeners are consulted about the change. Any one of the listeners has a chance to veto the change, in which case the property remains unchanged.
See more on website

## Bean Methods
A bean's methods are things it can do.
Any public method that is not a part of a property definition,
is a bean method.

## Bean Events

A bean class can fire off any type of event, including custom events. As with properties, events are identified by a specific pattern of method names.

```
public void add<Event>Listener(<Event>Listener a)
public void remove<Event>Listener(<Event>Listener a)
```

The listener type must be a descendant of java.util.EventListener.



## Bean Persistence
Component models provide a mechanism for persistence that
enables the state of components to be stored in a non-volatile
place for later retrieval.

This mechanism that makes persistence possible is called serialization.
Object serialization means converting an object into
a data stream and writing it to storage.
Any application that uses that bean can then reconstitute it by
deserialization.

All beans must persist!
TO persiste, beans must support serialization by implementing
java.io.Serializable (automatic serialization)
and java.io.Externalizable (custom serialization)

### Classes and serializability
Any class is serializable as long as that class or parent class
implements java.io.Serializable.
e.g.
Component, String, Date, Vector and Hashtable and their subclasses.

Notable classes that do not support Serialization are:
Image, Thread, Socket and InputStream.
Trying to serialize them will throw NotSerializableException.

Java Object Serialization API automatically serializes most
fields of a Serializable object to storage stream including
primitive types, arrays and strings. DOes not serialize or
deserialize fields that are marked transient or static.

### Default Serialization: The java.io.Serializable interface

Serializable acts a marker telling JVM that you have made
sure that your class wil work with default serialization.

Imp points of working with Serializable interface:

* Classes that implement Serializable must have an 
  access to no-argument constructor of supertype.
  This constructor will be called when object is reconstituted
  form .ser file
* You don't need to implement Serializable in your class
  if it is already implemented in superclass.
* All fields except static and transient fields are serialized.

Selective serialization with Serializable:

If your serializable class contains either of the following two methods (the signatures must be exact), then the default serialization will not take place.

```
private void writeObject(java.io.ObjectOutputStream out)
    throws IOException;
private void readObject(java.io.ObjectInputStream in)
    throws IOException, ClassNotFoundException;
```

You can control how more complex objects are serialized, by writing your own implementations of the writeObject and readObject methods







