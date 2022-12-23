package io.rogermoore.sdi.container;

import io.rogermoore.sdi.container.annotation.beans.FirstBean;
import io.rogermoore.sdi.container.annotation.beans.NotAnnotated;
import io.rogermoore.sdi.container.annotation.beans.second.SecondBean;
import io.rogermoore.sdi.container.annotation.beans.second.fourth.FourthBean;
import io.rogermoore.sdi.container.annotation.beans.second.third.ThirdBean;
import io.rogermoore.sdi.container.annotation.loader.util.ClassLoaderUtil;
import org.junit.jupiter.api.Test;

import java.util.Set;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

class ContainerFactoryTest {

    @Test
    void givenBasePackage_createNewAnnotationBasedContainer() {
        Container container = ContainerFactory.newAnnotationBasedContainer(this.getClass().getPackageName() + ".annotation.beans");
        assertThat(container).isNotNull();
    }


    @Test
    void givenBasePackage_loadAllAnnotatedBeans() {

        Container container = ContainerFactory.newAnnotationBasedContainer(this.getClass().getPackageName() + ".annotation.beans");

        assertThat(container.getBean(FirstBean.class)).isNotNull();
        assertThat(container.getBean("qualifiedPrototypeBean", SecondBean.class)).isNotNull();
        assertThat(container.getBean("thirdBean", ThirdBean.class)).isNotNull();
        assertThat(container.getBean(FourthBean.class)).isNotNull();
        assertThat(container.getBean(NotAnnotated.class)).isNull();
    }

    @Test
    void givenClassInsteadOfQualifier_fallBackToUsingQualifierProvidedInAnnotation() {
        Container container = ContainerFactory.newAnnotationBasedContainer(this.getClass().getPackageName() + ".annotation.beans");
        assertThat(container.getBean(SecondBean.class)).isNotNull();
    }

    @Test
    void givenMethodLevelLoader_loadAllBeanAnnotatedMethods() {
        Container container = ContainerFactory.newAnnotationBasedContainer(this.getClass().getPackageName() + ".annotation.beans.config");

        assertThat(container.getBean(FirstBean.class)).isNotNull();
        assertThat(container.getBean(FourthBean.class)).isNotNull();
    }
}
