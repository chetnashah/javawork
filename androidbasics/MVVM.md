
## model - view - viewmodel

This approach improves upon MVP by reducing coupling between VIew and presenter.

Here the `Viewmodel will expose observable state` for View to consume.
And view consumes observable data changes from Viewmodel and applies to itself on change event notification.

## ViewModel

ViewModel is a class that is responsible for preparing and managing the data for
an {@link `android.app.Activity` Activity} or a {@link `androidx.fragment.app.Fragment` Fragment}.
It also handles the communication of the Activity / Fragment with the rest of the application
(e.g. calling the business logic classes).

A ViewModel is always created in association with a scope (a fragment or an activity) and will
be retained as long as the scope is alive. E.g. if it is an Activity, until it is
finished.

In other words, this means that a ViewModel will not be destroyed if its owner is destroyed for a
configuration change (e.g. rotation). The new owner instance just re-connects to the existing model.

The purpose of the ViewModel is to acquire and keep the information that is necessary for an
Activity or a Fragment. The Activity or the Fragment should be able to observe changes in the
ViewModel. ViewModels usually expose this information via {@link LiveData} or Android Data
Binding. You can also use any observability construct from your favorite framework.

ViewModel's only responsibility is to manage the (observable) data for the UI. **ViewModel should never access your view hierarchy or hold a reference back to the Activity or the Fragment.**
