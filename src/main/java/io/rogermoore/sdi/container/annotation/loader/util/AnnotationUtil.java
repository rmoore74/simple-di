package io.rogermoore.sdi.container.annotation.loader.util;

import javax.inject.Named;
import java.lang.reflect.AnnotatedElement;
import java.lang.reflect.Method;
import java.util.Optional;

public class AnnotationUtil {

    private AnnotationUtil() {
        throw new IllegalStateException("Do not construct.");
    }

    public static String getQualifier(final Class<?> clazz) {
        return getNamedValue(clazz)
                .orElse(clazz.getName());
    }

    public static String getQualifier(final Method method) {
        return getNamedValue(method)
                .orElse(method.getName());
    }

    private static Optional<String> getNamedValue(final AnnotatedElement elem) {
        Named named = elem.getAnnotation(Named.class);
        if (named != null && !"".equals(named.value())) {
            return Optional.of(named.value());
        }
        return Optional.empty();
    }
}
