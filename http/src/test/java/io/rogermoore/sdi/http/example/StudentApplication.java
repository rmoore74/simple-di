package io.rogermoore.sdi.http.example;

import io.rogermoore.sdi.http.HttpDI;
import io.rogermoore.sdi.http.server.Server;
import io.rogermoore.sdi.http.annotation.HttpApplication;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

@HttpApplication("io.rogermoore.sdi.http.example")
public class StudentApplication {

    private static final Executor REQUEST_THREAD_EXECUTOR = Executors.newFixedThreadPool(10);
    private static final String HOST = "localhost";
    private static final int PORT = 8001;

    public static void main(String[] args) {
        var configuration = Server.newConfigurationBuilder()
                .executor(REQUEST_THREAD_EXECUTOR)
                .host(HOST)
                .port(PORT)
                .build();
        HttpDI.start(configuration);
    }
}
