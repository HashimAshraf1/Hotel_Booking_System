/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package assignment.pkg1;

/**
 *
 * @author hash
 */
import java.sql.*;

public class DatabaseManager {
    private Connection connection;

    public DatabaseManager() {
        try {
            // Connect to the Derby database
            Class.forName("org.apache.derby.jdbc.EmbeddedDriver");
            connection = DriverManager.getConnection("jdbc:derby:./data/myDB;create=true");
            System.out.println("Database connected successfully.");
        } catch (Exception e) {
            System.err.println("Database connection error: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public void createTable() {
        try (Statement stmt = connection.createStatement()) {
            String sql = "CREATE TABLE IF NOT EXISTS Users (id INT PRIMARY KEY, name VARCHAR(255), email VARCHAR(255))";
            stmt.executeUpdate(sql);
            System.out.println("Table created successfully.");
        } catch (SQLException e) {
            if (e.getSQLState().equals("X0Y32")) {
                System.out.println("Table already exists, skipping creation.");
            } else {
                System.err.println("Error creating table: " + e.getMessage());
                e.printStackTrace();
            }
        }
    }

    public void insertUser(int id, String name, String email) {
        try (PreparedStatement pstmt = connection.prepareStatement("INSERT INTO Users VALUES (?, ?, ?)")) {
            pstmt.setInt(1, id);
            pstmt.setString(2, name);
            pstmt.setString(3, email);
            pstmt.executeUpdate();
            System.out.println("User inserted successfully.");
        } catch (SQLException e) {
            System.err.println("Error inserting user: " + e.getMessage());
        }
    }

    public void retrieveUsers() {
        try (Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery("SELECT * FROM Users")) {
            while (rs.next()) {
                System.out.println("ID: " + rs.getInt("id") + ", Name: " + rs.getString("name") + ", Email: " + rs.getString("email"));
            }
        } catch (SQLException e) {
            System.err.println("Error retrieving users: " + e.getMessage());
        }
    }

    public void closeConnection() {
        try {
            if (connection != null) {
                connection.close();
                System.out.println("Database connection closed.");
            }
        } catch (SQLException e) {
            System.err.println("Error closing connection: " + e.getMessage());
        }
    }
}

