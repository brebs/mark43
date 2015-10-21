package grizzley;

import java.io.IOException;
import java.net.URI;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.glassfish.jersey.grizzly2.httpserver.GrizzlyHttpServerFactory;
import org.glassfish.jersey.server.ResourceConfig;

import org.glassfish.grizzly.http.server.HttpServer;

/**
 * Hello world!
 */
public class App {

    private static final URI BASE_URI = URI.create("http://localhost:8080");

    public static void main(String[] args) {
        try {
            System.out.println("Mark43 App");

            final ResourceConfig resourceConfig = new ResourceConfig(WordsResource.class).register(SentenceResource.class);
            final HttpServer server = GrizzlyHttpServerFactory.createHttpServer(BASE_URI, resourceConfig, false);
            Runtime.getRuntime().addShutdownHook(new Thread(new Runnable() {
                @Override
                public void run() {
                    server.shutdownNow();
                }
            }));
            server.start();

            System.out.println(String.format("Application started.\nTry out %s\n"
            		+ "Available endpoints:\n"
            		+ "/words/avg_len\n"
            		+ "/words/most_com\n"
            		+ "/sentences/avg_len\n"
            		+ "Stop the application using CTRL+C",
                    BASE_URI));
            Thread.currentThread().join();
        } catch (IOException | InterruptedException ex) {
            Logger.getLogger(App.class.getName()).log(Level.SEVERE, null, ex);
        }

    }
}