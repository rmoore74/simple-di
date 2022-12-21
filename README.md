# SimpleDI
A very simple dependency injection framework using core Java.

## Annotation Based Injection
Annotate your bean classes with the `@Bean` annotation at the class level to signify that you want this 
class to be managed by SimpleDI.

Annotate your constructor with the `@Inject` annotation to signify that you want SimpleDI to inject 
components via the specified constructor.
```
@Bean
public class UserService {

    private final UserRepository userRepository;

    @Inject
    public Car(final UserRepository userRepository) {
        this.userRepository = userRepository;
    }
    
}
```

To create the DI container, pass the base package via the `ContainerFactory` factory methods.

```
public static void main(String args[]) {
    Container diContainer = ContainerFactory.newAnnotationBasedContainer("org.somedomain.beans");
    diContainer.get(UserService.class);
    diContainer.get("customBeanQualifier");
    
    ...
}
```