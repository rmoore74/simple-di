package io.rogermoore.sdi.container.annotation.loader;

import io.rogermoore.sdi.bean.Bean;
import io.rogermoore.sdi.bean.loader.BeanLoader;
import io.rogermoore.sdi.container.annotation.provider.ClassBeanProvider;

import javax.inject.Inject;
import javax.inject.Named;
import javax.inject.Singleton;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import static io.rogermoore.sdi.container.annotation.loader.util.AnnotationUtil.getQualifier;

public class ClassBeanLoader implements BeanLoader {

    private final Set<Class<?>> classes;

    public ClassBeanLoader(final Set<Class<?>> classes) {
        this.classes = classes;
    }

    @SuppressWarnings({"unchecked", "rawtypes"})
    public Map<String, Bean<?>> load() {
        Map<String, Bean<?>> beans = new HashMap<>();
        for (var clazz : classes) {
            if (clazz.getAnnotation(Named.class) != null) {
                String qualifier = getQualifier(clazz);
                boolean singleton = clazz.getAnnotation(Singleton.class) != null;
                Map<String, Bean<?>> dependencies = new HashMap<>();
                for (var constructor : clazz.getConstructors()) {
                    if (constructor.getAnnotation(Inject.class) != null) {
                        for (int i=0; i < constructor.getParameterCount(); i++) {
                            var parameter = constructor.getParameters()[i];
                            String paramQualifier;
                            if (parameter.getAnnotation(Named.class) != null) {
                                paramQualifier = parameter.getAnnotation(Named.class).value();
                            } else {
                                paramQualifier = parameter.getType().getName();
                            }
                            dependencies.put(paramQualifier, null);
                        }
                        break;
                    }
                }
                beans.put(qualifier, new Bean<>(clazz, qualifier, singleton, dependencies, new ClassBeanProvider(clazz)));
            }
        }
        return beans;
    }

}
