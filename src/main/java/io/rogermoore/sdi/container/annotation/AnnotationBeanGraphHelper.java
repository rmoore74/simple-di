package io.rogermoore.sdi.container.annotation;

import io.rogermoore.sdi.bean.BeanGraph;
import io.rogermoore.sdi.bean.BeanGraphHelper;
import io.rogermoore.sdi.bean.BeanWrapper;

public class AnnotationBeanGraphHelper implements BeanGraphHelper {

    private final BeanGraph beanGraph;

    public AnnotationBeanGraphHelper(final BeanGraph beanGraph) {
        this.beanGraph = beanGraph;
    }

    @Override
    public <T> BeanWrapper<T> add(Class<T> beanClass) {
        Bean annotation = beanClass.getAnnotation(Bean.class);
        if (annotation == null) {
            throw new IllegalArgumentException("Class provided is not a bean!");
        }

        String qualifier = getQualifier(beanClass);
        if (beanGraph.contains(qualifier)) {
            return beanGraph.get(qualifier);
        }

        boolean singleton = annotation.singleton();
        BeanWrapper<?>[] dependencies = getDependencies(beanClass);

        BeanWrapper<T> beanWrapper =  new BeanWrapper<>(beanClass, qualifier, singleton, dependencies);
        beanGraph.add(beanWrapper);

        return beanWrapper;
    }

    private String getQualifier(Class<?> beanClass) {
        Bean annotation = beanClass.getAnnotation(Bean.class);
        if (!"".equals(annotation.qualifier())) {
            return annotation.qualifier();
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

    public <T> T get(Class<T> beanClass) {
        T bean = get(beanClass.getName());
        if (bean != null) {
            return bean;
        }

        Bean annotation = beanClass.getAnnotation(Bean.class);
        if (annotation == null || "".equals(annotation.qualifier())) {
            return null;
        }

        return get(annotation.qualifier());
    }

    @SuppressWarnings("unchecked")
    public <T> T get(String qualifier) {
        if (!beanGraph.contains(qualifier)) {
            return null;
        }
        return (T) beanGraph.get(qualifier).getInstance();
    }

    @Override
    public void initialiseGraph() {
        beanGraph.init();
    }
}
