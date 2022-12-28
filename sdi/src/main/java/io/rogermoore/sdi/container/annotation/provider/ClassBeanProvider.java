package io.rogermoore.sdi.container.annotation.provider;

import io.rogermoore.sdi.bean.Bean;
import io.rogermoore.sdi.bean.BeanInstantiationException;
import io.rogermoore.sdi.bean.BeanProvider;

import javax.inject.Inject;
import java.lang.reflect.InvocationTargetException;

import static io.rogermoore.sdi.container.annotation.loader.util.AnnotationUtil.getQualifier;

public class ClassBeanProvider<T> extends BeanProvider<T> {

    private final Class<T> clazz;

    public ClassBeanProvider(final Class<T> clazz) {
        this.clazz = clazz;
    }

    @Override
    protected T instantiate() {
        Class<?>[] dependencyParameterTypes = new Class<?>[]{};
        Object[] dependencyInstances = new Object[]{};
        for (var constructor : clazz.getConstructors()) {
            if (constructor.getAnnotation(Inject.class) != null) {
                dependencyParameterTypes = new Class<?>[constructor.getParameterCount()];
                dependencyInstances = new Object[constructor.getParameterCount()];
                for (int i=0; i < constructor.getParameterCount(); i++) {
                    String qualifier = getQualifier(constructor.getParameters()[i]);
                    Bean<?> dependency = getDependencies().get(qualifier);
                    dependencyParameterTypes[i] = dependency.getType();
                    dependencyInstances[i] = dependency.getInstance();
                }
                break;
            }
        }
        try {
            return clazz
                    .getDeclaredConstructor(dependencyParameterTypes)
                    .newInstance(dependencyInstances);
        } catch (NoSuchMethodException | InstantiationException
                 | InvocationTargetException | IllegalAccessException exception) {
            throw new BeanInstantiationException(exception);
        }
    }
}
