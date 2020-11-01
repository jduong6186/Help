package housingapp.housing;

import java.util.ArrayList;
import java.util.UUID;

public class Property {

    private final UUID id;
    private final String name;
    private final String address;
    private final double distanceToCampus;
    private ArrayList<UUID> ratings;
    private ArrayList<UUID> listings;

    public Property(String name, String address, double distanceToCampus) {
        this.id = UUID.randomUUID();
        this.name = name;
        this.address = address;
        this.distanceToCampus = distanceToCampus;
    }

    public Property(UUID id, String name, String address, double distanceToCampus, ArrayList<UUID> ratings, ArrayList<UUID> listings) {
        this.id = id;
        this.name = name;
        this.address = address;
        this.distanceToCampus = distanceToCampus;
        this.ratings = ratings;
        this.listings = listings;
    }

    public UUID getId() {
        return this.id;
    }

    public String getName() {
        return this.name;
    }

    public String getAddress() {
        return this.address;
    }

    public double getDistanceToCampus() {
        return this.distanceToCampus;
    }

    public ArrayList<UUID> getRatings() {
        return this.ratings;
    }

    public ArrayList<UUID> getListings() {
        return this.listings;
    }

    protected void associateRating(UUID ratingId) {
        this.ratings.add(ratingId);
    }

    protected void associateListing(UUID listingId) {
        this.listings.add(listingId);
    }
}
