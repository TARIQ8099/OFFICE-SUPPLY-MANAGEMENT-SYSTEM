import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

// =============================
// 1. Database Connection
// =============================
public class DatabaseConnection {
    // Fields
    private static final String URL  = "jdbc:mysql://localhost:3306/office_management";
    private static final String USER = "root";
    private static final String PASS = "T@riq8099";

    // Static initializer
    static {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver"); // MySQL driver
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    // Methods
    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASS);
    }
}
