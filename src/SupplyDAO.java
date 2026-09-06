import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class SupplyDAO {
    // Methods
    public List<Supply> getAllSupplies() {
        List<Supply> list = new ArrayList<>();
        String sql = "SELECT * FROM supply";
        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs   = stmt.executeQuery(sql)) {

            while (rs.next()) {
                int sid    = rs.getInt("supply_id");
                String name= rs.getString("supply_name");
                int qty    = rs.getInt("quantity");
                String st  = rs.getString("status");
                list.add(new Supply(sid, name, qty, st));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    public void insertSupply(Supply supply) {
        String sql = "INSERT INTO supply(supply_name, quantity, status) VALUES (?, ?, ?)";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            pstmt.setString(1, supply.getSupplyName());
            pstmt.setInt(2,    supply.getQuantity());
            pstmt.setString(3, supply.getStatus());
            pstmt.executeUpdate();

            try (ResultSet keys = pstmt.getGeneratedKeys()) {
                if (keys.next()) {
                    supply.setSupplyId(keys.getInt(1));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public boolean deleteSupplyById(int supplyId) {
        String sql = "DELETE FROM supply WHERE supply_id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, supplyId);
            int rows = pstmt.executeUpdate();
            return (rows > 0);
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public void updateSupply(Supply supply) {
        String sql = "UPDATE supply SET supply_name=?, quantity=?, status=? WHERE supply_id=?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, supply.getSupplyName());
            pstmt.setInt(2,    supply.getQuantity());
            pstmt.setString(3, supply.getStatus());
            pstmt.setInt(4,    supply.getSupplyId());
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
