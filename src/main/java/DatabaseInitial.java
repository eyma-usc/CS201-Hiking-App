

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class DatabaseInitial {

    private static final String URL = "jdbc:mysql://localhost/HikingApp";
    private static final String USERNAME = "root";
    private static final String PASSWORD = "Ethanchiu0520";

    public static void main(String[] args) {

        List<Trail> trails = fetchFilteredTrails(
            Optional.of("Mountain Path"),
            Optional.empty(),
            Optional.of("hard"),
            Optional.empty(),
            Optional.empty(),
            Optional.empty(),
            Optional.empty()
        );

        for (Trail trail : trails) {
            System.out.println(trail);
        }
    }

    // Method to establish a database connection
    private static Connection connect() throws SQLException {
        return DriverManager.getConnection(URL, USERNAME, PASSWORD);
    }

    // Method to fetch trails based on user-defined filters
    public static List<Trail> fetchFilteredTrails(
        Optional<String> name,
        Optional<String> location,
        Optional<String> difficulty,
        Optional<Double> minLength,
        Optional<Double> maxLength,
        Optional<Boolean> dogFriendly,
        Optional<String> type
    ) {
        List<Trail> trails = new ArrayList<>();
        StringBuilder query = new StringBuilder("SELECT * FROM trails WHERE 1=1");

        if (name.isPresent()) {
            query.append(" AND name LIKE ?");
        }
        if (location.isPresent()) {
            query.append(" AND location LIKE ?");
        }
        if (difficulty.isPresent()) {
            query.append(" AND difficulty = ?");
        }
        if (minLength.isPresent()) {
            query.append(" AND length >= ?");
        }
        if (maxLength.isPresent()) {
            query.append(" AND length <= ?");
        }
        if (dogFriendly.isPresent()) {
            query.append(" AND dog_friendly = ?");
        }
        if (type.isPresent()) {
            query.append(" AND type = ?");
        }

        try (Connection conn = connect();
             PreparedStatement ps = conn.prepareStatement(query.toString())) {

            int paramIndex = 1;

            if (name.isPresent()) {
                ps.setString(paramIndex++, "%" + name.get() + "%");
            }
            if (location.isPresent()) {
                ps.setString(paramIndex++, "%" + location.get() + "%");
            }
            if (difficulty.isPresent()) {
                ps.setString(paramIndex++, difficulty.get());
            }
            if (minLength.isPresent()) {
                ps.setDouble(paramIndex++, minLength.get());
            }
            if (maxLength.isPresent()) {
                ps.setDouble(paramIndex++, maxLength.get());
            }
            if (dogFriendly.isPresent()) {
                ps.setBoolean(paramIndex++, dogFriendly.get());
            }
            if (type.isPresent()) {
                ps.setString(paramIndex++, type.get());
            }

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                Trail trail = new Trail(
                    rs.getInt("trailId"),
                    rs.getString("name"),
                    rs.getString("location"),
                    rs.getString("difficulty"),
                    rs.getDouble("length"),
                    rs.getBoolean("dog_friendly"),
                    rs.getFloat("average_rating"),
                    rs.getString("type")
                );
                trails.add(trail);
            }

        } catch (SQLException e) {
            System.out.println("SQLException in fetchFilteredTrails: " + e.getMessage());
        }

        return trails;
    }
}
