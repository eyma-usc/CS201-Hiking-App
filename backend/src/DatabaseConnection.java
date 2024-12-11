import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {
    private static final String URL = "jdbc:mysql://localhost:3306/TrailFinderDB";
    private static final String USER = "root";
    private static final String PASSWORD = "Pax00!!!";

    public static Connection getConnection() throws SQLException {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver"); // Explicitly load the MySQL driver
        } catch (ClassNotFoundException e) {
            throw new SQLException("MySQL JDBC Driver not found", e);
        }

        return DriverManager.getConnection(URL, USER, PASSWORD);
    }
}
