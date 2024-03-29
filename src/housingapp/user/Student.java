package housingapp.user;

import housingapp.UserType;

import java.util.ArrayList;
import java.util.UUID;

/**
 * describes student user type
 */

public class Student extends User {

	private boolean hasPets;
	private double priceRangeLower;
	private double priceRangeUpper;
	private double maxTravelDistance;
	private int minRoommates;
	private int maxRoommates;
	private ArrayList<UUID> ratings;
	private ArrayList<UUID> listingFavorites;

    /**
     * constructor used for instantiating student at runtime
     * @param firstName student's first name
     * @param lastName student's last name
     * @param phone phone number
     * @param email email address
     * @param password password (hashed in constructor)
     * @param hasPets indicates whether student has pets
     * @param priceRangeLower lower rent price range
     * @param priceRangeUpper upper rent price range
     * @param maxTravelDistance max distance willing to travel to campus
     * @param minRoommates min acceptable number of roommates
     * @param maxRoommates max acceptable number of roommates
     */
    public Student(String firstName, String lastName, String phone, String email, String password, boolean hasPets,
                   double priceRangeLower, double priceRangeUpper, double maxTravelDistance, int minRoommates,
                   int maxRoommates) {
        super(UserType.STUDENT, firstName, lastName, phone, email, password);
        this.hasPets = hasPets;
        this.priceRangeLower = priceRangeLower;
        this.priceRangeUpper = priceRangeUpper;
        this.maxTravelDistance = maxTravelDistance;
        this.minRoommates = minRoommates;
        this.maxRoommates = maxRoommates;
        this.ratings = new ArrayList<UUID>();
        this.listingFavorites = new ArrayList<UUID>();
    }

    /**
     * constructor used for instantiating pre-constructed student from JSON file
     * @param id student's UUID
     * @param firstName student's first name
     * @param lastName student's last name
     * @param phone phone number
     * @param email email address
     * @param password bcrypt hash of password
     * @param hasPets indicates whether student has pets
     * @param priceRangeLower lower rent price range
     * @param priceRangeUpper upper rent price range
     * @param maxTravelDistance max distance willing to travel to campus
     * @param minRoommates min acceptable number of roommates
     * @param maxRoommates max acceptable number of roommates
     * @param ratings array list of studentRatings on student
     * @param listingFavorites array list of listings favorited by student
     * @param listings array list of listing created by student
     */
    public Student(UUID id, String firstName, String lastName, String phone, String email, String password, boolean hasPets,
                   double priceRangeLower, double priceRangeUpper, double maxTravelDistance, int minRoommates,
                   int maxRoommates, ArrayList<UUID> ratings, ArrayList<UUID> listingFavorites, ArrayList<UUID> listings) {
        super(id,UserType.STUDENT, firstName, lastName, phone, email, password, listings);
        this.hasPets = hasPets;
        this.priceRangeLower = priceRangeLower;
        this.priceRangeUpper = priceRangeUpper;
        this.maxTravelDistance = maxTravelDistance;
        this.minRoommates = minRoommates;
        this.maxRoommates = maxRoommates;
        if (ratings != null) {
            this.ratings = ratings;
        } else {
            this.ratings = new ArrayList<UUID>();
        }
        if (listingFavorites != null) {
            this.listingFavorites = listingFavorites;
        } else {
            this.listingFavorites = new ArrayList<UUID>();
        }
    }

    /**
     * id accessor
     */
    public UUID getId() {
        return super.getId();
    }

    /**
    returns UUID
    */
    public UserType getType() {
        return super.getType();
    }

    /**
    returns type
    */
    public String getFirstName() {
        return super.getFirstName();
    }

    /**
    returns first name
    */
    public String getLastName() {
        return super.getLastName();
    }

    /**
    returns last name
    */
    public String getPhone() {
        return super.getPhone();
    }

    /**
    returns phone number
    */
    public String getEmail() {
        return super.getEmail();
    }

