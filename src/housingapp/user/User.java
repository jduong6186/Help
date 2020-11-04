package housingapp.user;

import at.favre.lib.crypto.bcrypt.BCrypt;
import housingapp.UserType;
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
        if (listings != null) {
            this.listings = listings;
        } else {
            this.listings = new ArrayList<UUID>();
        }
    }

    /**
    gets the UUID number
    */
    public UUID getId() {
        return this.id;
    }

    /**
    gets the type of the user
    */
    public UserType getType() {
        return this.type;
    }

    /**
    gets the first name of the user
    */
    public String getFirstName() {
        return this.firstName;
    }

    /**
    gets the last name of the user
    */
    public String getLastName() {
        return this.lastName;
    }

    /**
    gets the phone number of the user
    */
    public String getPhone() {
        return this.phone;
    }

    /**
    gets the email of the user
    */
    public String getEmail() {
        return this.email;
    }

    /**
    gets the password of the user
    */
    public String getPassword() {
        return this.password;
    }

    /**
    gets the ArrayList of listings
    */
    public ArrayList<UUID> getListings() {
        return this.listings;
    }

    /**
    updates the first name of the user
    */
    public void updateFirstName(String firstName) {
        this.firstName = firstName;
    }

    /**
    updates the last name of the user
    */
    public void updateLastName(String lastName) {
        this.lastName = lastName;
    }

    /**
    updates the phone number
    */
    public void updatePhone(String phone) {
        this.phone = phone;
    }

    /**
    updates the email
    */
    public void updateEmail(String email) {
        this.email = email;
    }

    /**
    makes the listing
    */
    public void associateListing(UUID listingId) {
        this.listings.add(listingId);
    }

    /**
    removes the specified listing using it's UUID
    */
    public void removeListing(UUID listingId) {
        this.listings.remove(listingId);
    }

    /**
     * returns abstract user details as string
     */
    @Override
    public String toString() {
        return String.format("First name: %s\nLast name: %s\nPhone: %s\nEmail: %s\n", firstName, lastName, phone, email);
    }
}
