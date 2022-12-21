package io.rogermoore.sdi.bean.definition;

import java.util.HashMap;
import java.util.Map;

public class CompositeDefinitionLoader implements DefinitionLoader {

    private final DefinitionLoader[] definitionLoaders;

    private CompositeDefinitionLoader(final DefinitionLoader[] definitionLoaders) {
        this.definitionLoaders = definitionLoaders;
    }

    @Override
    public Map<String, BeanDefinition<?>> load() {
        Map<String, BeanDefinition<?>> definitions = new HashMap<>();
        for (var beanLoader : definitionLoaders) {
            definitions.putAll(beanLoader.load());
        }
        return definitions;
    }

    public static CompositeDefinitionLoader from(DefinitionLoader... loaders) {
        return new CompositeDefinitionLoader(loaders);
    }
}
