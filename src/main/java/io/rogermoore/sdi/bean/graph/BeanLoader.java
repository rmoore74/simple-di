package io.rogermoore.sdi.bean.graph;

import io.rogermoore.sdi.bean.definition.BeanDefinition;

import java.util.Map;

public class BeanLoader {

    private final BeanGraph beanGraph;
    private final Map<String, BeanDefinition<?>> definitions;

    public BeanLoader(final BeanGraph beanGraph,
                      final Map<String, BeanDefinition<?>> definitions) {
        this.beanGraph = beanGraph;
        this.definitions = definitions;
    }

    public void load() {
        for (var definition : definitions.values()) {
            add(definition);
        }
    }

    public <T> BeanWrapper<T> add(final BeanDefinition<T> definition) {
        if (beanGraph.contains(definition.qualifier())) {
            return beanGraph.get(definition.qualifier());
        }

        BeanWrapper<?>[] dependencies = getDependencies(definition);
        BeanWrapper<T> beanWrapper =
                new BeanWrapper<>(definition.type(), definition.qualifier(), definition.singleton(), dependencies);
        beanGraph.add(beanWrapper);

        return beanWrapper;
    }

    private BeanWrapper<?>[] getDependencies(final BeanDefinition<?> definition) {
        String[] parameterQualifiers = definition.parameterQualifiers();
        BeanWrapper<?>[] dependencies = new BeanWrapper<?>[parameterQualifiers.length];
        for (int i=0; i < parameterQualifiers.length; i++) {
            dependencies[i] = add(definitions.get(parameterQualifiers[i]));
        }
        return dependencies;
    }
}
