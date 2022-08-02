##

It will typically contain **use cases**, validation.

In smaller apps, this logic may be held in view models or the data layer like repositories.

This layer does not care about how to display or store data (so caching logic etc should not be present here).

**DomainLayer should not depend on UILayer(e.g. no imports)**.


### So how does one expose functionality to higher layers (e.g. UI layer)?
Use either
1. callbacks
2. flows (observables)
3. suspend functions

## Dependencies

Usecases can be dependent on lower layers like repositories (a usecase can use multiple repositories) or parellel dependencies like other use cases, **but Usecases should not depend on higher layer like UI layer**

## Naming convention

`verbNounUseCase` e.g. `GetLatestNewsUseCase`.