    /**
    returns email
    */
    public String getPassword() {
        return super.getPassword();
    }

    /**
    returns password
    */
    public ArrayList<UUID> getListings() {
        return super.getListings();
    }

    /**
    returns listings
    */
    public boolean hasPets() {
        return this.hasPets;
    }

    /**
    returns whether or not the user wants pets allowed
    */
    public double getPriceRangeLower() {
        return this.priceRangeLower;
    }

    /**
    returns the lower price range
    */
    public double getPriceRangeUpper() {
        return this.priceRangeUpper;
    }

    /**
    returns the upper price range
    */
    public double getMaxTravelDistance() {
        return this.maxTravelDistance;
    }

    /**
    returns the max distance from campus
    */
    public int getMinRoommates() {
        return this.minRoommates;
    }

    /**
    returns the max # of roommates
    */
    public int getMaxRoommates() {
        return this.maxRoommates;
    }

    /**
    returns the list of ratings
    */
    public ArrayList<UUID> getRatings() {
        return this.ratings;
    }

    /**
    returns the list of favorites
    */
    public ArrayList<UUID> getListingFavorites() {
        return this.listingFavorites;
    }

    /**
    updates the first name of the user
    */
    public void updateFirstName(String firstName) {
        super.updateFirstName(firstName);
    }

    /**
    updates the last name of the user
    */
    public void updateLastName(String lastName) {
        super.updateLastName(lastName);
    }

    /**
    updates the phone number of the user
    */
    public void updatePhone(String phone) {
        super.updatePhone(phone);
    }

    /**
    updates the email of the user
    */
    public void updateEmail(String email) {
        super.updateEmail(email);
    }

    /**
    update to whether or not they would like pets allowed
    */
    public void updateHasPets(boolean hasPets) {
        this.hasPets = hasPets;
    }

    /**
    updates the lower range of the price range
    */
    public void updatePriceRangeLower(double priceRangeLower) {
        this.priceRangeLower = priceRangeLower;
    }

    /**
    updates the upper range of the price range
    */
    public void updatePriceRangeUpper(double priceRangeUpper) {
        this.priceRangeUpper = priceRangeUpper;
    }

    /**
    updates the max amount of desired distance from campus
    */
    public void updateMaxTravelDistance(double maxTravelDistance) {
        this.maxTravelDistance = maxTravelDistance;
    }

    /**
    updates the min amount of desired roommates
    */
    public void updateMinRoommates(int minRoommates) {
        this.minRoommates = minRoommates;
    }

    /**
    updates the max amount of desired roommates
    */
    public void updateMaxRoommates(int maxRoommates) {
        this.maxRoommates = maxRoommates;
    }

    /**
    adds a rating
    */
    public void associateRating(UUID ratingId) {
        this.ratings.add(ratingId);
    }

    /**
    delete the rating
    */
    public void removeRating(UUID ratingId) {
        this.ratings.remove(ratingId);
    }

    /**
    adds listing to the favorites
    */
    public void associateListingFavorite(UUID listingId) {
        this.listingFavorites.add(listingId);
    }

    /**
    remove the listing from the favorites
    */
    public void removeListingFavorite(UUID listingId) {
        this.listingFavorites.remove(listingId);
    }

    /**
    adds the listing
    */
    public void associateListing(UUID listingId) {
        super.associateListing(listingId);
    }

    /**
    removes the listing via UUID #
    */
    public void removeListing(UUID listingId) {
        super.removeListing(listingId);
    }

    /**
     * returns student details as string
     */
    @Override
    public String toString() {
        String details = super.toString();
        details += "Has pets: " + hasPets + "\n";
        details += "Lower price range: " + priceRangeLower + "\n";
        details += "Upper price range: " + priceRangeUpper + "\n";
        details += "Min roommates: " + minRoommates + "\n";
        details += "Max roommates: " + maxRoommates;
        return details;
    }
}
