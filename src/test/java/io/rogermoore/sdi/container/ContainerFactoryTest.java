package io.rogermoore.sdi.container;

import io.rogermoore.sdi.container.annotation.AnnotationContainer;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

class ContainerFactoryTest {

    @Test
    void givenBasePackage_createNewAnnotationBasedContainer() {
        Container container = ContainerFactory.newAnnotationBasedContext(this.getClass().getPackageName() + ".annotation.beans");
        assertThat(container)
                .isNotNull()
                .isInstanceOf(AnnotationContainer.class);
    }
}
