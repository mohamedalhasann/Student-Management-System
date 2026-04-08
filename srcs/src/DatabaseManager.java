package finalonee;
import java.sql.*;

public class DatabaseManager {
    private static DatabaseManager instance;
    private static final String DB_URL = "jdbc:mysql://localhost:3306/javafx";
    private static final String DB_USER = "root";
    private static final String DB_PASSWORD = "";
    
    private DatabaseManager() {
    }
    
    public static DatabaseManager getInstance() {
        if (instance == null) {
            instance = new DatabaseManager();
        }
        return instance;
    }
    
    public Connection getConnection() throws SQLException {
        return DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
    }
    
    public void initializeDatabase() {
        try (Connection conn = getConnection()) {
            Statement stmt = conn.createStatement();
            

            stmt.execute("""
                CREATE TABLE IF NOT EXISTS users (
                    id INT AUTO_INCREMENT PRIMARY KEY,
                    username VARCHAR(50) UNIQUE NOT NULL,
                    password VARCHAR(255) NOT NULL
                )
            """);
            

            stmt.execute("""
                CREATE TABLE IF NOT EXISTS courses (
                    id INT AUTO_INCREMENT PRIMARY KEY,
                    course_code VARCHAR(20) UNIQUE NOT NULL,
                    course_name VARCHAR(100) NOT NULL,
                    credits INT DEFAULT 3
                )
            """);
            

            stmt.execute("""
                CREATE TABLE IF NOT EXISTS user_courses (
                    id INT AUTO_INCREMENT PRIMARY KEY,
                    user_id INT,
                    course_id INT,
                    FOREIGN KEY (user_id) REFERENCES users(id),
                    FOREIGN KEY (course_id) REFERENCES courses(id)
                )
            """);
            
            insertSampleData(conn);
            
        } catch (SQLException e) {
            System.out.println("Database Error"+ "Failed to initialize database: " + e.getMessage());
        }
    }
    
    private void insertSampleData(Connection conn) throws SQLException {
        Statement stmt = conn.createStatement();
        stmt.execute("""
            INSERT IGNORE INTO courses (course_code, course_name, credits) VALUES
            ('CS101', 'Intro to Programming', 3),
            ('CS102', 'Object Oriented Java', 3),
            ('CS201', 'Databases', 3),
            ('MATH101', 'Calculus I', 4)
        """);
    }
}