package io.rogermoore.sdi.bean.graph;

import io.rogermoore.sdi.bean.BeanInstantiationException;
import io.rogermoore.sdi.bean.definition.BeanDefinition;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.catchThrowable;

class BeanGraphTest {

    private BeanGraph underTest;

    @BeforeEach
    void setup() {
        underTest = new BeanGraph();
    }

    @Test
    void givenValidBeanWrapper_addToGraph() {
        var input = new BeanWrapper<>(DummyBean.class, "qualifier", true, new BeanWrapper[]{});

        underTest.add(input);

        assertThat(underTest.get("qualifier")).isEqualTo(input);
    }

    @Test
    void givenBeanDefinitions_loadBeans() {
        Map<String, BeanDefinition<?>> input = new HashMap<>();
        input.put("bean1", new BeanDefinition<>(DummyBean.class, "bean1", true, new String[]{}));
        input.put("bean2", new BeanDefinition<>(DummyBean.class, "bean2", true, new String[]{}));

        underTest.load(input);

        assertThat(underTest.get("bean1").getInstance()).isNotNull();
        assertThat(underTest.get("bean2").getInstance()).isNotNull();
    }

    @Test
    void givenBeansInGraphNotInitialised_throwBeanInstantiationException() {
        var bean1 = new BeanWrapper<>(DummyBean.class, "bean1", true, new BeanWrapper[]{});
        underTest.add(bean1);

        Throwable thrown = catchThrowable(() -> underTest.get("bean1").getInstance());

        assertThat(thrown)
                .isInstanceOf(BeanInstantiationException.class)
                .hasMessage("Bean has not been initialised!");
    }

    public static class DummyBean {}

}
