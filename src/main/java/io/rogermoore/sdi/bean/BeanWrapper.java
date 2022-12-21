package io.rogermoore.sdi.bean;

import java.lang.reflect.InvocationTargetException;

public class BeanWrapper<T> {

    private final Class<T> beanClass;
    private final String qualifier;
    private final boolean singleton;
    private final BeanWrapper<?>[] dependencies;
    private final Class<?>[] dependencyParameterTypes;
    private final Object[] dependencyInstances;

    private boolean initialised = false;
    private T instance;

    public BeanWrapper(final Class<T> beanClass,
                       final String qualifier,
                       final boolean singleton,
                       final BeanWrapper<?>[] dependencies) {
        this.beanClass = beanClass;
        this.qualifier = qualifier;
        this.singleton = singleton;
        this.dependencies = dependencies;
        this.dependencyParameterTypes = new Class<?>[dependencies.length];
        this.dependencyInstances = new Object[dependencies.length];
    }

    public void init() {
        if (initialised) {
            return;
        }

        for (int i=0; i < dependencies.length; i++) {
            dependencies[i].init();
            dependencyParameterTypes[i] = dependencies[i].beanClass;
            dependencyInstances[i] = dependencies[i].instance;
        }

        if (singleton) {
            instance = createNewInstance();
        }

        initialised = true;
    }

    public T getInstance() {
        if (!initialised) {
            throw new BeanInstantiationException("Bean has not been initialised!");
        }

        if (singleton) {
            return instance;
        }
        return createNewInstance();
    }

    private T createNewInstance() {
        try {
            return beanClass
                    .getDeclaredConstructor(dependencyParameterTypes)
                    .newInstance(dependencyInstances);
        } catch (NoSuchMethodException | InstantiationException
                 | InvocationTargetException | IllegalAccessException exception) {
            throw new BeanInstantiationException(exception);
        }
    }

    public String getQualifier() {
        return qualifier;
    }

}
