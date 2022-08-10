
The instance created from factory is known as `Product`.

## Main ingridients:

1. an Product interface for various product variants
2. Config param to choose between variants
3. Concrete subclasses to select from
4. (Optional) common interface for creator and creator subclasses, each creating different concrete variant of

## Although not obvious from the name, this involves inheritance & subclassing

allows subclasses to alter the type of an object that will be created.

**Creation method is not static!!**

An advanced version also has subclassing of creators

```php
abstract class Department {
    public abstract function createEmployee($id);

    public function fire($id) {
        $employee = $this->createEmployee($id);
        $employee->paySalary();
        $employee->dismiss();
    }
}

class ITDepartment extends Department {
    public function createEmployee($id) {// override creation method
        return new Programmer($id);
    }
}

class AccountingDepartment extends Department {
    public function createEmployee($id) {// override creation method
        return new Accountant($id);
    }
}
```

## Step 1: Interface creation: return type of factory method

## Step 2: Concrete product Variants implementing Interface

## Step 3: creation method: has multiple options.

### Option 1: In main class, have a (optionally static) creation method and return product variant based on config param

Selection of product variant is based on config param to creation method.
This is the more commonly used.

### Option 2 (advanced level): abstract creator base class, and allow concrete creator subclasses to override non-static creation method.

The selection of concrete creator subclass depends on config param.

## Example:
1. create interface: `Transport`
2. Concrete variants implementing interface: `class Ship implements Transport{}` and `class Truck implements Transport{}`.
3. Creation method
   1. Base class static method: `class Logistics { static Transport createTransport(String type) {} }`
   2. Or concrete creator subclasses like `abstract class Logistics { abstract Transport createTransport() {} }`
      1. and implementer like: `class SeaLogistics implements Logistics{ Transport createTransport() {}}` and `class RoadLogistics implement Logistics { Transport createTransport() {}}`