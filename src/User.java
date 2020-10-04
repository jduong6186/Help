public class User {

    private String name;
    private String phone;
    private String email;
    private String password;
    private Listing[] listings;

    public User(String name, String phone, String email, String password) {
        this.name = name;
        this.phone = phone;
        this.email = email;
        this.password = password;
    }

    protected String getName() {
        return this.name;
    }

    protected String getPhone() {
        return this.phone;
    }

    protected String getEmail() {
        return this.email;
    }

    protected Listing[] getListings() {
        return this.listings;
    }

    protected void updateName(String name) {
        this.name = name;
    }

    protected void updatePhone(String phone) {
        this.phone = phone;
    }

    protected void updateEmail(String email) {
        this.email = email;
    }

    protected void associateListing(Listing listing) {
        if (this.listings[this.listings.length-1] != null) {
            this.listings = growListings(this.listings);
        }
        for (int i=0; i<this.listings.length; i++) {
            if (this.listings[i] == null) {
                this.listings[i] = listing;
                return;
            }
        }
    }

    private Listing[] growListings(Listing[] listings) {
        Listing[] newListings = new Listing[listings.length*2];
        for (int i=0; i<listings.length; i++) {
            newListings[i] = listings[i];
        }
        return newListings;
    }

    protected boolean validateLogin(String email, String password) {
        return this.email.toLowerCase().equals(email.toLowerCase()) && PasswordManager.equals(this.password, password);
    }
}
