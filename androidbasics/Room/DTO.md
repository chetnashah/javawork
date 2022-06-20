
## DTO stands for "Data Transfer Object"

DTO models can be considered fatter objects that come from api/network.
You may have separate domain models that are lighter versions of above to manage the data relevant to app & UI.


## DTOs

One other thing you probably want to do, while we're at it, is have DTOs. 

They are an additional representation of your model entities (but they should go in their own package, not the model package), but DTOs needn't have the same properties as your model classes. DTOs are what the controller should feed to the view. 

That way, your view doesn't know about your model, and you don't expose to the view layer any properties of your model classes that you don't want to expose. 

For example, if you have a model class for customers with information about their credit card number, but you have a view that doesn't need to show that credit card number, you can create a DTO that has the other customer data and use it for that view without sending the unneeded data to your view.

