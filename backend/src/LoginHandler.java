import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.sun.net.httpserver.Headers;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
import java.sql.ResultSet;
import java.sql.SQLException;

public class LoginHandler implements HttpHandler {

    @Override
    public void handle(HttpExchange exchange) {
        try {
            Headers headers = exchange.getResponseHeaders();
            headers.add("Access-Control-Allow-Origin", "*");
            headers.add("Access-Control-Allow-Methods", "POST, OPTIONS");
            headers.add("Access-Control-Allow-Headers", "Content-Type");

            if ("OPTIONS".equalsIgnoreCase(exchange.getRequestMethod())) {
                exchange.sendResponseHeaders(204, -1); // No content for preflight requests
                return;
            }

            if ("POST".equalsIgnoreCase(exchange.getRequestMethod())) {
                handlePostRequest(exchange);
            } else {
                sendResponse(exchange, 405, "Method not allowed");
            }
        } catch (Exception e) {
            e.printStackTrace();
            sendResponse(exchange, 500, "Internal server error: " + e.getMessage());
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

        JsonObject json = JsonParser.parseString(requestBody.toString()).getAsJsonObject();
        String email = json.get("email").getAsString();
        String password = json.get("password").getAsString();

        UserDAO userDAO = new UserDAO();
        User user = userDAO.getUserByEmail(email);

        if (user != null && user.getPassword().equals(password)) {
                // Set isLoggedIn flag in local storage (client-side responsibility)
                exchange.getResponseHeaders().add("Set-Cookie", "isLoggedIn=true; Path=/; HttpOnly; SameSite=Strict"); 
                sendResponse(exchange, 200, "Login successful!");
            } else {
            sendResponse(exchange, 404, "User not found. Please register.");
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
