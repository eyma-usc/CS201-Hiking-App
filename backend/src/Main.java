import com.sun.net.httpserver.HttpServer;
import java.net.InetSocketAddress;
import java.util.concurrent.Executors;

public class Main {
    public static void main(String[] args) throws Exception {
        HttpServer server = HttpServer.create(new InetSocketAddress(8080), 0);

        // Register handlers
        server.createContext("/register", new RegistrationHandler());
        server.createContext("/login", new LoginHandler());
        server.createContext("/submitreview", new ReviewHandler());

        int numberOfThreads = 10; 
        server.setExecutor(Executors.newFixedThreadPool(numberOfThreads));

        // Start the server
        server.start();
        System.out.println("Server started on port 8080 with " + numberOfThreads + " threads.");
    }
}
