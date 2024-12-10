import com.sun.net.httpserver.HttpServer;
import java.io.IOException;
import java.net.InetSocketAddress;

public class Main {
    public static void main(String[] args) throws IOException {
        HttpServer server = HttpServer.create(new InetSocketAddress(8080), 0);
        server.createContext("/register", new RegistrationHandler());
        server.setExecutor(null); // Default executor
        System.out.println("Server started on port 8080...");
        server.start();
    }
}
