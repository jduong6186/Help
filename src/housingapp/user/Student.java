package housingapp.user;

import housingapp.system.UserType;
import housingapp.user.User;

import java.util.ArrayList;
import java.util.UUID;

public class Student extends User {

	private boolean hasPets;
	private double priceRangeLower;
	private double priceRangeUpper;
	private double maxTravelDistance;
	private int minRoommates;
	private int maxRoommates;
	private ArrayList<UUID> ratings;

    public Student(String firstName, String lastName, String phone, String email, String password, boolean hasPets,
                   double priceRangeLower, double priceRangeUpper, double maxTravelDistance, int minRoommates,
                   int maxRoommates) {
        super(firstName, lastName, phone, email, password);
        this.hasPets = hasPets;
        this.priceRangeLower = priceRangeLower;
        this.priceRangeUpper = priceRangeUpper;
        this.maxTravelDistance = maxTravelDistance;
        this.minRoommates = minRoommates;
        this.maxRoommates = maxRoommates;
        this.ratings = new ArrayList<UUID>();
    }

    public Student(UUID id, String firstName, String lastName, String phone, String email, String password, boolean hasPets,
                   double priceRangeLower, double priceRangeUpper, double maxTravelDistance, int minRoommates,
                   int maxRoommates, ArrayList<UUID> ratings, ArrayList<UUID> listings) {
        super(id, firstName, lastName, phone, email, password, listings);
        this.hasPets = hasPets;
        this.priceRangeLower = priceRangeLower;
        this.priceRangeUpper = priceRangeUpper;
        this.maxTravelDistance = maxTravelDistance;
        this.minRoommates = minRoommates;
        this.maxRoommates = maxRoommates;
        this.ratings = ratings;
    }

    public UserType getUserType() {
        return UserType.STUDENT;
    }

    public UUID getId() {
        return super.getId();
    }

    public String getFirstName() {
        return super.getFirstName();
    }

    public String getLastName() {
        return super.getLastName();
    }

    public String getPhone() {
        return super.getPhone();
    }

    public String getEmail() {
        return super.getEmail();
    }

    public String getPassword() {
        return super.getPassword();
    }

    public ArrayList<UUID> getListings() {
        return super.getListings();
    }

    public boolean hasPets() {
        return this.hasPets;
    }

    public double getPriceRangeLower() {
        return this.priceRangeLower;
    }

    public double getPriceRangeUpper() {
        return this.priceRangeUpper;
    }

    public double getMaxTravelDistance() {
        return this.maxTravelDistance;
    }

    public int getMinRoommates() {
        return this.minRoommates;
    }

    public int getMaxRoommates() {
        return this.maxRoommates;
    }

    public ArrayList<UUID> getRatings() {
        return this.ratings;
    }

    protected void updateFirstName(String firstName) {
        super.updateFirstName(firstName);
    }

    protected void updateLastName(String lastName) {
        super.updateLastName(lastName);
    }

    protected void updatePhone(String phone) {
        super.updatePhone(phone);
    }

    protected void updateEmail(String email) {
        super.updateEmail(email);
    }

    protected void associateListing(UUID listingId) {
        super.associateListing(listingId);
    }

    protected void removeListing(UUID listingId) {
        super.removeListing(listingId);
    }

    protected void updateHasPets(boolean hasPets) {
        this.hasPets = hasPets;
    }

    protected void updatePriceRangeLower(double priceRangeLower) {
        this.priceRangeLower = priceRangeLower;
    }

    protected void updatePriceRangeUpper(double priceRangeUpper) {
        this.priceRangeUpper = priceRangeUpper;
    }

    protected void updateMaxTravelDistance(double maxTravelDistance) {
        this.maxTravelDistance = maxTravelDistance;
    }

    protected void updateMinRoommates(int minRoommates) {
        this.minRoommates = minRoommates;
    }

    protected void updateMaxRoommates(int maxRoommates) {
        this.maxRoommates = maxRoommates;
    }

    protected void associateRating(UUID ratingId) {
        this.ratings.add(ratingId);
    }

    protected void removeRating(UUID ratingId) {
        this.ratings.remove(ratingId);
    }
}
