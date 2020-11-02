package housingapp.housing;

import housingapp.ListingType;

import java.util.UUID;

public class Townhouse extends Listing {

    private boolean hasGarage;
    private boolean hasDriveway;
    private boolean hasYard;
    private boolean hasFence;

    /**
     * basic constructor for Townhouse, used when creating Townhouse listing at runtime
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
     * @param hasGarage indicates whether townhouse has a garage
     * @param hasDriveway indicates whether townhouse has a driveway
     * @param hasYard indicates whether townhouse has a yard
     * @param hasFence indicates whether townhouse has a fence
     */
    public Townhouse(UUID propertyId, String description, double price, int leaseMonths, double squareFootage,
                     boolean isSublease, boolean utilitiesIncluded, int numBedrooms, int numBathrooms,
                     boolean hasShuttle, boolean available, boolean hasWasher, boolean hasDryer, boolean hasGarage, boolean hasDriveway, boolean hasYard,
                     boolean hasFence) {
        super(ListingType.TOWNHOUSE, propertyId, description, price, leaseMonths, squareFootage, isSublease, utilitiesIncluded,
                numBedrooms, numBathrooms, hasShuttle, available, hasWasher, hasDryer);
        this.hasGarage = hasGarage;
        this.hasDriveway = hasDriveway;
        this.hasYard = hasYard;
        this.hasFence = hasFence;
    }

    /**
     * constructor to be used when generating Townhouse from JSON input
     * @param id UUID of townhouse listing
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
     * @param hasGarage indicates whether townhouse has a garage
     * @param hasDriveway indicates whether townhouse has a driveway
     * @param hasYard indicates whether townhouse has a yard
     * @param hasFence indicates whether townhouse has a fence
     */
    public Townhouse(UUID id, UUID propertyId, String description, double price, int leaseMonths, double squareFootage,
                     boolean isSublease, boolean utilitiesIncluded, int numBedrooms, int numBathrooms,
                     boolean hasShuttle, boolean available, boolean hasWasher, boolean hasDryer, boolean hasGarage, boolean hasDriveway, boolean hasYard,
                     boolean hasFence) {
        super(id, ListingType.TOWNHOUSE, propertyId, description, price, leaseMonths, squareFootage, isSublease, utilitiesIncluded,
                numBedrooms, numBathrooms, hasShuttle, available, hasWasher, hasDryer);
        this.hasGarage = hasGarage;
        this.hasDriveway = hasDriveway;
        this.hasYard = hasYard;
        this.hasFence = hasFence;
    }

    /**
     * accessors
     */
    public boolean hasGarage() {
        return this.hasGarage;
    }

    public boolean hasDriveway() {
        return this.hasDriveway;
    }

    public boolean hasYard() {
        return this.hasYard;
    }

    public boolean hasFence() {
        return this.hasFence;
    }

    /**
     * mutators
     */
    public void updateHasGarage(boolean hasGarage) {
        this.hasGarage = hasGarage;
    }

    public void updateHasDriveway(boolean hasDriveway) {
        this.hasDriveway = hasDriveway;
    }

    public void updateHasYard(boolean hasYard) {
        this.hasYard = hasYard;
    }

    public void updateHasFence(boolean hasFence) {
        this.hasFence = hasFence;
    }

    /**
     * returns detailed description of townhouse listing, invokes super-method for generic details
     */
    @Override
    public String getDetails() {
        String details = super.getDetails();
        if (this.hasGarage) {
            details += "Townhouse *has* a garage\n";
        } else {
            details += "Townhouse *does not have* a garage\n";
        }
        if (this.hasDriveway) {
            details += "Townhouse *has* a driveway\n";
        } else {
            details += "Townhouse *does not have* a driveway\n";
        }
        if (this.hasYard) {
            details += "Townhouse *has* a yard\n";
        } else {
            details += "Townhouse *does not have* a yard\n";
        }
        if (this.hasFence) {
            details += "Townhouse *has* a fence\n";
        } else {
            details += "Townhouse *does not have* a fence\n";
        }
        return details;
    }
}
