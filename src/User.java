// =============================
// 2. Teacher / Administrator
// =============================
public abstract class User {
    // Fields
    protected String id;
    protected String name;
    protected String password; // This will hold the hashed password internally

    // Constructor
    public User(String id, String name, String hashedPassword) {
        this.id       = id;
        this.name     = name;
        this.password = hashedPassword; // can be hashed or not, depending on usage
    }

    // Methods
    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    /**
     * Authenticates by comparing hashed input vs stored hashed password.
     */
    public boolean authenticate(String inputPlainPassword) {
        String hashedInput = PasswordUtils.md5Hash(inputPlainPassword);
        return (hashedInput != null) && hashedInput.equalsIgnoreCase(this.password);
    }
}
