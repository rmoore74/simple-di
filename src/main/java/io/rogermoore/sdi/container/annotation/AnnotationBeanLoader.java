package io.rogermoore.sdi.container.annotation;

import io.rogermoore.sdi.bean.BeanLoader;
import io.rogermoore.sdi.container.exception.ContainerInitialisationException;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Optional;
import java.util.Queue;
import java.util.Set;

public class AnnotationBeanLoader implements BeanLoader {

    private final String basePackage;

    public AnnotationBeanLoader(final String basePackage) {
        this.basePackage = basePackage;
    }

    public Set<Class<?>> load() {
        Set<Class<?>> beanClasses = new HashSet<>();

        Queue<String> packages = new LinkedList<>();
        packages.add(basePackage);

        while (packages.peek() != null) {
            String currentPackage = packages.poll();
            InputStream stream = Optional.ofNullable(ClassLoader.getSystemClassLoader()
                            .getResourceAsStream(currentPackage.replaceAll("[.]", "/")))
                    .orElseThrow(() -> new ContainerInitialisationException(basePackage));
            BufferedReader reader = new BufferedReader(new InputStreamReader(stream));
            reader.lines()
                    .forEach(line -> {
                        if (line.endsWith(".class")) {
                            Class<?> clazz = loadClass(currentPackage, line);
                            if (clazz.getAnnotation(Bean.class) != null) {
                                beanClasses.add(clazz);
                            }
                        } else {
                            packages.add(currentPackage + "." + line);
                        }
                    });
        }

        return beanClasses;
    }

    private Class<?> loadClass(final String packageName,
                               final String className) {
        String classPath = packageName + "." + className.substring(0, className.lastIndexOf('.'));
        try {
            return Class.forName(classPath);
        } catch (ClassNotFoundException e) {
            throw new ContainerInitialisationException(classPath);
        }
    }
}
