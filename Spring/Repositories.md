


## Dependency name for Spring data JPA

```xml
<dependency>
    <groupId>org.springframework.data</groupId>
    <artifactId>spring-data-jpa</artifactId>
    <version>2.7.2</version>
</dependency>
```

## Difference between JpaRepository and CrudRepository

1. minimal `CrudRepository` mainly provides CRUD functions. Return type of `saveAll()` method is `Iterable`.
2. more powerful  - `PagingAndSortingRepository` provides methods to do pagination and sorting records.
3. most powerful - `JpaRepository` provides some JPA-related methods such as flushing the persistence context and deleting records in a batch. Return type of `saveAll()` method is a List.
4. 
Because of the inheritance mentioned above, JpaRepository will have all the functions of CrudRepository and PagingAndSortingRepository. So if you don't need the repository to have the functions provided by `JpaRepository` and `PagingAndSortingRepository` , use `CrudRepository`.



