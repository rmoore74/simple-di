package io.rogermoore.sdi.http.server.handler;

import java.net.URI;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class RequestUtil {

    private RequestUtil() {
        throw new IllegalStateException("Do not construct!");
    }

    public static Map<String, String> getQueryParameters(URI requestUri) {
        if (requestUri == null || requestUri.getQuery() == null) {
            return Collections.emptyMap();
        }
        Map<String, String> queryParams = new HashMap<>();
        String[] p = requestUri.getQuery().split("&");
        for (var param : p) {
            String[] keyValue = param.split("=");
            queryParams.put(keyValue[0], keyValue[1]);
        }
        return queryParams;
    }
}
