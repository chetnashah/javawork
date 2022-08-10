

## UI Events

Originated from OS/hardware/sdk and typically delivered i.e entry point into  the app in a callback, e.g. onClick listener
or onMotionEvent, onScroll.

### Handling is simple, since view always has access to viewmodel, it can just forward event via method call

Since Views always have access to viewmodels, views can forward ui events by calling methods on viewmodels .

## ViewModel events

Some events originating from data/network or time based logic asking the UI to update.

Example of ViewModel events can be network request failed/succeeded with data, or some timer updated expired. -> these would be again OS driven events but the entry point into these is not UI hierarchy callbacks, but some other library callbacks like retrofit.

**Remember: Viewmodels cannot access/depend upon views** - So viewmodels must expose `a loading/data uiState observable` for the view to consume.

Views will react to the observable/Flow exposed by ViewModel, instead of Viewmodel directly calling view. Another options are LiveData.

Note - ViewModels are agnostic of how View exists/works it only exposes an observable, the View might consume data and show error in any way it wants: toast/dialog/view etc.


