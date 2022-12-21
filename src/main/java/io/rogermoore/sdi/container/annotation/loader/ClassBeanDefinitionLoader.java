package io.rogermoore.sdi.container.annotation.loader;

import io.rogermoore.sdi.bean.definition.BeanDefinition;
import io.rogermoore.sdi.bean.definition.DefinitionLoader;

import javax.inject.Inject;
import javax.inject.Named;
import javax.inject.Singleton;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

import static io.rogermoore.sdi.container.annotation.loader.util.AnnotationUtil.getQualifier;

public class ClassBeanDefinitionLoader implements DefinitionLoader {

    private final Set<Class<?>> classes;

    public ClassBeanDefinitionLoader(final Set<Class<?>> classes) {
        this.classes = classes;
    }

    public Map<String, BeanDefinition<?>> load() {
        return classes.stream()
                .filter(clazz -> clazz.getAnnotation(Named.class) != null)
                .map(clazz -> {
                    String qualifier = getQualifier(clazz);
                    boolean singleton = clazz.getAnnotation(Singleton.class) != null;
                    String[] parameters = new String[]{};
                    for (var constructor : clazz.getConstructors()) {
                        if (constructor.getAnnotation(Inject.class) != null) {
                            parameters = new String[constructor.getParameters().length];
                            for (int i=0; i < parameters.length; i++) {
                                var parameter = constructor.getParameters()[i];
                                if (parameter.getAnnotation(Named.class) != null) {
                                    parameters[i] = parameter.getAnnotation(Named.class).value();
                                } else {
                                    parameters[i] = parameter.getType().getName();
                                }
                            }
                            break;
                        }
                    }

                    return new BeanDefinition<>(clazz, qualifier, singleton, parameters);
                })
                .collect(Collectors.toMap(BeanDefinition::qualifier, Function.identity()));
    }

}
