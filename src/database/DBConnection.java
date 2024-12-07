package database;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {
    // Database connection URL, username, and password
    private static final String URL = "jdbc:mysql://localhost/HikingApp?user=root&password=Ethanchiu0520"; // Replace "WeatherConditions" with your database name
    private static final String USERNAME = "root"; // Replace with your MySQL username
    private static final String PASSWORD = "Ethanchiu0520"; // Replace with your MySQL password

    // Connection object
    private static Connection connection = null;

    // Method to establish a connection
    public static Connection getConnection() {
        if (connection == null) {
            try {
                // Load the MySQL JDBC driver
                Class.forName("com.mysql.cj.jdbc.Driver");
                
                // Establish the connection
                connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
                System.out.println("Database connection established successfully.");
            } catch (ClassNotFoundException e) {
                System.err.println("JDBC Driver not found. Make sure to add the MySQL connector JAR.");
                e.printStackTrace();
            } catch (SQLException e) {
                System.err.println("Failed to establish database connection.");
                e.printStackTrace();
            }
        }
        return connection;
    }

    // Method to close the connection
    public static void closeConnection() {
        if (connection != null) {
            try {
                connection.close();
                System.out.println("Database connection closed.");
            } catch (SQLException e) {
                System.err.println("Error while closing the database connection.");
                e.printStackTrace();
            }
        }
    }
}
