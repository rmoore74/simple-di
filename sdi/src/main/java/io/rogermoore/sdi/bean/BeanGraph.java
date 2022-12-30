package io.rogermoore.sdi.bean;

import java.util.Collection;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

public class BeanGraph {

    private final ConcurrentMap<String, Bean<?>> beanMap = new ConcurrentHashMap<>();

    public BeanGraph(final Map<String, Bean<?>> beans) {
        for (var bean : beans.values()) {
            add(bean, beans);
        }
        for (var bean : beanMap.values()) {
            bean.init();
        }
    }

    private Bean<?> add(final Bean<?> bean,
                        final Map<String, Bean<?>> beans) {
        if (beanMap.containsKey(bean.getQualifier())) {
            return beanMap.get(bean.getQualifier());
        }

        getDependencies(bean, beans);
        beanMap.put(bean.getQualifier(), bean);

        return bean;
    }

    private void getDependencies(final Bean<?> bean,
                                 final Map<String, Bean<?>> beans) {
        for (var parameter : bean.getDependencyQualifiers()) {
            bean.addDependency(parameter, add(beans.get(parameter), beans));
        }
    }

    public boolean contains(final String qualifier) {
        return beanMap.containsKey(qualifier);
    }

    public Collection<Bean<?>> getAll() {
        return beanMap.values();
    }

    @SuppressWarnings("unchecked")
    public <T> Bean<T> get(final String qualifier) {
        return (Bean<T>) beanMap.get(qualifier);
    }

}
