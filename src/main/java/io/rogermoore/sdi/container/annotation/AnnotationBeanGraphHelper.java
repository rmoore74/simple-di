package io.rogermoore.sdi.container.annotation;

import io.rogermoore.sdi.bean.BeanGraph;
import io.rogermoore.sdi.bean.BeanGraphHelper;
import io.rogermoore.sdi.bean.BeanWrapper;

import javax.inject.Inject;
import javax.inject.Named;
import javax.inject.Singleton;

public class AnnotationBeanGraphHelper implements BeanGraphHelper {

    private final BeanGraph beanGraph;

    public AnnotationBeanGraphHelper(final BeanGraph beanGraph) {
        this.beanGraph = beanGraph;
    }

    @Override
    public <T> BeanWrapper<T> add(Class<T> beanClass) {
        Named named = beanClass.getAnnotation(Named.class);
        if (named == null) {
            throw new IllegalArgumentException("Class provided is not a bean!");
        }

        String qualifier = getQualifier(beanClass);
        if (beanGraph.contains(qualifier)) {
            return beanGraph.get(qualifier);
        }

        boolean singleton = beanClass.getAnnotation(Singleton.class) != null;
        BeanWrapper<?>[] dependencies = getDependencies(beanClass);

        BeanWrapper<T> beanWrapper =  new BeanWrapper<>(beanClass, qualifier, singleton, dependencies);
        beanGraph.add(beanWrapper);

        return beanWrapper;
    }

    private String getQualifier(Class<?> beanClass) {
        Named named = beanClass.getAnnotation(Named.class);
        if (!"".equals(named.value())) {
            return named.value();
        }
        return beanClass.getName();
    }

    private BeanWrapper<?>[] getDependencies(Class<?> beanClass) {
        for (var constructor : beanClass.getConstructors()) {
            if (constructor.getAnnotation(Inject.class) != null) {
                var parameters = constructor.getParameters();
                BeanWrapper<?>[] dependencies = new BeanWrapper<?>[parameters.length];
                for (int i=0; i < parameters.length; i++) {
                    dependencies[i] = add(parameters[i].getType());
                }
                return dependencies;
            }
        }
        return new BeanWrapper[]{};
    }

    @Override
    public <T> T get(Class<T> beanClass) {
        T bean = get(beanClass.getName(), beanClass);
        if (bean != null) {
            return bean;
        }

        Named named = beanClass.getAnnotation(Named.class);
        if (named == null || "".equals(named.value())) {
            return null;
        }

        return get(named.value(), beanClass);
    }

    @Override
    @SuppressWarnings("unchecked")
    public <T> T get(String qualifier, Class<T> clazz) {
        if (!beanGraph.contains(qualifier)) {
            return null;
        }
        if (beanGraph.get(qualifier).getType() != clazz) {
            return null;
        }
        return (T) beanGraph.get(qualifier).getInstance();
    }

    @Override
    public void initialiseGraph() {
        beanGraph.init();
    }
}
