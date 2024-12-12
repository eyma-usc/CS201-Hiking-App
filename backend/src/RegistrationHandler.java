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
import java.util.logging.Level;
import java.util.logging.Logger;

public class RegistrationHandler implements HttpHandler {
    private static final Logger logger = Logger.getLogger(RegistrationHandler.class.getName());

    @Override
    public void handle(HttpExchange exchange) {
        try {
            // Add CORS headers to every response
            Headers headers = exchange.getResponseHeaders();
            headers.add("Access-Control-Allow-Origin", "*");
            headers.add("Access-Control-Allow-Methods", "GET, POST, OPTIONS");
            headers.add("Access-Control-Allow-Headers", "Content-Type");

            if ("OPTIONS".equalsIgnoreCase(exchange.getRequestMethod())) {
                exchange.sendResponseHeaders(204, -1); // Preflight request handling
                return;
            }

            if ("POST".equalsIgnoreCase(exchange.getRequestMethod())) {
                handlePostRequest(exchange);
            } else {
                sendResponse(exchange, 405, "Method not allowed");
            }
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Unexpected error", e);
            sendResponse(exchange, 500, "Internal server error");
        }
    }

    private void handlePostRequest(HttpExchange exchange) {
        try (BufferedReader reader = new BufferedReader(
                new InputStreamReader(exchange.getRequestBody(), StandardCharsets.UTF_8))) {

            StringBuilder requestBody = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                requestBody.append(line);
            }

            logger.info("Received request body: " + requestBody);

            JsonObject json = parseJson(requestBody.toString(), exchange);
            if (json == null) return;

            // Validate and extract user details
            String firstName = extractJsonField(json, "firstName", exchange);
            String lastName = extractJsonField(json, "lastName", exchange);
            String email = extractJsonField(json, "email", exchange);
            String password = extractJsonField(json, "password", exchange);

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
                handleDatabaseError(exchange, e);
            }

        } catch (Exception e) {
            logger.log(Level.WARNING, "Invalid request", e);
            sendResponse(exchange, 400, "Invalid request");
        }
    }

    private JsonObject parseJson(String body, HttpExchange exchange) {
        try {
            return JsonParser.parseString(body).getAsJsonObject();
        } catch (Exception e) {
            sendResponse(exchange, 400, "Invalid JSON format");
            logger.log(Level.WARNING, "Invalid JSON: " + body, e);
            return null;
        }
    }

    private String extractJsonField(JsonObject json, String field, HttpExchange exchange) {
        if (json.has(field) && !json.get(field).getAsString().isEmpty()) {
            return json.get(field).getAsString();
        }
        sendResponse(exchange, 400, "Missing or empty field: " + field);
        return null;
    }

    private void handleDatabaseError(HttpExchange exchange, SQLException e) {
        if (e.getMessage().contains("Duplicate entry")) {
            sendResponse(exchange, 400, "Email already registered.");
        } else {
            logger.log(Level.SEVERE, "Database error", e);
            sendResponse(exchange, 500, "Database error: " + e.getMessage());
        }
    }

    private void sendResponse(HttpExchange exchange, int statusCode, String message) {
        try {
            exchange.sendResponseHeaders(statusCode, message.getBytes().length);
            try (OutputStream os = exchange.getResponseBody()) {
                os.write(message.getBytes(StandardCharsets.UTF_8));
            }
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Error sending response", e);
        }
    }
}
