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

public class ReviewHandler implements HttpHandler {

    @Override
    public void handle(HttpExchange exchange) {
        try {
            // Add CORS headers to every response
            Headers headers = exchange.getResponseHeaders();
            headers.add("Access-Control-Allow-Origin", "*"); // Allow all origins
            headers.add("Access-Control-Allow-Methods", "GET, POST, OPTIONS");
            headers.add("Access-Control-Allow-Headers", "Content-Type, Cookie");

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

            // Validate user authentication using the session token
            Headers requestHeaders = exchange.getRequestHeaders();
            String cookies = requestHeaders.getFirst("Cookie");
            String sessionToken = TokenManager.getTokenFromCookies(cookies);

            if (sessionToken == null || !TokenManager.isValidToken(sessionToken)) {
                sendResponse(exchange, 401, "Unauthorized. Please log in.");
                return;
            }

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

            // Validate and extract review details
            Integer rating = null;
            String recommendation = json.has("recommendation") ? json.get("recommendation").getAsString() : null;
            String comments = json.has("comments") ? json.get("comments").getAsString() : null;

            if (json.has("rating")) {
                try {
                    rating = json.get("rating").getAsInt();
                } catch (NumberFormatException e) {
                    sendResponse(exchange, 400, "Rating must be an integer.");
                    return;
                }
            }

            if (rating == null || recommendation == null) {
                sendResponse(exchange, 400, "Missing required fields: rating or recommendation.");
                return;
            }

            // Create a new Review instance
            Review review = new Review(rating, recommendation, comments);
            ReviewDAO reviewDAO = new ReviewDAO();

            try {
                reviewDAO.submitReview(review);
                sendResponse(exchange, 200, "Review submission successful!");
            } catch (SQLException e) {
                e.printStackTrace();
                sendResponse(exchange, 500, "Database error: " + e.getMessage());
            }

        } catch (Exception e) {
            e.printStackTrace();
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
            e.printStackTrace();
        }
    }
}
