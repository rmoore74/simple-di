package io.rogermoore.sdi.bean;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

public class BeanGraph {

    private final ConcurrentMap<String, BeanWrapper<?>> beans;

    public BeanGraph() {
        this.beans = new ConcurrentHashMap<>();
    }

    public void init() {
        for (var bean : beans.values()) {
            bean.init();
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
