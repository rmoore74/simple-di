package io.rogermoore.sdi.container;

import io.rogermoore.sdi.bean.definition.CompositeDefinitionLoader;
import io.rogermoore.sdi.container.annotation.AnnotationContainer;
import io.rogermoore.sdi.container.annotation.loader.ClassBeanDefinitionLoader;
import io.rogermoore.sdi.container.annotation.loader.MethodBeanDefinitionLoader;
import io.rogermoore.sdi.container.annotation.loader.util.ClassLoaderUtil;

import java.util.Set;

public class ContainerFactory {

    private ContainerFactory() {
        throw new IllegalArgumentException("Do not construct.");
    }

    public static Container newAnnotationBasedContainer(final String basePackage) {
        Set<Class<?>> baseClasses = ClassLoaderUtil.loadClassesInPackage(basePackage);
        return new AnnotationContainer(
                CompositeDefinitionLoader.from(
                        new ClassBeanDefinitionLoader(baseClasses),
                        new MethodBeanDefinitionLoader(baseClasses)
                )
        );
    }

}
