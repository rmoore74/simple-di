package io.rogermoore.sdi.bean.definition;

import java.util.Map;
public interface DefinitionLoader {
    Map<String, BeanDefinition<?>> load();
}
