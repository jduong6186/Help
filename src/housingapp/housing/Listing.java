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

    /**
     * basic constructor for generic Listing, invoked by sub-classes when creating a listing at runtime
     * @param type type of listing (apartment or townhouse)
     * @param propertyId UUID of property the listing is on
     * @param description description of listing
     * @param price price of rent each month
     * @param leaseMonths number of months on lease
     * @param squareFootage square footage of listing
     * @param isSublease indicates whether listing is a sublease
     * @param utilitiesIncluded indicates whether utilities are included in rent
     * @param numBedrooms number of bedrooms in listing
     * @param numBathrooms number of bathrooms in listing
     * @param hasShuttle indicates whether a shuttle service is offered
     * @param available indicates whether listing is currently available
     * @param hasWasher indicates whether a washer is included
     * @param hasDryer indicates whether a dryer is included
     */
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

    /**
     * constructor to be used when reading pre-constructed listings from JSON input
     * @param id UUID of listing object
     * @param type type of listing (apartment or townhouse)
     * @param propertyId UUID of property the listing is on
     * @param description description of listing
     * @param price price of rent each month
     * @param leaseMonths number of months on lease
     * @param squareFootage square footage of listing
     * @param isSublease indicates whether listing is a sublease
     * @param utilitiesIncluded indicates whether utilities are included in rent
     * @param numBedrooms number of bedrooms in listing
     * @param numBathrooms number of bathrooms in listing
     * @param hasShuttle indicates whether a shuttle service is offered
     * @param available indicates whether listing is currently available
     * @param hasWasher indicates whether a washer is included
     * @param hasDryer indicates whether a dryer is included
     */
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

    /**
     * id accessor
     */
    public UUID getId() {
        return this.id;
    }

    /**
     * type accessor
     */
    public ListingType getType() {
        return this.type;
    }

    /**
     * propertyId accessor
     */
    public UUID getPropertyId() {
        return this.propertyId;
    }

    /**
     * description accessor
     */
    public String getDescription() {
        return this.description;
    }

    /**
     * price accessor
     */
    public double getPrice() {
        return this.price;
    }

    /**
     * leaseMonths accessor
     */
    public int getLeaseMonths() {
        return this.leaseMonths;
    }

    /**
     * squareFootage accessor
     */
    public double getSquareFootage() {
        return this.squareFootage;
    }

    /**
     * isSublease accessor
     */
    public boolean isSublease() {
        return this.isSublease;
    }

    /**
     * utilitiesIncluded accessor
     */
    public boolean utilitiesIncluded() {
        return this.utilitiesIncluded;
    }

    /**
     * numBedrooms accessor
     */
    public int getNumBedrooms() {
        return this.numBedrooms;
    }

    /**
     * numBathrooms accessor
     */
    public int getNumBathrooms() {
        return this.numBathrooms;
    }

    /**
     * hasShuttle accessor
     */
    public boolean hasShuttle() {
        return this.hasShuttle;
    }

    /**
     * available accessor
     */
    public boolean isAvailable() {
        return this.available;
    }

    /**
     * hasWasher accessor
     */
    public boolean hasWasher() {
        return this.hasWasher;
    }

    /**
     * hasDryer accessor
     */
    public boolean hasDryer() {
        return this.hasDryer;
    }

    /**
     * mutators
     */
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

    /**
     * returns generic listing details string (invoked by sub-classes for constructing details str)
     */
    public String getDetails() {
        ResourceManager rm = ResourceManager.getInstance();
        Property parentProperty = rm.getPropertyById(this.propertyId);
        String detailsStr = String.format("-----\nListing at %s\n-----\n", rm.getPropertyById(this.propertyId).getName());
        detailsStr += "Price: $" + this.price + "\n";
        detailsStr += this.numBedrooms + " bedrooms, " + this.numBathrooms + " bathrooms\n";
        detailsStr += this.leaseMonths + " month lease\n";
        detailsStr += this.squareFootage + " square feet\n";
        detailsStr += parentProperty.getDistanceToCampus() + "km from campus\n";
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

    /**
     * returns minimized information string
     */
    @Override
    public String toString() {
        ResourceManager rm = ResourceManager.getInstance();
        return String.format("Listing at %s | $%f | %d bedrooms | %d bathrooms", rm.getPropertyById(this.propertyId).getName(), this.price, this.numBedrooms, this.numBathrooms);
    }
}
