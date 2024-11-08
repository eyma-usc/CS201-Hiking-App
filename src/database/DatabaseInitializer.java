package database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;

public class DatabaseInitializer {
 public static void initialize() {
     try (Connection conn = DriverManager.getConnection("jdbc:sqlite:hiking_trail_app.db")) {
         if (conn != null) {
             String createUsersTable = "CREATE TABLE IF NOT EXISTS Users ("
                     + "user_id INTEGER PRIMARY KEY AUTOINCREMENT,"
                     + "email TEXT UNIQUE NOT NULL,"
                     + "password TEXT NOT NULL"
                     + ");";

             Statement stmt = conn.createStatement();
             stmt.execute(createUsersTable);

             System.out.println("Database initialized successfully.");
         }
     } catch (Exception e) {
         e.printStackTrace();
     }
 }
}
