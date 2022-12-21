package io.rogermoore.sdi.container.annotation.loader;

import io.rogermoore.sdi.bean.definition.BeanDefinition;
import io.rogermoore.sdi.bean.definition.DefinitionLoader;
import io.rogermoore.sdi.container.annotation.marker.Beans;

import javax.inject.Named;
import javax.inject.Singleton;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

import static io.rogermoore.sdi.container.annotation.loader.util.AnnotationUtil.getQualifier;

public class MethodBeanDefinitionLoader implements DefinitionLoader {

    private final Set<Class<?>> classes;

    public MethodBeanDefinitionLoader(final Set<Class<?>> classes) {
        this.classes = classes;
    }

    @Override
    public Map<String, BeanDefinition<?>> load() {
        return classes.stream()
                .filter(clazz -> clazz.getAnnotation(Beans.class) != null)
                .flatMap(clazz -> Arrays.stream(clazz.getMethods()))
                .filter(method -> method.getAnnotation(Named.class) != null)
                .map(this::mapToBeanDefinition)
                .collect(Collectors.toMap(BeanDefinition::qualifier, Function.identity()));
    }

    private BeanDefinition<?> mapToBeanDefinition(final Method method) {
        Class<?> type = method.getReturnType();
        String qualifier = getQualifier(method.getReturnType());
        boolean singleton = method.getAnnotation(Singleton.class) != null;
        String[] parameters = new String[method.getParameterCount()];
        for (int i=0; i < parameters.length; i++) {
            var parameter = method.getParameters()[i];
            if (parameter.getAnnotation(Named.class) != null) {
                parameters[i] = parameter.getAnnotation(Named.class).value();
            } else {
                parameters[i] = parameter.getType().getName();
            }
        }
        return new BeanDefinition<>(type, qualifier, singleton, parameters);
    }
}
