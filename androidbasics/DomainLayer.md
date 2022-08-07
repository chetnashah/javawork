##

It will typically contain **use cases**, validation, business logic, util classes, projection & aggregation & selection & filtering & denormalization of data from multiple repositories(based on input filters/arguments).

In smaller apps, this logic may be held in view models or the data layer like repositories.

This layer does not care about how to display or store data (so caching logic etc should not be present here).

**DomainLayer should not depend on UILayer(e.g. no imports)**.


Aggregation use case example: `GetLatestNewsWithAuthors` -> can combine data from `NewsRepository` and `AuthorsRepository`.

### So how does one expose functionality to higher layers (e.g. UI layer)?
Use either
1. callbacks
2. flows (observables)
3. suspend functions


## Dependencies

Usecases can be dependent on lower layers like repositories (a usecase can use multiple repositories) or parellel dependencies like other use cases, **but Usecases should not depend on higher layer like UI layer**

## Naming convention

`verbNounUseCase` e.g. `GetLatestNewsUseCase`.

## Lifecycle

The lifecyle of usecase is scoped to the same as that of the caller, since UseCases are pure logic, there is no lifecycle intelligence of their own.

## Threading 

Should be main safe i.e. it should be safe to call a Usecase from main method.

