package io.rogermoore.sdi.http;

import io.rogermoore.sdi.container.Container;
import io.rogermoore.sdi.container.ContainerFactory;
import io.rogermoore.sdi.http.annotation.HttpApplication;
import io.rogermoore.sdi.http.server.Endpoint;
import io.rogermoore.sdi.http.server.Server;

public class HttpDI {

    private HttpDI() {
        throw new IllegalStateException("Do not construct.");
    }

    public static void start(final Server.Configuration configuration) {
        Container container = ContainerFactory.newAnnotationBasedContainer(getBaseBeanPackage());
        Server server = Server.from(configuration);

        for (var endpoint : Endpoint.from(container.getBeans())) {
            server.register(endpoint);
        }

        server.start();
    }

    private static String getBaseBeanPackage() {
        StackTraceElement[] stackTrace = Thread.currentThread().getStackTrace();
        StackTraceElement lastElement = stackTrace[stackTrace.length - 1];

        Class<?> caller;
        try {
            caller = Class.forName(lastElement.getClassName());
        } catch (ClassNotFoundException e) {
            throw new IllegalStateException("Unable to locate caller class!");
        }

        HttpApplication httpApplication = caller.getAnnotation(HttpApplication.class);
        if (httpApplication == null) {
            throw new IllegalArgumentException("Missing required @HttpApplication!");
        }

        return httpApplication.value().isBlank()
                ? caller.getPackageName()
                : httpApplication.value();
    }

}
