package io.rogermoore.sdi.bean.loader;

import io.rogermoore.sdi.bean.Bean;

import java.util.HashMap;
import java.util.Map;

public class CompositeBeanLoader implements BeanLoader {

    private final BeanLoader[] beanLoaders;

    private CompositeBeanLoader(final BeanLoader[] beanLoaders) {
        this.beanLoaders = beanLoaders;
    }

    @Override
    public Map<String, Bean<?>> load() {
        Map<String, Bean<?>> definitions = new HashMap<>();
        for (var beanLoader : beanLoaders) {
            definitions.putAll(beanLoader.load());
        }
        return definitions;
    }

    public static CompositeBeanLoader from(BeanLoader... loaders) {
        return new CompositeBeanLoader(loaders);
    }
}
