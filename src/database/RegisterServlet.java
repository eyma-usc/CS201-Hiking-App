package database;

import org.json.JSONObject;
import at.favre.lib.crypto.bcrypt.BCrypt;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.sql.*;

@WebServlet("/register")
public class RegisterServlet extends HttpServlet {
    private static final String DB_URL = "jdbc:mysql://localhost:3306/userdb";
    private static final String DB_USER = "root";
    private static final String DB_PASSWORD = "password";

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // Set CORS headers to allow requests from frontend
        resp.setHeader("Access-Control-Allow-Origin", "http://localhost:3000");
        resp.setHeader("Access-Control-Allow-Methods", "POST, OPTIONS");
        resp.setHeader("Access-Control-Allow-Headers", "Content-Type");
        resp.setContentType("application/json");

        // Handle OPTIONS preflight request
        if ("OPTIONS".equalsIgnoreCase(req.getMethod())) {
            resp.setStatus(HttpServletResponse.SC_OK);
            return;
        }

        // Read JSON request body
        StringBuilder sb = new StringBuilder();
        try (BufferedReader reader = req.getReader()) {
            String line;
            while ((line = reader.readLine()) != null) {
                sb.append(line);
            }
        }

        try {
            JSONObject json = new JSONObject(sb.toString());
            String firstName = json.getString("firstName").trim();
            String lastName = json.getString("lastName").trim();
            String email = json.getString("email").trim();
            String password = json.getString("password").trim();

            // Validate input
            if (firstName.isEmpty() || lastName.isEmpty() || email.isEmpty() || password.isEmpty()) {
                resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                resp.getWriter().write("{\"error\":\"All fields are required\"}");
                return;
            }

            if (!email.matches("\\S+@\\S+\\.\\S+")) {
                resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                resp.getWriter().write("{\"error\":\"Invalid email format\"}");
                return;
            }

            // Check if the user already exists in the database
            try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD)) {
                String checkQuery = "SELECT * FROM users WHERE email = ?";
                try (PreparedStatement stmt = conn.prepareStatement(checkQuery)) {
                    stmt.setString(1, email);
                    ResultSet rs = stmt.executeQuery();
                    if (rs.next()) {
                        resp.setStatus(HttpServletResponse.SC_CONFLICT);
                        resp.getWriter().write("{\"error\":\"User already exists\"}");
                        return;
                    }
                }

                // Hash the password
                String hashedPassword = BCrypt.withDefaults().hashToString(12, password.toCharArray());

                // Insert the new user into the database
                String insertQuery = "INSERT INTO users (first_name, last_name, email, password) VALUES (?, ?, ?, ?)";
                try (PreparedStatement stmt = conn.prepareStatement(insertQuery)) {
                    stmt.setString(1, firstName);
                    stmt.setString(2, lastName);
                    stmt.setString(3, email);
                    stmt.setString(4, hashedPassword);
                    stmt.executeUpdate();
                }

                resp.setStatus(HttpServletResponse.SC_CREATED);
                resp.getWriter().write("{\"message\":\"User registered successfully\"}");
            }
        } catch (Exception e) {
            resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            resp.getWriter().write("{\"error\":\"An error occurred while processing your request\"}");
            e.printStackTrace();
        }
    }

    @Override
    protected void doOptions(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        resp.setHeader("Access-Control-Allow-Origin", "http://localhost:3000");
        resp.setHeader("Access-Control-Allow-Methods", "POST, OPTIONS");
        resp.setHeader("Access-Control-Allow-Headers", "Content-Type");
        resp.setStatus(HttpServletResponse.SC_OK);
    }
}
