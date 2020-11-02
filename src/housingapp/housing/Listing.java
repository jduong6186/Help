package housingapp.housing;

import housingapp.ListingType;
import housingapp.query.ResourceManager;

import java.util.UUID;

public abstract class Listing {

    private final UUID id;
    private final ListingType type;
    private UUID propertyId;
    private String description;
    private double price;
    private int leaseMonths;
    private double squareFootage;
    private boolean isSublease;
    private boolean utilitiesIncluded;
    private int numBedrooms;
    private int numBathrooms;
    private boolean hasShuttle;
    private boolean available;
    private boolean hasWasher;
    private boolean hasDryer;

    public Listing(ListingType type, UUID propertyId, String description, double price, int leaseMonths, double squareFootage,
                   boolean isSublease, boolean utilitiesIncluded, int numBedrooms, int numBathrooms,
                   boolean hasShuttle, boolean available, boolean hasWasher, boolean hasDryer) {
        this.id = UUID.randomUUID();
        this.type = type;
        this.propertyId = propertyId;
        this.description = description;
        this.price = price;
        this.leaseMonths = leaseMonths;
        this.squareFootage = squareFootage;
        this.isSublease = isSublease;
        this.utilitiesIncluded = utilitiesIncluded;
        this.numBedrooms = numBedrooms;
        this.numBathrooms = numBathrooms;
        this.hasShuttle = hasShuttle;
        this.available = available;
        this.hasWasher = hasWasher;
        this.hasDryer = hasDryer;
    }

    public Listing(UUID id, ListingType type, UUID propertyId, String description, double price, int leaseMonths, double squareFootage,
                   boolean isSublease, boolean utilitiesIncluded, int numBedrooms, int numBathrooms,
                   boolean hasShuttle, boolean available, boolean hasWasher, boolean hasDryer) {
        this.id = id;
        this.type = type;
        this.propertyId = propertyId;
        this.description = description;
        this.price = price;
        this.leaseMonths = leaseMonths;
        this.squareFootage = squareFootage;
        this.isSublease = isSublease;
        this.utilitiesIncluded = utilitiesIncluded;
        this.numBedrooms = numBedrooms;
        this.numBathrooms = numBathrooms;
        this.hasShuttle = hasShuttle;
        this.available = available;
        this.hasWasher = hasWasher;
        this.hasDryer = hasDryer;
    }

    public UUID getId() {
        return this.id;
    }

    public ListingType getType() {
        return this.type;
    }

    public UUID getPropertyId() {
        return this.propertyId;
    }

    public String getDescription() {
        return this.description;
    }

    public double getPrice() {
        return this.price;
    }

    public int getLeaseMonths() {
        return this.leaseMonths;
    }

    public double getSquareFootage() {
        return this.squareFootage;
    }

    public boolean isSublease() {
        return this.isSublease;
    }

    public boolean utilitiesIncluded() {
        return this.utilitiesIncluded;
    }

    public int getNumBedrooms() {
        return this.numBedrooms;
    }

    public int getNumBathrooms() {
        return this.numBathrooms;
    }

    public boolean hasShuttle() {
        return this.hasShuttle;
    }

    public boolean isAvailable() {
        return this.available;
    }

    public boolean hasWasher() {
        return this.hasWasher;
    }

    public boolean hasDryer() {
        return this.hasDryer;
    }

    public void updatePropertyId(UUID propertyId) {
        this.propertyId = propertyId;
    }

    public void updateDescription(String description) {
        this.description = description;
    }

    public void updatePrice(double price) {
        this.price = price;
    }

    public void updateLeaseMonths(int leaseMonths) {
        this.leaseMonths = leaseMonths;
    }

    public void updateSquareFootage(double squareFootage) {
        this.squareFootage = squareFootage;
    }

    public void updateIsSublease(boolean isSublease) {
        this.isSublease = isSublease;
    }

    public void updateUtilitiesIncluded(boolean utilitiesIncluded) {
        this.utilitiesIncluded = utilitiesIncluded;
    }

    public void updateNumBedrooms(int numBedrooms) {
        this.numBedrooms = numBedrooms;
    }

    public void updateNumBathrooms(int numBathrooms) {
        this.numBathrooms = numBathrooms;
    }

    public void updateHasShuttle(boolean hasShuttle) {
        this.hasShuttle = hasShuttle;
    }

    public void updateAvailable(boolean isAvailable) {
        this.available = isAvailable;
    }

    public void updateHasWasher(boolean hasWasher) {
        this.hasWasher = hasWasher;
    }

    public void updateHasDryer(boolean hasDryer) {
        this.hasDryer = hasDryer;
    }

    public String getDetails() {
        ResourceManager rm = ResourceManager.getInstance();
        Property parentProperty = rm.getPropertyById(this.propertyId);
        String detailsStr = String.format("-----\nListing at %s\n-----\n", rm.getPropertyById(this.propertyId).getName());
        detailsStr += "Price: $" + this.price + "\n";
        detailsStr += this.numBedrooms + " bedrooms, " + this.numBathrooms + " bathrooms\n";
        detailsStr += this.leaseMonths + " month lease\n";
        detailsStr += this.squareFootage + " square feet\n";
        if (parentProperty.isFurnished()) {
            detailsStr += "Furnished\n";
        } else {
            detailsStr += "*NOT* furnished\n";
        }
        if (parentProperty.petsAllowed()) {
            detailsStr += "Pets *are* allowed\n";
        } else {
            detailsStr += "Pets *are not* allowed\n";
        }
        if (parentProperty.hasPool()) {
            detailsStr += "*Has* pool\n";
        } else {
            detailsStr += "*Does not have* pool\n";
        }
        if (parentProperty.hasGym()) {
            detailsStr += "*Has* gym\n";
        } else {
            detailsStr += "*Does not have* gym\n";
        }
        if (parentProperty.hasFreeWifi()) {
            detailsStr += "Free Wifi provided\n";
        } else {
            detailsStr += "Free Wifi *NOT* provided\n";
        }
        if (this.isSublease) {
            detailsStr += "Sublease*\n";
        }
        if (this.utilitiesIncluded) {
            detailsStr += "Utilities *are* included\n";
        } else {
            detailsStr += "Utilities *are not* included\n";
        }
        if (this.hasShuttle) {
            detailsStr += "Shuttle service offered\n";
        } else {
            detailsStr += "No shuttle service offered\n";
        }
        if (this.hasWasher) {
            detailsStr += "*Has* washer\n";
        } else {
            detailsStr += "*Does not have* washer\n";
        }
        if (this.hasDryer) {
            detailsStr += "*Has* dryer\n";
        } else {
            detailsStr += "*Does not have* dryer\n";
        }
        return detailsStr;
    }

    @Override
    public String toString() {
        ResourceManager rm = ResourceManager.getInstance();
        return String.format("Listing at %s | $%f | %d bedrooms | %d bathrooms", rm.getPropertyById(this.propertyId).getName(), this.price, this.numBedrooms, this.numBathrooms);
    }
}
