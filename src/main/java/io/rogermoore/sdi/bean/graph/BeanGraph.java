package io.rogermoore.sdi.bean.graph;

import io.rogermoore.sdi.bean.definition.BeanDefinition;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

public class BeanGraph {

    private final ConcurrentMap<String, BeanWrapper<?>> beans;

    public BeanGraph() {
        this.beans = new ConcurrentHashMap<>();
    }

    public void load(final Map<String, BeanDefinition<?>> definitions) {
        BeanLoader beanLoader = new BeanLoader(this, definitions);
        beanLoader.load();
        for (var qualifier : definitions.keySet()) {
            beans.get(qualifier).init();
        }
    }

    public <T> void add(final BeanWrapper<T> beanWrapper) {
        beans.put(beanWrapper.getQualifier(), beanWrapper);
    }

    public boolean contains(final String qualifier) {
        return beans.containsKey(qualifier);
    }

    @SuppressWarnings("unchecked")
    public <T> BeanWrapper<T> get(final String qualifier) {
        return (BeanWrapper<T>) beans.get(qualifier);
    }
}
