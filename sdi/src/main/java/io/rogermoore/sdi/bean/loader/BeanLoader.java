package io.rogermoore.sdi.bean.loader;

import io.rogermoore.sdi.bean.Bean;

import java.util.Map;
public interface BeanLoader {
    Map<String, Bean<?>> load();
}
