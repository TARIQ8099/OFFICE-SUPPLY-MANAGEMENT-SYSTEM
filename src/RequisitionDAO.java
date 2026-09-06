import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class RequisitionDAO {
    // Methods
    public List<Requisition> getAllRequisitions() {
        List<Requisition> list = new ArrayList<>();
        String sql = "SELECT * FROM requisition";
        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs   = stmt.executeQuery(sql)) {

            while (rs.next()) {
                int rid      = rs.getInt("requisition_id");
                String tId   = rs.getString("teacher_id");
                int sId      = rs.getInt("supply_id");
                int quantity = rs.getInt("req_quantity");
                String st    = rs.getString("status");
                list.add(new Requisition(rid, tId, sId, quantity, st));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    public void insertRequisition(Requisition r) {
        String sql = "INSERT INTO requisition(teacher_id, supply_id, req_quantity, status) VALUES (?, ?, ?, ?)";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            pstmt.setString(1, r.getTeacherId());
            pstmt.setInt(2,    r.getSupplyId());
            pstmt.setInt(3,    r.getReqQuantity());
            pstmt.setString(4, r.getStatus());
            pstmt.executeUpdate();

            try (ResultSet keys = pstmt.getGeneratedKeys()) {
                if (keys.next()) {
                    r.setRequisitionId(keys.getInt(1));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void updateRequisition(Requisition r) {
        String sql = "UPDATE requisition SET teacher_id=?, supply_id=?, req_quantity=?, status=? WHERE requisition_id=?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, r.getTeacherId());
            pstmt.setInt(2,    r.getSupplyId());
            pstmt.setInt(3,    r.getReqQuantity());
            pstmt.setString(4, r.getStatus());
            pstmt.setInt(5,    r.getRequisitionId());
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public boolean deleteRequisitionById(int requisitionId) {
        String sql = "DELETE FROM requisition WHERE requisition_id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, requisitionId);
            int rowsAffected = pstmt.executeUpdate();
            return (rowsAffected > 0);
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}
