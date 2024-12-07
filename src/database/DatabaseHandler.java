package database;

import java.sql.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class DatabaseHandler {
    private static final String DB_URL = "jdbc:postgresql://localhost:5432/osm";
    private static final String DB_USER = "youruser";
    private static final String DB_PASSWORD = "yourpassword";

    public static void insertIntersections(List<Point> intersections) {
        try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD)) {
            String sql = "INSERT INTO intersections (latitude, longitude) VALUES (?, ?)";
            PreparedStatement stmt = conn.prepareStatement(sql);

            for (Point point : intersections) {
                stmt.setDouble(1, point.getY());
                stmt.setDouble(2, point.getX());
                stmt.addBatch();
            }
            stmt.executeBatch();
            System.out.println("Intersections inserted.");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        // Call insertIntersections with your data.
    }
}
