package io.rogermoore.sdi.container.annotation;

import io.rogermoore.sdi.container.annotation.beans.FirstBean;
import io.rogermoore.sdi.container.annotation.beans.second.SecondBean;
import io.rogermoore.sdi.container.annotation.beans.second.fourth.FourthBean;
import io.rogermoore.sdi.container.annotation.beans.second.fourth.fifth.FifthBean;
import io.rogermoore.sdi.container.annotation.beans.second.third.ThirdBean;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class AnnotationBeanLoaderTest {

    @Test
    void givenBasePackage_loadAnnotatedClasses() {
        AnnotationBeanLoader beanLoader = new AnnotationBeanLoader(this.getClass().getPackageName() + ".beans");
        assertThat(beanLoader.load())
                .hasSize(5)
                .containsExactlyInAnyOrder(
                        FirstBean.class,
                        SecondBean.class,
                        ThirdBean.class,
                        FourthBean.class,
                        FifthBean.class
                );
    }

}
