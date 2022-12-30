package io.rogermoore.sdi.container;

import io.rogermoore.sdi.bean.Bean;
import io.rogermoore.sdi.bean.BeanGraph;

import javax.inject.Named;
import java.util.Collection;

public class Container {

    private final BeanGraph beanGraph;

    Container(final BeanGraph beanGraph) {
        this.beanGraph = beanGraph;
    }

    public Collection<Bean<?>> getBeans() {
        return beanGraph.getAll();
    }

    public <T> T getBean(final Class<T> beanClass) {
        T bean = getBean(beanClass.getName(), beanClass);
        if (bean != null) {
            return bean;
        }

        Named named = beanClass.getAnnotation(Named.class);
        if (named == null || "".equals(named.value())) {
            return null;
        }

        return getBean(named.value(), beanClass);
    }

    @SuppressWarnings("unchecked")
    public <T> T getBean(final String qualifier, Class<T> clazz) {
        if (!beanGraph.contains(qualifier)) {
            return null;
        }
        if (beanGraph.get(qualifier).getType() != clazz) {
            return null;
        }
        return (T) beanGraph.get(qualifier).getInstance();
    }
}
