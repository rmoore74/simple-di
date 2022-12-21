package io.rogermoore.sdi.container.annotation;

import io.rogermoore.sdi.bean.BeanGraph;
import io.rogermoore.sdi.container.annotation.beans.FirstBean;
import io.rogermoore.sdi.container.annotation.beans.NotAnnotated;
import io.rogermoore.sdi.container.annotation.beans.second.SecondBean;
import io.rogermoore.sdi.container.annotation.beans.second.fourth.FourthBean;
import io.rogermoore.sdi.container.annotation.beans.second.third.ThirdBean;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

class AnnotationContainerTest {

    private static final String BASE_PACKAGE = "io.rogermoore.sdi.container.annotation.beans";

    private AnnotationBeanLoader beanLoader;
    private AnnotationBeanGraphHelper beanGraphHelper;

    @BeforeEach
    void setup() {
        beanLoader = new AnnotationBeanLoader(BASE_PACKAGE);
        beanGraphHelper = new AnnotationBeanGraphHelper(new BeanGraph());
    }

    @Test
    void givenBasePackage_loadAllAnnotatedBeans() {
        var container = new AnnotationContainer(beanLoader, beanGraphHelper);

        assertThat(container.getBean(FirstBean.class)).isNotNull();
        assertThat((SecondBean) container.getBean("qualifiedPrototypeBean")).isNotNull();
        assertThat((ThirdBean) container.getBean("thirdBean")).isNotNull();
        assertThat(container.getBean(FourthBean.class)).isNotNull();
        assertThat(container.getBean(NotAnnotated.class)).isNull();
    }

    @Test
    void givenClassInsteadOfQualifier_fallBackToUsingQualifierProvidedInAnnotation() {
        var container = new AnnotationContainer(beanLoader, beanGraphHelper);
        assertThat(container.getBean(SecondBean.class)).isNotNull();
    }

}
