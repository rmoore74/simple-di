package io.rogermoore.sdi.bean;

import java.util.Set;

public interface BeanLoader {
    Set<Class<?>> load();
}
