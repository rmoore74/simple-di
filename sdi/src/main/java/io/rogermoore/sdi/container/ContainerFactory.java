package io.rogermoore.sdi.container;

import io.rogermoore.sdi.bean.Bean;
import io.rogermoore.sdi.bean.loader.CompositeBeanLoader;
import io.rogermoore.sdi.bean.BeanGraph;
import io.rogermoore.sdi.container.annotation.loader.ClassBeanLoader;
import io.rogermoore.sdi.container.annotation.loader.MethodBeanLoader;
import io.rogermoore.sdi.container.annotation.loader.util.ClassLoaderUtil;

import java.util.Map;
import java.util.Set;

public class ContainerFactory {

    private ContainerFactory() {
        throw new IllegalArgumentException("Do not construct.");
    }

    public static Container newAnnotationBasedContainer(final String basePackage) {
        Set<Class<?>> baseClasses = ClassLoaderUtil.loadClassesInPackage(basePackage);
        Map<String, Bean<?>> beans = CompositeBeanLoader.from(
                new ClassBeanLoader(baseClasses),
                new MethodBeanLoader(baseClasses)
        ).load();
        BeanGraph beanGraph = new BeanGraph(beans);
        return new Container(beanGraph);
    }

}
