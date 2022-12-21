package io.rogermoore.sdi.bean;

public class BeanInstantiationException extends RuntimeException {

    public BeanInstantiationException(String message) {
        super(message);
    }

    public BeanInstantiationException(Throwable throwable) {
        super(throwable);
    }
}
