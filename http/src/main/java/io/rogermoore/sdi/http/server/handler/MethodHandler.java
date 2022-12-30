package io.rogermoore.sdi.http.server.handler;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import io.rogermoore.sdi.http.annotation.request.Parameter;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Map;

import static io.rogermoore.sdi.http.server.handler.RequestUtil.getQueryParameters;

public class MethodHandler<T> implements HttpHandler {

    private static final ObjectMapper MAPPER = new ObjectMapper();

    private final T callerInstance;
    private final Method endpoint;

    public MethodHandler(final T callerInstance,
                         final Method endpoint) {
        this.callerInstance = callerInstance;
        this.endpoint = endpoint;
    }

    @Override
    public void handle(HttpExchange exchange) {
        try {
            Map<String, String> queryParameters = getQueryParameters(exchange.getRequestURI());

            Object[] parameters = buildMethodParameters(queryParameters);
            Object response = endpoint.invoke(callerInstance, parameters);

            writeResponse(exchange, response);
        } catch (IllegalAccessException | InvocationTargetException e) {
            throw new RuntimeException(e);
        }
    }

    private Object[] buildMethodParameters(final Map<String, String> queryParameters) {
        Object[] methodParameters = new Object[endpoint.getParameterCount()];
        for (int i = 0; i < methodParameters.length; i++) {
            var param = endpoint.getParameters()[i];
            if (param.getAnnotation(Parameter.class) != null) {
                var annotation = param.getAnnotation(Parameter.class);
                String key = annotation.value();
                if (param.getType().equals(String.class)) {
                    methodParameters[i] = String.valueOf(queryParameters.get(key));
                } else if ("int".equals(param.getType().toString()) || param.getType().equals(Integer.class)) {
                    methodParameters[i] = Integer.parseInt(queryParameters.get(key));
                }
            }
        }
        return methodParameters;
    }

    private void writeResponse(HttpExchange exchange, Object response) {
        try {
            byte[] mappedResponse = MAPPER.writeValueAsBytes(response);
            exchange.sendResponseHeaders(200, mappedResponse.length);

            var responseBody = exchange.getResponseBody();
            responseBody.write(mappedResponse);
            responseBody.flush();
            responseBody.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}
