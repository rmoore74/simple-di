package io.rogermoore.sdi.bean.definition;

public record BeanDefinition<T>(
        Class<T> type,
        String qualifier,
        boolean singleton,
        String[] parameterQualifiers)
{}
