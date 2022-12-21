package io.rogermoore.sdi.bean.graph;

import io.rogermoore.sdi.bean.BeanInstantiationException;

import java.lang.reflect.InvocationTargetException;

public class BeanWrapper<T> {

    private final Class<T> type;
    private final String qualifier;
    private final boolean singleton;
    private final BeanWrapper<?>[] dependencies;
    private final Class<?>[] dependencyParameterTypes;
    private final Object[] dependencyInstances;

    private boolean initialised = false;
    private T instance;

    public BeanWrapper(final Class<T> type,
                       final String qualifier,
                       final boolean singleton,
                       final BeanWrapper<?>[] dependencies) {
        this.type = type;
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
            dependencyParameterTypes[i] = dependencies[i].getType();
            dependencyInstances[i] = dependencies[i].getInstance();
        }

        instance = createNewInstance();
        initialised = true;
    }

    public Class<T> getType() {
        return type;
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
            return type
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
