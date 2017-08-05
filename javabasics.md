
Why is defining equals(), hashcode() important?
It will define how your value classes will behave with well known collections.
e.g. Set, HashMap etc.

Equals contract:

equals() should be defined for value classes(e.g. Person, Car) etc.
for value equality semantics.
The equals methods compares two objects in question(self and other).
1. equals() method should be equivalence relation, i.e. with self and other,
	they should be reflexive, symmetric and transitive.
2. consistent: multiple invocations of x.equals(y) must return same result, provided
	no object is modified.
3. For any non-null reference value , x.equals(null) should return false.

HashCode contract:
hashCode() method returns an integer and is supported for benifit of hasing based collection classes such as HashTable, HashMap, HashSet.(typical use of hashCode() will be for bucketing, once bucketing is done, equals() would be used for actually finding element)
1. always override hashcode when you override equals
2. two Objects with value equality should return same hashcode
