package io.rogermoore.sdi.container;

import io.rogermoore.sdi.container.annotation.AnnotationContainer;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

class ContainerFactoryTest {

    @Test
    void givenBasePackage_createNewAnnotationBasedContainer() {
        Container container = ContainerFactory.newAnnotationBasedContainer(this.getClass().getPackageName() + ".annotation.beans.config");
        container.initialise();
        assertThat(container)
                .isNotNull()
                .isInstanceOf(AnnotationContainer.class);
    }
}
