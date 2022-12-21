# SimpleDI
A very simple dependency injection framework using core Java and `javax.inject`.

## Annotation Based Injection
Annotate your bean classes with the `@Named` annotation at the class level to signify that you want this 
class to be managed by SimpleDI.

Annotate your constructor with the `@Inject` annotation to signify that you want SimpleDI to inject 
components via the specified constructor.
```
@Named
public class UserService {

    private final UserRepository userRepository;

    @Inject
    public UserService(final UserRepository userRepository) {
        this.userRepository = userRepository;
    }
    
}
```

To create the DI container, pass the base package via the `ContainerFactory` factory methods.

```
public static void main(String args[]) {
    Container diContainer = ContainerFactory.newAnnotationBasedContainer("org.somedomain.beans");
    UserService userService = diContainer.get(UserService.class);
    UserService userService = diContainer.get("customBeanQualifier", UserService.class);
    
    ...
}
```