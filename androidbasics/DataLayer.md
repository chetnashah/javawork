
it's made of rules that determine how your app creates, stores, and changes data.

The data layer is made of repositories that each can contain zero to many data sources. You should create a repository class for each different type of data you handle in your app.

For example, you might create a MoviesRepository class for data related to movies, or a PaymentsRepository class for data related to payments.

## Responsibility of repository

Repository classes are responsible for the following tasks:

* `Exposing` data to the rest of the app.
* `Centralizing changes` to the data.
* `Resolving conflicts` between multiple data sources.
* `Abstracting sources` of data from the rest of the app.
* Containing `business logic`.
