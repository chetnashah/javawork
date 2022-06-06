
## Create a separate "db" package for all this db like logic

DAO classes talk to both your persistence system (generally a database) and your controller, main JOB of DAO being hiding ugly quries from the rest of your app, and move instances of your model classes between them.
**Ideally, your model classes shouldn't even know that there's a DAO.**

### Repository vs DAO

`repository` is a mechanism for encapsulating storage, retrieval, and search behavior, which emulates a collection of objects

a repository also deals with data and hides queries similar to DAO. However, **repository sits at a higher level, closer to the business logic of an app.**

Consequently, a repository can use a DAO to fetch data from the database and populate a domain object. Or, it can prepare the data from a domain object and send it to a storage system using a DAO for persistence.

Now that we've seen the nuances of the DAO and Repository patterns, let's summarize their differences:

1. DAO is an abstraction of data persistence. However, a repository is an abstraction of a collection of objects
2. DAO is a lower-level concept, closer to the storage systems. However, Repository is a higher-level concept, closer to the Domain objects
3. DAO works as a data mapping/access layer, hiding ugly queries. However, a repository is a layer between domains and data access layers, hiding the complexity of collating data and preparing a domain object
4. DAO can't be implemented using a repository. However, a repository can use a DAO for accessing underlying storage

## DAOs are interfaces 

DAO code is generated by the kotlin/room compiler.
All we need to provide is metadata via annotations

### @Dao

Marks interface as a DAO whose code needs to be generated as a DAO

### @Insert

Method that will insert data

### @Query

**@Query** fun returns `LiveData` objects!
Which means queries are observable.

