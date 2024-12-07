package database;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class TrailService {

    // Fetch trail data from the database
    public static List<TrailSummary> fetchTrails(String name, String location, String difficulty, String lengthRange, Boolean dogFriendly) {
        List<TrailSummary> trails = new ArrayList<>();
        
        // Base SQL query
        StringBuilder query = new StringBuilder("SELECT name, location, difficulty, length_miles FROM Trails WHERE 1=1");
        List<Object> params = new ArrayList<>();

        // Dynamically build query based on parameters
        if (name != null && !name.isEmpty()) {
            query.append(" AND name LIKE ?");
            params.add("%" + name + "%");
        }
        if (location != null && !location.isEmpty()) {
            query.append(" AND location LIKE ?");
            params.add("%" + location + "%");
        }
        if (difficulty != null && !difficulty.isEmpty()) {
            query.append(" AND difficulty = ?");
            params.add(difficulty);
        }
        if (lengthRange != null) {
            switch (lengthRange) {
                case "under 5":
                    query.append(" AND length_miles < 5");
                    break;
                case "5-10":
                    query.append(" AND length_miles BETWEEN 5 AND 10");
                    break;
                case "over 10":
                    query.append(" AND length_miles > 10");
                    break;
            }
        }
        if (dogFriendly != null) {
            query.append(" AND dog_friendly = ?");
            params.add(dogFriendly);
        }

        // Execute query
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query.toString())) {

            // Set query parameters dynamically
            for (int i = 0; i < params.size(); i++) {
                stmt.setObject(i + 1, params.get(i));
            }

            // Execute query and process results
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                trails.add(new TrailSummary(
                        rs.getString("name"),
                        rs.getString("location"),
                        rs.getString("difficulty"),
                        rs.getDouble("length_miles")
                ));
            }

        } catch (SQLException e) {
            System.err.println("Error fetching trail data: " + e.getMessage());
            e.printStackTrace();
        }

        return trails;
    }
    
}
