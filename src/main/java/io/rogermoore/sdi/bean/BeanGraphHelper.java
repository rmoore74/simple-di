package io.rogermoore.sdi.bean;

public interface BeanGraphHelper {
    <T> BeanWrapper<T> add(Class<T> beanClass);
    <T> T get(String qualifier);
    <T> T get(Class<T> beanClass);
    void initialiseGraph();
}
