package io.rogermoore.sdi.container.annotation;

import io.rogermoore.sdi.container.Container;

public class AnnotationContainer extends Container {

    public AnnotationContainer(final AnnotationBeanLoader annotationBeanLoader,
                               final AnnotationBeanGraphHelper annotationBeanGraphHelper) {
        super(annotationBeanLoader, annotationBeanGraphHelper);
    }
}
