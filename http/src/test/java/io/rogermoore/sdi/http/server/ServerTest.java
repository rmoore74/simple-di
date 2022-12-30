package io.rogermoore.sdi.http.server;

import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;
import io.rogermoore.sdi.http.server.handler.StatusHandler;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.net.InetSocketAddress;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class ServerTest {

    @Mock
    private HttpServer httpServer;

    @Mock
    private HttpHandler handler;

    private Server underTest;

    @BeforeEach
    void setup() {
        underTest = new Server(httpServer);
    }

    @Test
    void givenEndpoint_registerWithHttpServer() {
        underTest.register(new Endpoint("/test", handler));

        verify(httpServer).createContext("/test", handler);
    }

    @Test
    void startServer_expectServerInitialisedCorrectly() {
        given(httpServer.getAddress()).willReturn(new InetSocketAddress("dummyaddress", 8080));

        underTest.start();

        verify(httpServer).createContext(eq("/status"), any(StatusHandler.class));
        verify(httpServer).start();
    }

}
