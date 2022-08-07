
it's made of rules that determine how your app creates, stores, and changes data.

The entry point into the data layer should always be `Repository` classes.

The data layer is made of repositories that each can contain zero to many data sources. You should create a repository class for each different type/Entity of data you handle in your app.

For example, you might create a `MoviesRepository` class for data related to movies, or a `PaymentsRepository` class for data related to payments.

Pull based API of a repository would be: Create/read/update/delete for any entity, but we can have pull based data exposure via Kotlin Flows which expose stream of changes to the data.

## Responsibility of repository

Repository classes are responsible for the following tasks:

* `Exposing` data to the rest of the app.
* `Centralizing changes` to the data.
* `Resolving conflicts` between multiple data sources.
* `Abstracting sources` of data from the rest of the app.
* Containing `business logic`.

**thread safety** - Repository methods should be callable from main thread. It is repository's job to make the call (to db or network) on a separate thread.

## DataSources

A `DataSource` will typically only use a single data source e.g. network or local database to serve the data to the app.

Repositories depend on/use DataSources to fetch or store their data.
A DataSource should not depend on/use/import repositories.

You can have one DataSource per entity per sourcetype e.g. RemoteMoviesDataSource or LocalArticleDataSource or RemoteArticleDataSource.

## Maintaining source of truth between multiple data sources

## API source

Many times API may return more data than what your app might need, so keep your entities thin.

For even more granularity, each model can have its own entity definition also.

## Testing Repositories

only logic so should be fine

## Testing Datasources

Testing data sources is slightly tricky due to side effects like network call or db.

For local database mocking, use `InMemoryDatabase` provided by Room.

For API calls, explore: MockWebSErver or WireMock.

