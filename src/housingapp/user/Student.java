package housingapp.user;

import housingapp.UserType;

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
	private ArrayList<UUID> listingFavorites;

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

    public Student(UUID id, String firstName, String lastName, String phone, String email, String password, boolean hasPets,
                   double priceRangeLower, double priceRangeUpper, double maxTravelDistance, int minRoommates,
                   int maxRoommates, ArrayList<UUID> ratings, ArrayList<UUID> listingFavorites, ArrayList<UUID> listings) {
        super(id,UserType.PROPERTY_MANAGER, firstName, lastName, phone, email, password, listings);
        this.hasPets = hasPets;
        this.priceRangeLower = priceRangeLower;
        this.priceRangeUpper = priceRangeUpper;
        this.maxTravelDistance = maxTravelDistance;
        this.minRoommates = minRoommates;
        this.maxRoommates = maxRoommates;
        this.ratings = ratings;
        this.listingFavorites = listingFavorites;
    }

    public UUID getId() {
        return super.getId();
    }

    public UserType getType() {
        return super.getType();
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

    public ArrayList<UUID> getListingFavorites() {
        return this.listingFavorites;
    }

    public void updateFirstName(String firstName) {
        super.updateFirstName(firstName);
    }

    public void updateLastName(String lastName) {
        super.updateLastName(lastName);
    }

    public void updatePhone(String phone) {
        super.updatePhone(phone);
    }

    public void updateEmail(String email) {
        super.updateEmail(email);
    }

    public void updateHasPets(boolean hasPets) {
        this.hasPets = hasPets;
    }

    public void updatePriceRangeLower(double priceRangeLower) {
        this.priceRangeLower = priceRangeLower;
    }

    public void updatePriceRangeUpper(double priceRangeUpper) {
        this.priceRangeUpper = priceRangeUpper;
    }

    public void updateMaxTravelDistance(double maxTravelDistance) {
        this.maxTravelDistance = maxTravelDistance;
    }

    public void updateMinRoommates(int minRoommates) {
        this.minRoommates = minRoommates;
    }

    public void updateMaxRoommates(int maxRoommates) {
        this.maxRoommates = maxRoommates;
    }

    public void associateRating(UUID ratingId) {
        this.ratings.add(ratingId);
    }

    public void removeRating(UUID ratingId) {
        this.ratings.remove(ratingId);
    }
    
    public void associateListingFavorite(UUID listingId) {
        this.listingFavorites.add(listingId);
    }

    public void removeListingFavorite(UUID listingId) {
        this.listingFavorites.remove(listingId);
    }

    public void associateListing(UUID listingId) {
        super.associateListing(listingId);
    }

    public void removeListing(UUID listingId) {
        super.removeListing(listingId);
    }
}
