package io.rogermoore.sdi.bean;

import javax.inject.Provider;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

public abstract class BeanProvider<T> implements Provider<T> {

    private final Map<String, Bean<?>> dependencies;
    protected BeanProvider() {
        this.dependencies = new HashMap<>();
    }

    public final void addDependency(final String qualifier,
                                    final Bean<?> bean) {
        dependencies.put(qualifier, bean);
    }

    protected final Map<String, Bean<?>> getDependencies() {
        return dependencies;
    }

    protected abstract T instantiate();

    public final T get() {
        var nonInitialisedDependencies = dependencies.values().stream()
                .filter(Objects::isNull)
                .collect(Collectors.toSet());
        if (!nonInitialisedDependencies.isEmpty()) {
            throw new BeanInstantiationException(String.format("Following beans not instantiated: %s", nonInitialisedDependencies));
        }
        return instantiate();
    }

}
