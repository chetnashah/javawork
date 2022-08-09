

## Dependency injection with Autowired

### Autowired on constructor field

Here there is no confusion, the dependency gets (and has to be) created first, and then it is used in the constructor.

### Autowired on field or setter

The autowired dependency will be constructed first, but field assignment or field setting will happen after 
the current beans constructor. i.e. assignment happens in post bean construction processing.



