
## ExternalResource - Any resource that have setup/teardown

A base class for Rules (like TemporaryFolder) that set up an external 
resource before a test (a file, socket, server, database connection, etc.),
and guarantee to tear it down afterward.

e.g.

```java
public static class UsesExternalResource {
    Server myServer= new Server();

    // this Rule manages setup and teardown of server used by client
    @Rule
    public ExternalResource resource= new ExternalResource() {
        @Override
        protected void before() throws Throwable {
            myServer.connect();
        };
 
        @Override
        protected void after() {
            myServer.disconnect();
        };
    };

    @Test
    public void testFoo() {
       new Client().run(myServer);
    }
}
```

## Common implementors

`ActivityScenarioRule`, `AppComponentFactoryRule`
