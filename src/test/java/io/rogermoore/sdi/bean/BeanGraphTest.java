package io.rogermoore.sdi.bean;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

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
    void givenBeansInGraph_initialiseBeans() {
        var bean1 = new BeanWrapper<>(DummyBean.class, "bean1", true, new BeanWrapper[]{});
        underTest.add(bean1);
        var bean2 = new BeanWrapper<>(DummyBean.class, "bean2", true, new BeanWrapper[]{});
        underTest.add(bean2);

        underTest.init();

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
