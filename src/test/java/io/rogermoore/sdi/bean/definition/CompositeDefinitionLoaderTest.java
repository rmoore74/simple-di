package io.rogermoore.sdi.bean.definition;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Map;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;


@ExtendWith(MockitoExtension.class)
class CompositeDefinitionLoaderTest {

    @Mock
    private DefinitionLoader firstLoader;

    @Mock
    private DefinitionLoader secondLoader;

    private CompositeDefinitionLoader underTest;

    @BeforeEach
    void setup() {
        underTest = CompositeDefinitionLoader.from(firstLoader, secondLoader);
    }

    @Test
    void givenMultipleLoaders_ensureAllAreRun() {
        given(firstLoader.load()).willReturn(Map.of("bean1", new BeanDefinition<>(this.getClass(), "bean1", true, new String[]{})));
        given(secondLoader.load()).willReturn(Map.of("bean2", new BeanDefinition<>(this.getClass(), "bean2", true, new String[]{})));
        assertThat(underTest.load()).hasSize(2);
    }

}
