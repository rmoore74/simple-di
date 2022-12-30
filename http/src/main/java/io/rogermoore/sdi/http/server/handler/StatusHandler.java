package io.rogermoore.sdi.http.server.handler;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.IOException;

public class StatusHandler implements HttpHandler {
    @Override
    public void handle(HttpExchange exchange) throws IOException {
        var outputStream = exchange.getResponseBody();
        exchange.sendResponseHeaders(200, "RUNNING".length());
        outputStream.write("RUNNING".getBytes());
        outputStream.flush();
        outputStream.close();
    }
}
