package housingapp.housing;

import java.util.UUID;

public class Listing {

    private final UUID id;
    private Property property;
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

    public Listing(Property property, String description, double price, int leaseMonths, double squareFootage,
                   boolean petsAllowed, boolean isSublease, boolean utilitiesIncluded, int numBedrooms, int numBathrooms,
                   boolean hasShuttle, boolean available) {
        this.id = UUID.randomUUID();
        this.property = property;
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

    public Listing(UUID id, Property property, String description, double price, int leaseMonths, double squareFootage,
                   boolean petsAllowed, boolean isSublease, boolean utilitiesIncluded, int numBedrooms, int numBathrooms,
                   boolean hasShuttle, boolean available) {
        this.id = id;
        this.property = property;
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

    public Property getProperty() {
        return this.property;
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

    public void setProperty(Property property) {
        this.property = property;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public void setLeaseMonths(int leaseMonths) {
        this.leaseMonths = leaseMonths;
    }

    public void setSquareFootage(double squareFootage) {
        this.squareFootage = squareFootage;
    }

    public void setPetsAllowed(boolean petsAllowed) {
        this.petsAllowed = petsAllowed;
    }

    public void setIsSublease(boolean isSublease) {
        this.isSublease = isSublease;
    }

    public void setUtilitiesIncluded(boolean utilitiesIncluded) {
        this.utilitiesIncluded = utilitiesIncluded;
    }

    public void setNumBedrooms(int numBedrooms) {
        this.numBedrooms = numBedrooms;
    }

    public void setNumBathrooms(int numBathrooms) {
        this.numBathrooms = numBathrooms;
    }

    public void setHasShuttle(boolean hasShuttle) {
        this.hasShuttle = hasShuttle;
    }

    public void setAvailable(boolean isAvailable) {
        this.available = isAvailable;
    }

    public String getDetails() {
        String detailsStr = String.format("-----\nListing at %s\n-----", this.property);
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
        return String.format("Listing at %s | $%f | %d bedrooms | %d bathrooms", this.property, this.price, this.numBedrooms, this.numBathrooms);
    }
}
