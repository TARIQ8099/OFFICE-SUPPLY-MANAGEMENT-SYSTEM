// =============================
// 5. Requisition + RequisitionDAO
// =============================
public class Requisition {
    // Fields
    private int    requisitionId;
    private String teacherId;
    private int    supplyId;
    private int    reqQuantity;
    private String status;

    // Constructors
    public Requisition(int requisitionId, String teacherId, int supplyId, int reqQuantity, String status) {
        this.requisitionId = requisitionId;
        this.teacherId     = teacherId;
        this.supplyId      = supplyId;
        this.reqQuantity   = reqQuantity;
        this.status        = status;
    }

    public Requisition(String teacherId, int supplyId, int reqQuantity, String status) {
        this(0, teacherId, supplyId, reqQuantity, status);
    }

    // Getters & setters
    public int getRequisitionId() {
        return requisitionId;
    }

    public void setRequisitionId(int id) {
        this.requisitionId = id;
    }

    public String getTeacherId() {
        return teacherId;
    }

    public int getSupplyId() {
        return supplyId;
    }

    public int getReqQuantity() {
        return reqQuantity;
    }

    public void setReqQuantity(int q) {
        reqQuantity = q;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String s) {
        this.status = s;
    }

    // Method
    @Override
    public String toString() {
        return String.format("Requisition{id=%d, teacher_id='%s', supply_id=%d, quantity=%d, status='%s'}",
                requisitionId, teacherId, supplyId, reqQuantity, status);
    }
}
