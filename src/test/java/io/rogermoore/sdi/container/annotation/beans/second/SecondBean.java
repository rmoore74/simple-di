package io.rogermoore.sdi.container.annotation.beans.second;

import io.rogermoore.sdi.container.annotation.Bean;

@Bean(qualifier = "qualifiedPrototypeBean", singleton = false)
public class SecondBean {
}
