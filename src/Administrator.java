public class Administrator extends User {
    // Constructor
    // Hard-coded admin is stored with hashed password
    public Administrator(String id, String name, String plainPassword) {
        super(id, name, PasswordUtils.md5Hash(plainPassword));
    }
}
