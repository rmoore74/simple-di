package io.rogermoore.sdi.container.annotation;

import io.rogermoore.sdi.bean.BeanGraph;
import io.rogermoore.sdi.container.annotation.beans.FirstBean;
import io.rogermoore.sdi.container.annotation.beans.NotAnnotated;
import io.rogermoore.sdi.container.annotation.beans.second.SecondBean;
import io.rogermoore.sdi.container.annotation.beans.second.fourth.FourthBean;
import io.rogermoore.sdi.container.annotation.beans.second.fourth.fifth.FifthBean;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class AnnotationBeanGraphHelperTest {

    @Spy
    private BeanGraph beanGraph;

    private AnnotationBeanGraphHelper underTest;

    @BeforeEach
    void setup() {
        underTest = new AnnotationBeanGraphHelper(beanGraph);
    }

    @Test
    void givenAnnotatedBeanClassWithDependencies_addBeansToGraph() {
        underTest.add(FirstBean.class);

        underTest.initialiseGraph();

        assertThat(underTest.get(FirstBean.class)).isNotNull();
        assertThat(underTest.get(FourthBean.class)).isNotNull();
    }

    @Test
    void givenBeanWithQualifier_addBeansToGraph() {
        underTest.add(SecondBean.class);

        underTest.initialiseGraph();

        assertThat((SecondBean) underTest.get("qualifiedPrototypeBean")).isNotNull();
    }

    @Test
    void givenQualifiedBeanButAccessUsingClass_expectBeanReturned() {
        underTest.add(SecondBean.class);

        underTest.initialiseGraph();

        assertThat(underTest.get(SecondBean.class)).isNotNull();
    }

    @Test
    void givenBeanWithDependency_avoidRedundantLoads() {
        underTest.add(SecondBean.class);
        underTest.add(FifthBean.class);

        verify(beanGraph, times(2)).add(any());
    }

    @Test
    void givenNonAnnotatedClass_expectException() {
        Throwable thrown = catchThrowable(() -> underTest.add(NotAnnotated.class));
        assertThat(thrown)
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("Class provided is not a bean!");
    }

}
