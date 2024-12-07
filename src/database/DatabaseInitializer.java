
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class  Databaseinitializer {

    private static final String URL = "jdbc:mysql://localhost/YourDatabase?user=root&password=YourPassword";
    private static final String USERNAME = "root";
    private static final String PASSWORD = "YourPassword";

    public static void main(String[] args) {
        addUsers();
    }

    // Method to establish a database connection
    private static Connection connect() throws SQLException {
        return DriverManager.getConnection(URL, USERNAME, PASSWORD);
    }

    // Method to add a few users into the Users table
    private static void addUsers() {
        String query = "INSERT INTO Users (user_id, email, password) VALUES (?, ?, ?)";

        try (Connection conn = connect();
             PreparedStatement ps = conn.prepareStatement(query)) {

            // Adding first user
            ps.setInt(1, 1);
            ps.setString(2, "john.doe@example.com");
            ps.setString(3, "password123");
            ps.executeUpdate();

            // Adding second user
            ps.setInt(1, 2);
            ps.setString(2, "jane.smith@example.com");
            ps.setString(3, "password456");
            ps.executeUpdate();

            // Adding third user
            ps.setInt(1, 3);
            ps.setString(2, "alice.jones@example.com");
            ps.setString(3, "alice789");
            ps.executeUpdate();

            System.out.println("Users added successfully.");

        } catch (SQLException e) {
            System.out.println("SQLException in addUsers: " + e.getMessage());
        }
    }
}
