package finalonee;


import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import java.sql.*;

public class CourseDAO {
    private DatabaseManager dbManager;
    
    public CourseDAO() {
        this.dbManager = DatabaseManager.getInstance();
    }
    
    public ObservableList<Course> getEnrolledCourses(int userId) {
        ObservableList<Course> list = FXCollections.observableArrayList();
        String sql = """
            SELECT c.course_code, c.course_name, c.credits FROM courses c
            JOIN user_courses uc ON c.id = uc.course_id
            WHERE uc.user_id = ?
        """;
        
        try (Connection conn = dbManager.getConnection(); 
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setInt(1, userId);
            ResultSet rs = stmt.executeQuery();
            
            while (rs.next()) {
                list.add(new Course(
                    rs.getString("course_code"), 
                    rs.getString("course_name"), 
                    rs.getInt("credits")
                ));
            }
        } catch (SQLException e) {
            System.out.println("Database Error"+ 
                "Failed to load enrolled courses: " + e.getMessage());
        }
        return list;
    }
    
    public ObservableList<Course> getAvailableCourses(int userId) {
        ObservableList<Course> list = FXCollections.observableArrayList();
        String sql = """
            SELECT c.course_code, c.course_name, c.credits FROM courses c
            WHERE c.id NOT IN (SELECT course_id FROM user_courses WHERE user_id = ?)
        """;
        
        try (Connection conn = dbManager.getConnection(); 
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setInt(1, userId);
            ResultSet rs = stmt.executeQuery();
            
            while (rs.next()) {
                list.add(new Course(
                    rs.getString("course_code"), 
                    rs.getString("course_name"), 
                    rs.getInt("credits")
                ));
            }
        } catch (SQLException e) {
            System.out.println("Database Error"+
                "Failed to load available courses: " + e.getMessage());
        }
        return list;
    }
    
    public boolean enrollInCourse(int userId, String courseCode) {
        String findSql = "SELECT id FROM courses WHERE course_code = ?";
        String insertSql = "INSERT INTO user_courses (user_id, course_id) VALUES (?, ?)";
        
        try (Connection conn = dbManager.getConnection();
             PreparedStatement findStmt = conn.prepareStatement(findSql);
             PreparedStatement insertStmt = conn.prepareStatement(insertSql)) {
            
            findStmt.setString(1, courseCode);
            ResultSet rs = findStmt.executeQuery();
            
            if (rs.next()) {
                int courseId = rs.getInt("id");
                insertStmt.setInt(1, userId);
                insertStmt.setInt(2, courseId);
                insertStmt.executeUpdate();
                
                System.out.println("Enrollment Successful"+ 
                    "You have successfully enrolled in " + courseCode + "!");
                return true;
            }
        } catch (SQLException e) {
            System.out.println("Enrollment Failed"+ 
                "Failed to enroll in course: " + e.getMessage());
        }
        return false;
    }
}