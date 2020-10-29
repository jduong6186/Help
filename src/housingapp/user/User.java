package housingapp.user;

import at.favre.lib.crypto.bcrypt.BCrypt;
import housingapp.system.UserType;
import java.util.ArrayList;
import java.util.UUID;

public abstract class User {

    private final UUID id;
    private final UserType type;
    private String firstName;
    private String lastName;
    private String phone;
    private String email;
    private String password;
    private ArrayList<UUID> listings;

    /**
     * constructor with base properties
     * @param type user type (student/property manager)
     * @param firstName user's first name
     * @param lastName user's last name
     * @param phone user's phone number
     * @param email user's email address
     * @param password user's raw password (hashed in constructor)
     */
    public User(UserType type, String firstName, String lastName, String phone, String email, String password) {
        this.id = UUID.randomUUID();
        this.type = type;
        this.firstName = firstName;
        this.lastName = lastName;
        this.phone = phone;
        this.email = email;
        this.password = BCrypt.withDefaults().hashToString(6, password.toCharArray());
        this.listings = new ArrayList<UUID>();
    }

    /**
     * constructor with id, listings, and base properties (used on startup when pulling users from JSON storage)
     * @param id user's UUID
     * @param type user type (student/property manager)
     * @param firstName user's first name
     * @param lastName user's last name
     * @param phone user's phone number
     * @param email user's email address
     * @param password bcrypt hash of user's password
     * @param listings ArrayList of listings associated with user
     */
    public User(UUID id, UserType type, String firstName, String lastName, String phone, String email, String password,
                ArrayList<UUID> listings) {
        this.id = id;
        this.type = type;
        this.firstName = firstName;
        this.lastName = lastName;
        this.phone = phone;
        this.email = email;
        this.password = password;
        this.listings = listings;
    }

    public UUID getId() {
        return this.id;
    }

    public UserType getType() {
        return this.type;
    }

    public String getFirstName() {
        return this.firstName;
    }

    public String getLastName() {
        return this.lastName;
    }

    public String getPhone() {
        return this.phone;
    }

    public String getEmail() {
        return this.email;
    }

    public String getPassword() {
        return this.password;
    }

    public ArrayList<UUID> getListings() {
        return this.listings;
    }

    protected void updateFirstName(String firstName) {
        this.firstName = firstName;
    }

    protected void updateLastName(String lastName) {
        this.lastName = lastName;
    }

    protected void updatePhone(String phone) {
        this.phone = phone;
    }

    protected void updateEmail(String email) {
        this.email = email;
    }

    protected void associateListing(UUID listingId) {
        this.listings.add(listingId);
    }

    protected void removeListing(UUID listingId) {
        this.listings.remove(listingId);
    }
}
