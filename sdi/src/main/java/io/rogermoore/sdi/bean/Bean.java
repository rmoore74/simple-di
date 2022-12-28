package io.rogermoore.sdi.bean;

import java.util.Map;
import java.util.Set;
public final class Bean<T> {

    private final Class<T> type;
    private final String qualifier;
    private final boolean singleton;
    private final BeanProvider<T> instanceProvider;
    private final Map<String, Bean<?>> dependencies;

    private boolean initialised = false;
    private T instance;

    public Bean(final Class<T> type,
                final String qualifier,
                final boolean singleton,
                final Map<String, Bean<?>> dependencies,
                final BeanProvider<T> instanceProvider) {
        this.type = type;
        this.qualifier = qualifier;
        this.singleton = singleton;
        this.dependencies = dependencies;
        this.instanceProvider = instanceProvider;
    }

    public void init() {
        if (initialised) {
            return;
        }

        for (Bean<?> dependency : dependencies.values()) {
            dependency.init();
        }

        instance = instanceProvider.get();
        initialised = true;
    }

    public void addDependency(final String qualifier,
                              final Bean<?> dependency) {
        dependencies.put(qualifier, dependency);
        instanceProvider.addDependency(qualifier, dependency);
    }

    public Set<String> getDependencyQualifiers() {
        return dependencies.keySet();
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
        return instanceProvider.get();
    }

    public String getQualifier() {
        return qualifier;
    }

}
