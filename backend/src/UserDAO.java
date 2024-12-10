import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserDAO {

    public void registerUser(User user) throws SQLException {
        String sql = "INSERT INTO users (first_name, last_name, email, password) VALUES (?, ?, ?, ?)";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, user.getFirstName());
            stmt.setString(2, user.getLastName());
            stmt.setString(3, user.getEmail());
            stmt.setString(4, user.getPassword()); // Store plain-text password
            stmt.executeUpdate();
            System.out.println("User registered successfully: " + user.getEmail());
        } catch (SQLException e) {
            System.err.println("Error registering user: " + e.getMessage());
            throw e;
        }
    }

    public User getUserByEmail(String email) throws SQLException {
        String sql = "SELECT * FROM users WHERE email = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, email);

            try (ResultSet resultSet = stmt.executeQuery()) {
                if (resultSet.next()) {
                    return new User(
                        resultSet.getString("first_name"),
                        resultSet.getString("last_name"),
                        resultSet.getString("email"),
                        resultSet.getString("password") 
                    );
                }
            }
        }
        return null;
    }
}
