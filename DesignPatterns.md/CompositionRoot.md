Used in context of Dependency Injection.

This is what will usually be present before you write your own IOC container.
https://admin.indepth.dev/why-do-we-have-dependency-injection-in-web-development/

## Definition

a single point in an application where the entire object graph is composed at once

e.g. class that gets its dependencies from outside

```js
class RowRenderer {
    private columnController: ColumnController;
    private gridApi: GridApi;
    private eventService: EventService;

    // constructor dependency injection
    public init(columnController, gridApi, eventService) {
        this.columnController = columnController;
        this.gridApi = gridApi;
        this.eventService = eventService;
    }
}
```

Then where does the instantiation really happen?

A main/application/process level class is the composition root that holds all objects and instnatiates it.

```js
export class Grid {
    private setupComponents(...) {
        
        // create all the services/object graph
        var columnController = new ColumnController();
        var gridApi = new GridApi();
        var eventService = new EventService();


        // intialize the service by passing the dependencies
        rowRenderer.init(
            ...,
            columnController,
            gridApi,
            eventService,
            ...
        );
    }
}

```

## An observation

https://www.youtube.com/watch?v=hBVJbzAagfs

Dependencies graph at runtime become same as initialization graph at runtime.
In order to resolve dependency graph construction DI container must know the dependency graph ahead of time - either via compile time annotation configuration, or runtime registration of dependencies.

Dependency resolution/construction of a high level object can be recursive, causing to construct lower level objects

Constructor injection helps our dependency references be final.

For best practices in DI for java, constructor params should be interfaces. 
So dependency graph will have interfaces in the dependency graph, but not the implementatioons.

DI container api should provide ways for registering on how/which resolve implementations for interfaces.

Ways of registering if given type/class should be singleton or not.