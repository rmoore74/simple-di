package io.rogermoore.sdi.bean;

import io.rogermoore.sdi.container.annotation.provider.ClassBeanProvider;
import org.junit.jupiter.api.Test;

import java.util.Map;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

class BeanGraphTest {

    @Test
    void givenBeanMap_loadBeans() {
        var input = new Bean<>(DummyBean.class, "qualifier", true, Map.of(), new ClassBeanProvider<>(DummyBean.class));

        var underTest = new BeanGraph(Map.of("qualifier", input));

        assertThat(underTest.get("qualifier")).isEqualTo(input);
    }

    public static class DummyBean {}

}
