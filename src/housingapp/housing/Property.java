package housingapp.housing;

import java.util.ArrayList;
import java.util.UUID;

public class Property {

    private final UUID id;
    private final String name;
    private final String address;
    private final double distanceToCampus;
    private boolean furnished;
    private boolean petsAllowed;
    private boolean hasPool;
    private boolean hasGym;
    private boolean hasFreeWifi;
    private ArrayList<UUID> ratings;
    private ArrayList<UUID> listings;

    public Property(String name, String address, double distanceToCampus, boolean furnished, boolean petsAllowed, boolean hasPool,
                    boolean hasGym, boolean hasFreeWifi) {
        this.id = UUID.randomUUID();
        this.name = name;
        this.address = address;
        this.distanceToCampus = distanceToCampus;
        this.furnished = furnished;
        this.petsAllowed = petsAllowed;
        this.hasPool = hasPool;
        this.hasGym = hasGym;
        this.hasFreeWifi = hasFreeWifi;
    }

    public Property(UUID id, String name, String address, double distanceToCampus, boolean furnished, boolean petsAllowed, boolean hasPool,
                    boolean hasGym, boolean hasFreeWifi, ArrayList<UUID> ratings, ArrayList<UUID> listings) {
        this.id = id;
        this.name = name;
        this.address = address;
        this.distanceToCampus = distanceToCampus;
        this.furnished = furnished;
        this.petsAllowed = petsAllowed;
        this.hasPool = hasPool;
        this.hasGym = hasGym;
        this.hasFreeWifi = hasFreeWifi;
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

    public boolean isFurnished() {
        return this.furnished;
    }

    public boolean petsAllowed() {
        return this.petsAllowed;
    }

    public boolean hasPool() {
        return this.hasPool;
    }

    public boolean hasGym() {
        return this.hasGym;
    }

    public boolean hasFreeWifi() {
        return this.hasFreeWifi;
    }

    public ArrayList<UUID> getRatings() {
        return this.ratings;
    }

    public ArrayList<UUID> getListings() {
        return this.listings;
    }

    public void updateFurnished(boolean furnished) {
        this.furnished = furnished;
    }

    public void updatePetsAllowed(boolean petsAllowed) {
        this.petsAllowed = petsAllowed;
    }

    public void updateHasPool(boolean hasPool) {
        this.hasPool = hasPool;
    }

    public void updateHasGym(boolean hasGym) {
        this.hasGym = hasGym;
    }

    public void updateHasFreeWifi(boolean hasFreeWifi) {
        this.hasFreeWifi = hasFreeWifi;
    }

    public void associateRating(UUID ratingId) {
        this.ratings.add(ratingId);
    }

    public void associateListing(UUID listingId) {
        this.listings.add(listingId);
    }
}
