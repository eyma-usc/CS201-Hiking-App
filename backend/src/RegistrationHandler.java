import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.sun.net.httpserver.Headers;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
import java.sql.SQLException;

public class RegistrationHandler implements HttpHandler {
    @Override
    public void handle(HttpExchange exchange) {
        try {
            // Add CORS headers to every response
            Headers headers = exchange.getResponseHeaders();
            headers.add("Access-Control-Allow-Origin", "*"); // Allow all origins
            headers.add("Access-Control-Allow-Methods", "GET, POST, OPTIONS");
            headers.add("Access-Control-Allow-Headers", "Content-Type");

            // Handle preflight (OPTIONS) requests
            if ("OPTIONS".equalsIgnoreCase(exchange.getRequestMethod())) {
                exchange.sendResponseHeaders(204, -1); // No content for OPTIONS requests
                return;
            }

            if ("POST".equalsIgnoreCase(exchange.getRequestMethod())) {
                handlePostRequest(exchange);
            } else {
                sendResponse(exchange, 405, "Method not allowed");
            }
        } catch (Exception e) {
            e.printStackTrace(); // Log unexpected errors
            sendResponse(exchange, 500, "Internal server error: " + e.getMessage());
        }
    }

    private void handlePostRequest(HttpExchange exchange) {
        try (BufferedReader reader = new BufferedReader(
                new InputStreamReader(exchange.getRequestBody(), StandardCharsets.UTF_8))) {

            // Read and parse the JSON request body
            StringBuilder requestBody = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                requestBody.append(line);
            }

            // Log the received request body for debugging
            System.out.println("Received request body: " + requestBody);

            JsonObject json;
            try {
                json = JsonParser.parseString(requestBody.toString()).getAsJsonObject();
            } catch (Exception e) {
                sendResponse(exchange, 400, "Invalid JSON format: " + e.getMessage());
                return;
            }

            // Validate and extract user details
            String firstName = json.has("firstName") ? json.get("firstName").getAsString() : null;
            String lastName = json.has("lastName") ? json.get("lastName").getAsString() : null;
            String email = json.has("email") ? json.get("email").getAsString() : null;
            String password = json.has("password") ? json.get("password").getAsString() : null;

            if (firstName == null || lastName == null || email == null || password == null) {
                sendResponse(exchange, 400, "Missing required fields: firstName, lastName, email, or password.");
                return;
            }

            User user = new User(firstName, lastName, email, password);
            UserDAO userDAO = new UserDAO();

            try {
                userDAO.registerUser(user);
                sendResponse(exchange, 200, "Registration successful!");
            } catch (SQLException e) {
                if (e.getMessage().contains("Duplicate entry")) {
                    sendResponse(exchange, 400, "Email already registered.");
                } else {
                    e.printStackTrace(); // Log database errors
                    sendResponse(exchange, 500, "Database error: " + e.getMessage());
                }
            }

        } catch (Exception e) {
            e.printStackTrace(); // Log unexpected errors
            sendResponse(exchange, 400, "Invalid request: " + e.getMessage());
        }
    }

    private void sendResponse(HttpExchange exchange, int statusCode, String message) {
        try {
            exchange.sendResponseHeaders(statusCode, message.getBytes().length);
            try (OutputStream os = exchange.getResponseBody()) {
                os.write(message.getBytes());
            }
        } catch (Exception e) {
            e.printStackTrace(); // Log errors while sending responses
        }
    }
}
