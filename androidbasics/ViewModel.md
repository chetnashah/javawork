
##

The ViewModel class is designed to store and manage UI-related data in a lifecycle conscious way. The ViewModel class allows data to survive configuration changes such as screen rotations.

## Typically a viewmodel will expose a `LiveData<SomeUiState>` or `StateFlow<SomeUiState>` to UI components

## Restrictions

**A ViewModel must never reference a view, Lifecycle, or any class that may hold a reference to the activity context.**

ViewModel objects are designed to outlive specific instantiations of views or LifecycleOwners. This design also means you can write tests to cover a ViewModel more easily as it doesn't know about view and Lifecycle objects.

## lifecycle of a viewmodel