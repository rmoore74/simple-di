package io.rogermoore.sdi.container;

import io.rogermoore.sdi.bean.BeanGraph;
import io.rogermoore.sdi.container.annotation.AnnotationBeanGraphHelper;
import io.rogermoore.sdi.container.annotation.AnnotationBeanLoader;
import io.rogermoore.sdi.container.annotation.AnnotationContainer;

public class ContainerFactory {

    private ContainerFactory() {
        throw new IllegalArgumentException("Do not construct.");
    }

    public static Container newAnnotationBasedContext(final String basePackage) {
        return new AnnotationContainer(
                new AnnotationBeanLoader(basePackage),
                new AnnotationBeanGraphHelper(new BeanGraph()));
    }

}
