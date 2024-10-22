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
import java.util.ArrayList;
import java.util.List;

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

    // Insert a new user into the database
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

    // Retrieve all users from the database
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

    // Retrieve a specific user by ID
    public void retrieveUserById(int id) {
        try (PreparedStatement pstmt = connection.prepareStatement("SELECT * FROM Users WHERE id = ?")) {
            pstmt.setInt(1, id);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                System.out.println("ID: " + rs.getInt("id") + ", Name: " + rs.getString("name") + ", Email: " + rs.getString("email"));
            } else {
                System.out.println("User with ID " + id + " not found.");
            }
        } catch (SQLException e) {
            System.err.println("Error retrieving user by ID: " + e.getMessage());
        }
    }

    // Retrieve users by email
    public void retrieveUsersByEmail(String email) {
        try (PreparedStatement pstmt = connection.prepareStatement("SELECT * FROM Users WHERE email = ?")) {
            pstmt.setString(1, email);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                System.out.println("ID: " + rs.getInt("id") + ", Name: " + rs.getString("name") + ", Email: " + rs.getString("email"));
            }
        } catch (SQLException e) {
            System.err.println("Error retrieving users by email: " + e.getMessage());
        }
    }

    // Retrieve users by name
    public void retrieveUsersByName(String name) {
        try (PreparedStatement pstmt = connection.prepareStatement("SELECT * FROM Users WHERE name = ?")) {
            pstmt.setString(1, name);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                System.out.println("ID: " + rs.getInt("id") + ", Name: " + rs.getString("name") + ", Email: " + rs.getString("email"));
            }
        } catch (SQLException e) {
            System.err.println("Error retrieving users by name: " + e.getMessage());
        }
    }

    // Update a user's email by ID
    public void updateUserEmail(int id, String newEmail) {
        try (PreparedStatement pstmt = connection.prepareStatement("UPDATE Users SET email = ? WHERE id = ?")) {
            pstmt.setString(1, newEmail);
            pstmt.setInt(2, id);
            int rowsAffected = pstmt.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("User's email updated successfully.");
            } else {
                System.out.println("No user found with ID " + id);
            }
        } catch (SQLException e) {
            System.err.println("Error updating user email: " + e.getMessage());
        }
    }

    // Update a user's name by ID
    public void updateUserName(int id, String newName) {
        try (PreparedStatement pstmt = connection.prepareStatement("UPDATE Users SET name = ? WHERE id = ?")) {
            pstmt.setString(1, newName);
            pstmt.setInt(2, id);
            int rowsAffected = pstmt.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("User's name updated successfully.");
            } else {
                System.out.println("No user found with ID " + id);
            }
        } catch (SQLException e) {
            System.err.println("Error updating user name: " + e.getMessage());
        }
    }

    // Update a user's details (name and email) by ID
    public void updateUserDetails(int id, String newName, String newEmail) {
        try (PreparedStatement pstmt = connection.prepareStatement("UPDATE Users SET name = ?, email = ? WHERE id = ?")) {
            pstmt.setString(1, newName);
            pstmt.setString(2, newEmail);
            pstmt.setInt(3, id);
            int rowsAffected = pstmt.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("User's details updated successfully.");
            } else {
                System.out.println("No user found with ID " + id);
            }
        } catch (SQLException e) {
            System.err.println("Error updating user details: " + e.getMessage());
        }
    }

    // Delete a user by ID
    public void deleteUser(int id) {
        try (PreparedStatement pstmt = connection.prepareStatement("DELETE FROM Users WHERE id = ?")) {
            pstmt.setInt(1, id);
            int rowsAffected = pstmt.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("User deleted successfully.");
            } else {
                System.out.println("No user found with ID " + id);
            }
        } catch (SQLException e) {
            System.err.println("Error deleting user: " + e.getMessage());
        }
    }

    // Close the database connection
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
