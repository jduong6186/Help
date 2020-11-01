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
    private boolean petsAllowed;
    private boolean isSublease;
    private boolean utilitiesIncluded;
    private int numBedrooms;
    private int numBathrooms;
    private boolean hasShuttle;
    private boolean available;

    public Listing(ListingType type, UUID propertyId, String description, double price, int leaseMonths, double squareFootage,
                   boolean petsAllowed, boolean isSublease, boolean utilitiesIncluded, int numBedrooms, int numBathrooms,
                   boolean hasShuttle, boolean available) {
        this.id = UUID.randomUUID();
        this.type = type;
        this.propertyId = propertyId;
        this.description = description;
        this.price = price;
        this.leaseMonths = leaseMonths;
        this.squareFootage = squareFootage;
        this.petsAllowed = petsAllowed;
        this.isSublease = isSublease;
        this.utilitiesIncluded = utilitiesIncluded;
        this.numBedrooms = numBedrooms;
        this.numBathrooms = numBathrooms;
        this.hasShuttle = hasShuttle;
        this.available = available;
    }

    public Listing(UUID id, ListingType type, UUID propertyId, String description, double price, int leaseMonths, double squareFootage,
                   boolean petsAllowed, boolean isSublease, boolean utilitiesIncluded, int numBedrooms, int numBathrooms,
                   boolean hasShuttle, boolean available) {
        this.id = id;
        this.type = type;
        this.propertyId = propertyId;
        this.description = description;
        this.price = price;
        this.leaseMonths = leaseMonths;
        this.squareFootage = squareFootage;
        this.petsAllowed = petsAllowed;
        this.isSublease = isSublease;
        this.utilitiesIncluded = utilitiesIncluded;
        this.numBedrooms = numBedrooms;
        this.numBathrooms = numBathrooms;
        this.hasShuttle = hasShuttle;
        this.available = available;
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

    public boolean petsAllowed() {
        return this.petsAllowed;
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

    public void updatePetsAllowed(boolean petsAllowed) {
        this.petsAllowed = petsAllowed;
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

    public String getDetails() {
        ResourceManager rm = ResourceManager.getInstance();
        String detailsStr = String.format("-----\nListing at %s\n-----", rm.getPropertyById(this.propertyId).getName());
        detailsStr += "Price: $" + this.price + "\n";
        detailsStr += this.numBedrooms + " bedrooms, " + this.numBathrooms + " bathrooms\n";
        detailsStr += this.leaseMonths + " month lease\n";
        detailsStr += this.squareFootage + " square feet\n";
        if (this.petsAllowed) {
            detailsStr += "Pets *are* allowed\n";
        } else {
            detailsStr += "Pets *are not* allowed\n";
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
        return detailsStr;
    }

    @Override
    public String toString() {
        ResourceManager rm = ResourceManager.getInstance();
        return String.format("Listing at %s | $%f | %d bedrooms | %d bathrooms", rm.getPropertyById(this.propertyId).getName(), this.price, this.numBedrooms, this.numBathrooms);
    }
}
