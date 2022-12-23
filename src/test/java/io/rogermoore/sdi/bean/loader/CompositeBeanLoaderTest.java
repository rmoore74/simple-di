package io.rogermoore.sdi.bean.loader;

import io.rogermoore.sdi.bean.Bean;
import io.rogermoore.sdi.container.annotation.provider.ClassBeanProvider;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Map;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;
import static org.mockito.BDDMockito.given;


@ExtendWith(MockitoExtension.class)
class CompositeBeanLoaderTest {

    @Mock
    private BeanLoader firstLoader;

    @Mock
    private BeanLoader secondLoader;

    private CompositeBeanLoader underTest;

    @BeforeEach
    void setup() {
        underTest = CompositeBeanLoader.from(firstLoader, secondLoader);
    }

    @Test
    void givenMultipleLoaders_ensureAllAreRun() {
        given(firstLoader.load()).willReturn(Map.of("bean1", new Bean<>(this.getClass(), "bean1", true, Map.of(), new ClassBeanProvider<>(null))));
        given(secondLoader.load()).willReturn(Map.of("bean2", new Bean<>(this.getClass(), "bean2", true, Map.of(), new ClassBeanProvider<>(null))));
        assertThat(underTest.load()).hasSize(2);
    }

}
