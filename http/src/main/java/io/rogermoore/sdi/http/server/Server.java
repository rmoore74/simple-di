package io.rogermoore.sdi.http.server;

import com.sun.net.httpserver.HttpServer;
import com.sun.net.httpserver.HttpsServer;
import io.rogermoore.sdi.http.server.handler.StatusHandler;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.util.concurrent.Executor;

public class Server {

    private static final Logger LOGGER = LogManager.getLogger(Server.class);

    private final HttpServer httpServer;
    private final long startTime;

    Server(final HttpServer httpServer) {
        this.httpServer = httpServer;
        this.startTime = System.currentTimeMillis();
    }

    public void register(final Endpoint endpoint) {
        httpServer.createContext(endpoint.path(), endpoint.handler());
        LOGGER.info("Registered endpoint {}", endpoint.path());
    }

    public void start() {
        register(new Endpoint("/status", new StatusHandler()));
        httpServer.start();
        LOGGER.info("Started HttpDI server on {}:{}. Started in {}ms.", httpServer.getAddress().getHostName(),
                httpServer.getAddress().getPort(), System.currentTimeMillis() - startTime);
    }

    public static Server from(Configuration configuration) {
        var address = new InetSocketAddress(configuration.host, configuration.port);
        HttpServer server;
        try {
            server = configuration.https
                    ? HttpsServer.create(address, 0)
                    : HttpServer.create(address, 0);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        server.setExecutor(configuration.executor);
        return new Server(server);
    }

    public static Configuration.Builder newConfigurationBuilder() {
        return new Configuration.Builder();
    }

    public static class Configuration {
        private final String host;
        private final int port;
        private final boolean https;
        private final Executor executor;

        private Configuration(Builder builder) {
            this.host = builder.host;
            this.port = builder.port;
            this.https = builder.https;
            this.executor = builder.executor;
        }

        public static class Builder {
            private String host;
            private int port;
            private boolean https;
            private Executor executor;

            public Builder host(String host) {
                this.host = host;
                return this;
            }

            public Builder port(int port) {
                this.port = port;
                return this;
            }

            public Builder https(boolean https) {
                this.https = https;
                return this;
            }

            public Builder executor(Executor executor) {
                this.executor = executor;
                return this;
            }

            public Configuration build() {
                return new Configuration(this);
            }
        }
    }
}
