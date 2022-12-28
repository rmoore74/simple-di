package io.rogermoore.sdi.container.annotation.provider;

import io.rogermoore.sdi.bean.BeanInstantiationException;
import io.rogermoore.sdi.bean.BeanProvider;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import static io.rogermoore.sdi.container.annotation.loader.util.AnnotationUtil.getQualifier;

public class MethodBeanProvider<T> extends BeanProvider<T> {

    private final Method method;
    private final Object caller;

    public MethodBeanProvider(final Method method,
                              final Object caller) {
        this.method = method;
        this.caller = caller;
    }

    @Override
    @SuppressWarnings("unchecked")
    protected T instantiate() {
        Object[] params = new Object[method.getParameterCount()];
        for (int i=0; i < params.length; i++) {
            String qualifier = getQualifier(method.getParameters()[i]);
            params[i] = getDependencies().get(qualifier).getInstance();
        }
        try {
            return (T) method.invoke(caller, params);
        } catch (IllegalAccessException | InvocationTargetException e) {
            throw new BeanInstantiationException(e);
        }
    }
}
