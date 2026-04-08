package finalonee;
import java.sql.*;

public class UserDAO {
    private DatabaseManager dbManager;
    
    public UserDAO() {
        this.dbManager = DatabaseManager.getInstance();
    }
    
    public User authenticate(String username, String password) {
        if (username.trim().isEmpty() || password.trim().isEmpty()) {
            return null;
        }
        
        String sql = "SELECT id, username FROM users WHERE username = ? AND password = ?";
        try (Connection conn = dbManager.getConnection(); 
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setString(1, username);
            stmt.setString(2, password);
            ResultSet rs = stmt.executeQuery();
            
            if (rs.next()) {
                return new User(rs.getInt("id"), rs.getString("username"));
            }
        } catch (SQLException e) {
            System.out.println("Login Error" + "Database connection failed: " + e.getMessage());
        }
        return null;
    }
    
    public boolean registerUser(String username, String password) {
        if (username.trim().isEmpty() || password.trim().isEmpty()) {
            System.out.println("Invalid Input");
            return false;
        }
        
        String sql = "INSERT INTO users (username, password) VALUES (?, ?)";
        try (Connection conn = dbManager.getConnection(); 
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setString(1, username);
            stmt.setString(2, password);
            stmt.executeUpdate();
            
            System.out.println("Your account has been created successfully!");
            return true;
            
        } catch (SQLException e) {
            if (e.getMessage().contains("Duplicate entry")) {
                System.out.println("Username already exists.");
            } else {
                System.out.println("Registration Failed"+
                    "Failed to create account: " + e.getMessage());
            }
            return false;
        }
    }
}