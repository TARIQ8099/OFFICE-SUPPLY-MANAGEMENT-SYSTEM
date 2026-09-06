import java.sql.*;
import java.util.ArrayList;
import java.util.List;

// =============================
// 3. TeacherDAO
// =============================
public class TeacherDAO {
    // Methods
    public List<Teacher> getAllTeachers() {
        List<Teacher> list = new ArrayList<>();
        String sql = "SELECT * FROM teacher";
        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt   = conn.createStatement();
             ResultSet rs     = stmt.executeQuery(sql)) {

            while (rs.next()) {
                String teacherId   = rs.getString("teacher_id");
                String teacherName = rs.getString("teacher_name");
                String dept        = rs.getString("teacher_department");
                String hashedPass  = rs.getString("teacher_password");

                list.add(new Teacher(teacherId, teacherName, dept, hashedPass));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    public void insertTeacher(Teacher teacher) {
        // -- ADDED VALIDATION CHECKS HERE --
        // (Adjust max lengths as needed based on your actual DB constraints.)
        if (teacher.getId().length() > 20) {
            throw new IllegalArgumentException("Teacher ID length is too long (max 20).");
        }
        if (teacher.getName().length() > 50) {
            throw new IllegalArgumentException("Teacher name length is too long (max 50).");
        }
        if (teacher.getDepartment().length() > 50) {
            throw new IllegalArgumentException("Teacher department length is too long (max 50).");
        }

        String sql = "INSERT INTO teacher(teacher_id, teacher_name, teacher_department, teacher_password) "
                + "VALUES (?, ?, ?, ?)";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, teacher.getId());
            pstmt.setString(2, teacher.getName());
            pstmt.setString(3, teacher.getDepartment());

            // Hash the password before storing
            String hashedPassword = PasswordUtils.md5Hash(teacher.password);
            pstmt.setString(4, hashedPassword);

            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public boolean deleteTeacherById(String teacherId) {
        String sql = "DELETE FROM teacher WHERE teacher_id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, teacherId);
            int rowsAffected = pstmt.executeUpdate();
            return (rowsAffected > 0);
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}
