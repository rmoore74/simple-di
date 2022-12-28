package io.rogermoore.sdi.container.annotation.loader;

import io.rogermoore.sdi.bean.Bean;
import io.rogermoore.sdi.bean.BeanInstantiationException;
import io.rogermoore.sdi.bean.loader.BeanLoader;
import io.rogermoore.sdi.container.annotation.marker.Beans;
import io.rogermoore.sdi.container.annotation.provider.MethodBeanProvider;
import io.rogermoore.sdi.container.exception.ContainerInitialisationException;

import javax.inject.Named;
import javax.inject.Singleton;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import static io.rogermoore.sdi.container.annotation.loader.util.AnnotationUtil.getQualifier;

public class MethodBeanLoader implements BeanLoader {

    private final Set<Class<?>> classes;

    public MethodBeanLoader(final Set<Class<?>> classes) {
        this.classes = classes;
    }

    @Override
    public Map<String, Bean<?>> load() {
        Map<String, Bean<?>> beans = new HashMap<>();
        for (Class<?> clazz : classes) {
            if (clazz.getAnnotation(Beans.class) != null) {
                for (var method : clazz.getMethods()) {
                    if (method.getAnnotation(Named.class) != null) {
                        try {
                            Object callerClass = clazz.getDeclaredConstructor().newInstance();
                            Bean<?> bean = mapToBeanDefinition(method, callerClass);
                            beans.put(bean.getQualifier(), bean);
                        } catch (NoSuchMethodException | InstantiationException
                                 | InvocationTargetException | IllegalAccessException exception) {
                            throw new ContainerInitialisationException(exception);
                        }
                    }
                }
            }
        }
        return beans;
    }

    private Bean<?> mapToBeanDefinition(final Method method,
                                        final Object callerObject) {
        Class<?> type = method.getReturnType();
        String qualifier = getQualifier(method.getReturnType());
        boolean singleton = method.getAnnotation(Singleton.class) != null;
        Map<String, Bean<?>> parameters = new HashMap<>();
        for (int i=0; i < method.getParameterCount(); i++) {
            var parameter = method.getParameters()[i];
            String paramQualifier;
            if (parameter.getAnnotation(Named.class) != null) {
                paramQualifier = parameter.getAnnotation(Named.class).value();
            } else {
                paramQualifier = parameter.getType().getName();
            }
            parameters.put(paramQualifier, null);
        }
        return new Bean<>(type, qualifier, singleton, parameters, new MethodBeanProvider<>(method, callerObject));
    }
}
