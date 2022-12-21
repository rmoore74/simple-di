package io.rogermoore.sdi.container;

import io.rogermoore.sdi.bean.graph.BeanGraph;
import io.rogermoore.sdi.container.exception.ContainerInitialisationException;

import javax.inject.Named;

public abstract class Container {

    private final BeanGraph beanGraph;

    private boolean initialised;

    protected Container(final BeanGraph beanGraph) {
        this.beanGraph = beanGraph;
    }

    public abstract void initialise();

    protected void setInitialised() {
        initialised = true;
    }

    protected BeanGraph getBeanGraph() {
        return beanGraph;
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
        if (!initialised) {
            throw new ContainerInitialisationException("Container not initialised!");
        }
        if (!beanGraph.contains(qualifier)) {
            return null;
        }
        if (beanGraph.get(qualifier).getType() != clazz) {
            return null;
        }
        return (T) beanGraph.get(qualifier).getInstance();
    }
}
