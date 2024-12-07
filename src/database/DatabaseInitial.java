package database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class DatabaseInitial {

    private static final String URL = "jdbc:mysql://localhost/HikingApp?user=root&password=Ethanchiu0520";
    private static final String USERNAME = "root";
    private static final String PASSWORD = "Ethanchiu0520";

    public static void main(String[] args) {
        addTrails();
    }

    // Method to establish a database connection
    private static Connection connect() throws SQLException {
        return DriverManager.getConnection(URL, USERNAME, PASSWORD);
    }

    // Method to add a few trails into the Trails table
    private static void addTrails() {
        String query = "INSERT INTO trails (name, location, difficulty, length, dog_friendly, average_rating, type) VALUES (?, ?, ?, ?, ?, ?, ?)";

        try (Connection conn = connect();
             PreparedStatement ps = conn.prepareStatement(query)) {

            // Adding first trail
            ps.setString(1, "Mountain Path");
            ps.setString(2, "Mountain Region");
            ps.setString(3, "hard");
            ps.setDouble(4, 7.5);
            ps.setBoolean(5, true);
            ps.setFloat(6, 8);
            ps.setString(7, "out and back");
            ps.executeUpdate();


            System.out.println("Trails added successfully.");

        } catch (SQLException e) {
            System.out.println("SQLException in addTrails: " + e.getMessage());
        }
    }
}
