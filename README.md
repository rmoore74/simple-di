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

You can also create beans at method level by storing them in a class annotated with the `@Beans` tag.
```
@Beans
public class UserBeans {

    @Named
    public UserService userService(final UserRepository userRepository) {
        return new UserService(userRepository);
    }
    
    @Named
    public UserRepository userRepository() {
        return new UserRepository();
    }

}
```

To create the DI container, pass the base package via the `ContainerFactory` factory methods.

```
public static void main(String args[]) {
    Container diContainer = ContainerFactory.newAnnotationBasedContainer("org.somedomain.beans");
    
    UserService userService = diContainer.get(UserService.class);
    UserRepository userRepository = diContainer.get("repositoryQualifier", UserService.class);
    
    ...
}
```
