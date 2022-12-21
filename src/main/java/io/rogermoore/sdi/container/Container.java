package io.rogermoore.sdi.container;

import io.rogermoore.sdi.bean.BeanGraphHelper;
import io.rogermoore.sdi.bean.BeanLoader;

public abstract class Container {

    private final BeanLoader beanLoader;
    private final BeanGraphHelper beanGraphHelper;

    protected Container(final BeanLoader beanLoader,
                        final BeanGraphHelper beanGraphHelper) {
        this.beanLoader = beanLoader;
        this.beanGraphHelper = beanGraphHelper;

        this.initialise();
    }

    private void initialise() {
        for (Class<?> clazz : beanLoader.load()) {
            beanGraphHelper.add(clazz);
        }
        beanGraphHelper.initialiseGraph();
    }

    public <T> T getBean(final String qualifier, Class<T> clazz) {
        return beanGraphHelper.get(qualifier, clazz);
    }

    public <T> T getBean(final Class<T> beanClass) {
        return beanGraphHelper.get(beanClass);
    }
}
