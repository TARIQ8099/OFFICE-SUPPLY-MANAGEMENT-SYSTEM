public class Teacher extends User {
    // Field
    private String department;

    // Constructor
    public Teacher(String id, String name, String department, String hashedPassword) {
        super(id, name, hashedPassword);
        this.department = department;
    }

    // Method
    public String getDepartment() {
        return department;
    }
}
