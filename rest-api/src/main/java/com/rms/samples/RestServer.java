package com.rms.samples;

import java.io.OutputStream;
import java.io.IOException;
import java.net.InetSocketAddress;
import com.sun.net.httpserver.*;
import java.util.*;
import java.util.concurrent.Executors

public class RestServer {

    private HttpServer server;
    private int serverPort;
    private int threads;
    private Map<String, HttpHandler> restHandlers;


    public RestServer() {
        this(8000, 5);
    }

    public RestServer(int serverPort) {
        this(serverPort, 5);
    }

    public RestServer(int serverPort, int threads) {
        this.serverPort = serverPort;
        this.threads = threads;
        this.restHandlers = new HashMap<>();
    }

    public void start() throws IOException {
        if(this.server == null) {
            // initialize the server.
            this.server = HttpServer.create(new InetSocketAddress(this.serverPort), 0);

            // Initialize the handlers
            this.restHandlers.forEach((e, h) -> {
                server.createContext(e, h);
            });

            // Initialize the server
            server.setExecutor(Executors.newFixedThreadPool(this.threads));
            server.start();
        }
    }

    public void stop(){
        if(this.server != null) {
            this.server.stop(0);
            this.server = null;
        }
    }

    protected void SetHandler(String endpoint, HttpHandler handler) {
        this.restHandlers.put(endpoint.toLowerCase(), handler);
    }

    protected void SendResponse(String respText, HttpExchange exchange) throws IOException {
        exchange.sendResponseHeaders(200, respText.getBytes().length);
        OutputStream output = exchange.getResponseBody();
        output.write(respText.getBytes());
        output.flush();
        exchange.close();
    }
}
