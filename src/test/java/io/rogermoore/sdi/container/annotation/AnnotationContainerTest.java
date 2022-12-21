package io.rogermoore.sdi.container.annotation;

import io.rogermoore.sdi.container.annotation.beans.FirstBean;
import io.rogermoore.sdi.container.annotation.beans.NotAnnotated;
import io.rogermoore.sdi.container.annotation.beans.second.SecondBean;
import io.rogermoore.sdi.container.annotation.beans.second.fourth.FourthBean;
import io.rogermoore.sdi.container.annotation.beans.second.third.ThirdBean;
import io.rogermoore.sdi.container.annotation.loader.ClassBeanDefinitionLoader;
import io.rogermoore.sdi.container.annotation.loader.MethodBeanDefinitionLoader;
import io.rogermoore.sdi.container.annotation.loader.util.ClassLoaderUtil;
import org.junit.jupiter.api.Test;

import java.util.Set;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

class AnnotationContainerTest {

    private static final String BASE_PACKAGE = "io.rogermoore.sdi.container.annotation.beans";

    @Test
    void givenBasePackage_loadAllAnnotatedBeans() {
        Set<Class<?>> classes = ClassLoaderUtil.loadClassesInPackage(BASE_PACKAGE);
        var container = new AnnotationContainer(new ClassBeanDefinitionLoader(classes));

        container.initialise();

        assertThat(container.getBean(FirstBean.class)).isNotNull();
        assertThat(container.getBean("qualifiedPrototypeBean", SecondBean.class)).isNotNull();
        assertThat(container.getBean("thirdBean", ThirdBean.class)).isNotNull();
        assertThat(container.getBean(FourthBean.class)).isNotNull();
        assertThat(container.getBean(NotAnnotated.class)).isNull();
    }

    @Test
    void givenClassInsteadOfQualifier_fallBackToUsingQualifierProvidedInAnnotation() {
        Set<Class<?>> classes = ClassLoaderUtil.loadClassesInPackage(BASE_PACKAGE);
        var container = new AnnotationContainer(new ClassBeanDefinitionLoader(classes));

        container.initialise();

        assertThat(container.getBean(SecondBean.class)).isNotNull();
    }

    @Test
    void givenMethodLevelLoader_loadAllBeanAnnotatedMethods() {
        Set<Class<?>> classes = ClassLoaderUtil.loadClassesInPackage(BASE_PACKAGE + ".config");
        var container = new AnnotationContainer(new MethodBeanDefinitionLoader(classes));

        container.initialise();

        assertThat(container.getBean(FirstBean.class)).isNotNull();
        assertThat(container.getBean(FourthBean.class)).isNotNull();
    }

}
