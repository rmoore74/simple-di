package io.rogermoore.sdi.container.annotation;

import io.rogermoore.sdi.bean.graph.BeanGraph;
import io.rogermoore.sdi.bean.definition.DefinitionLoader;
import io.rogermoore.sdi.container.Container;

public class AnnotationContainer extends Container {

    private final DefinitionLoader definitionLoader;

    public AnnotationContainer(final DefinitionLoader definitionLoader) {
        super(new BeanGraph());
        this.definitionLoader = definitionLoader;
    }

    @Override
    public void initialise() {
        var definitions = definitionLoader.load();
        getBeanGraph()
                .load(definitions);
        setInitialised();
    }
}
