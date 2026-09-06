// =============================
// 4. Supply + SupplyDAO
// =============================
public class Supply {
    // Fields
    private int    supplyId;
    private String supplyName;
    private int    quantity;
    private String status;

    // Constructors
    public Supply(int supplyId, String supplyName, int quantity, String status) {
        this.supplyId   = supplyId;
        this.supplyName = supplyName;
        this.quantity   = quantity;
        this.status     = status;
    }

    public Supply(String supplyName, int quantity, String status) {
        this(0, supplyName, quantity, status);
    }

    // Getters & setters
    public int getSupplyId() {
        return supplyId;
    }

    public void setSupplyId(int supplyId) {
        this.supplyId = supplyId;
    }

    public String getSupplyName() {
        return supplyName;
    }

    public void setSupplyName(String supplyName) {
        this.supplyName = supplyName;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    // Method
    @Override
    public String toString() {
        return String.format("Supply{id=%d, name='%s', quantity=%d, status='%s'}",
                supplyId, supplyName, quantity, status);
    }
}
