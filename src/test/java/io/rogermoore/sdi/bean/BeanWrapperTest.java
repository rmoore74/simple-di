package io.rogermoore.sdi.bean;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.catchThrowable;

class BeanWrapperTest {

    @Test
    void givenNonInitialisedBean_throwExceptionOnGetInstance() {
        var bean = new BeanWrapper<>(DummyBean.class, "qualifier", true, new BeanWrapper[]{});

        Throwable thrown = catchThrowable(bean::getInstance);

        assertThat(thrown)
                .isInstanceOf(BeanInstantiationException.class)
                .hasMessage("Bean has not been initialised!");
    }

    @Test
    void givenSingletonBean_onlyCreateOneInstance() {
        var bean = new BeanWrapper<>(DummyBean.class, "qualifier", true, new BeanWrapper[]{});
        bean.init();

        assertThat(bean.getInstance()).isEqualTo(bean.getInstance());
    }

    @Test
    void givenPrototypBean_createUniqueInstanceEachTime() {
        var bean = new BeanWrapper<>(DummyBean.class, "qualifier", false, new BeanWrapper[]{});
        bean.init();

        assertThat(bean.getInstance()).isNotEqualTo(bean.getInstance());
    }

    @Test
    void givenBeanWithDependencies_ensureAllBeansLoaded() {
        var innerBean = new BeanWrapper<>(DummyBean.class, "innerBean", true, new BeanWrapper[]{});
        var outerBean = new BeanWrapper<>(OuterBean.class, "outerBean", true, new BeanWrapper[]{ innerBean });

        outerBean.init();

        assertThat(outerBean.getInstance()).isNotNull();
        assertThat(innerBean.getInstance()).isNotNull();
    }

    public static class DummyBean {}
    public static class OuterBean {
        public OuterBean(DummyBean dummyBean) {}
    }

}
