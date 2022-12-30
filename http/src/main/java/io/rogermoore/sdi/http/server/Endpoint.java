package io.rogermoore.sdi.http.server;

import com.sun.net.httpserver.HttpHandler;
import io.rogermoore.sdi.bean.Bean;
import io.rogermoore.sdi.http.annotation.method.Get;
import io.rogermoore.sdi.http.server.handler.MethodHandler;

import java.util.ArrayList;
import java.util.Collection;

public record Endpoint(String path, HttpHandler handler) {
    public static Collection<Endpoint> from(Collection<Bean<?>> beans) {
        Collection<Endpoint> endpoints = new ArrayList<>();
        for (var bean : beans) {
            Object instance = bean.getInstance();
            for (var method : instance.getClass().getMethods()) {
                if (method.getAnnotation(Get.class) != null) {
                    Get getAnnotation = method.getAnnotation(Get.class);
                    endpoints.add(new Endpoint(getAnnotation.value(), new MethodHandler<>(instance, method)));
                }
            }
        }
        return endpoints;
    }
}
