package io.rogermoore.sdi.container.exception;

public class ContainerInitialisationException extends RuntimeException {

    private static final String MESSAGE = "Unable to locate beans under %s.";

    public ContainerInitialisationException(final String basePackage) {
        super(String.format(MESSAGE, basePackage));
    }
}
