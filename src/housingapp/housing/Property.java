package housingapp.housing;

import java.util.ArrayList;
import java.util.UUID;

public class Property {

    private final UUID id;
    private final UUID landlordId;
    private final String name;
    private final String address;
    private final String zipCode;
    private final double distanceToCampus;
    private double damagesCost;
    private boolean furnished;
    private boolean petsAllowed;
    private boolean hasPool;
    private boolean hasGym;
    private boolean hasFreeWifi;
    private ArrayList<UUID> ratings;
    private ArrayList<UUID> listings;

    public Property(UUID landlordId, String name, String address, String zipCode, double distanceToCampus, double damagesCost,
                    boolean furnished, boolean petsAllowed, boolean hasPool, boolean hasGym, boolean hasFreeWifi) {
        this.id = UUID.randomUUID();
        this.landlordId = landlordId;
        this.name = name;
        this.address = address;
        this.zipCode = zipCode;
        this.distanceToCampus = distanceToCampus;
        this.damagesCost = damagesCost;
        this.furnished = furnished;
        this.petsAllowed = petsAllowed;
        this.hasPool = hasPool;
        this.hasGym = hasGym;
        this.hasFreeWifi = hasFreeWifi;
    }

    public Property(UUID id, UUID landlordId, String name, String address, String zipCode, double distanceToCampus, double damagesCost,
                    boolean furnished, boolean petsAllowed, boolean hasPool, boolean hasGym, boolean hasFreeWifi, ArrayList<UUID> ratings, ArrayList<UUID> listings) {
        this.id = id;
        this.landlordId = landlordId;
        this.name = name;
        this.address = address;
        this.zipCode = zipCode;
        this.distanceToCampus = distanceToCampus;
        this.damagesCost = damagesCost;
        this.furnished = furnished;
        this.petsAllowed = petsAllowed;
        this.hasPool = hasPool;
        this.hasGym = hasGym;
        this.hasFreeWifi = hasFreeWifi;
        if (ratings != null) {
            this.ratings = ratings;
        } else {
            this.ratings = new ArrayList<UUID>();
        }
        if (listings != null) {
            this.listings = listings;
        } else {
            this.listings = new ArrayList<UUID>();
        }
    }

    public UUID getId() {
        return this.id;
    }

    public UUID getLandlordId() {
        return this.landlordId;
    }

    public String getName() {
        return this.name;
    }

    public String getAddress() {
        return this.address;
    }

    public String getZipCode() {
        return this.zipCode;
    }

    public double getDistanceToCampus() {
        return this.distanceToCampus;
    }

    public double getDamagesCost() {
        return this.damagesCost;
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

    public void updateDamagesCost(double damagesCost) {
        this.damagesCost = damagesCost;
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
