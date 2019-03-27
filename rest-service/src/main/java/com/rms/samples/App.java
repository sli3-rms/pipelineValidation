package com.rms.samples;

//import com.rms.samples.ConfigServer;
import java.io.IOException;


public class App {

    public static void main(String[] args) {

        try {
            ConfigServer server = new ConfigServer();

            Runtime.getRuntime().addShutdownHook( new Thread(() -> {

                System.out.println("Got shutdown signal.");
                server.stop();
                System.out.println("Server Stopped.");
            }));

            System.out.println("Starting Server.");
            server.start();
        }
        catch (IOException e) {
        }
    }
}
