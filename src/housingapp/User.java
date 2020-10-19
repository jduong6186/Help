package housingapp;

import at.favre.lib.crypto.bcrypt.BCrypt;

import java.util.ArrayList;
import java.util.UUID;

public class User {

    private final UUID id;
    private String firstName;
    private String lastName;
    private String phone;
    private String email;
    private String password;
    private ArrayList<UUID> listings;
    private ResourceManager rm;

    /**
     * constructor with base properties
     * @param firstName user's first name
     * @param lastName user's last name
     * @param phone user's phone number
     * @param email user's email address
     * @param password user's raw password (hashed in constructor)
     */
    public User(String firstName, String lastName, String phone, String email, String password) {
        this.id = UUID.randomUUID();
        this.firstName = firstName;
        this.lastName = lastName;
        this.phone = phone;
        this.email = email;
        this.password = BCrypt.withDefaults().hashToString(6, password.toCharArray());
        this.rm = ResourceManager.getInstance();
    }

    /**
     * constructor with id, listings, and base properties (used on startup when pulling users from JSON storage)
     * @param id user's UUID
     * @param firstName user's first name
     * @param lastName user's last name
     * @param phone user's phone number
     * @param email user's email address
     * @param password bcrypt hash of user's password
     * @param listings ArrayList of listings associated with user
     */
    public User(UUID id, String firstName, String lastName, String phone, String email, String password, ArrayList<UUID> listings) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.phone = phone;
        this.email = email;
        this.password = password;
        this.listings = listings;
        this.rm = ResourceManager.getInstance();
    }

    public UUID getId() {
        return this.id;
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

    protected boolean validateLogin(String email, String password) {
        return this.email.toLowerCase().equals(email.toLowerCase()) && PasswordManager.equals(this.password, password);
    }
}
