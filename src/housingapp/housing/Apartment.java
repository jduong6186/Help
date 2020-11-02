package housingapp.housing;

import housingapp.ListingType;

import java.util.UUID;

public class Apartment extends Listing {

    private String apartmentNumber;
    private boolean hasParking;

    /**
     * regular constructor for Apartment listing, used when creating an apartment at runtime
     * @param propertyId UUID of property this listing belongs to
     * @param description description of the listing
     * @param price price of rent per month
     * @param leaseMonths number of months on lease
     * @param squareFootage square footage of apartment
     * @param isSublease indicates whether listing is a sublease
     * @param utilitiesIncluded indicates whether utilities are included in rent cost
     * @param numBedrooms number of bedrooms in apartment
     * @param numBathrooms nubmer of bathrooms in apartment
     * @param hasShuttle indicates whether shuttle service is offered
     * @param available indicates whether listing is currently available
     * @param hasWasher indicates whether comes with a washer
     * @param hasDryer indicates whether comes with a dryer
     * @param apartmentNumber apartment number e.g. "112A"
     * @param hasParking indicates whether apartment complex has parking
     */
    public Apartment(UUID propertyId, String description, double price, int leaseMonths, double squareFootage,
                     boolean isSublease, boolean utilitiesIncluded, int numBedrooms, int numBathrooms,
                     boolean hasShuttle, boolean available, boolean hasWasher, boolean hasDryer, String apartmentNumber, boolean hasParking) {
        super(ListingType.APARTMENT, propertyId, description, price, leaseMonths, squareFootage, isSublease, utilitiesIncluded,
                numBedrooms, numBathrooms, hasShuttle, available, hasWasher, hasDryer);
        this.apartmentNumber = apartmentNumber;
        this.hasParking = hasParking;
    }

    /**
     * constructor to be used when reading pre-constructed objects from JSON input file
     * @param id UUID of apartment object
     * @param propertyId UUID of property this listing belongs to
     * @param description description of the listing
     * @param price price of rent per month
     * @param leaseMonths number of months on lease
     * @param squareFootage square footage of apartment
     * @param isSublease indicates whether listing is a sublease
     * @param utilitiesIncluded indicates whether utilities are included in rent cost
     * @param numBedrooms number of bedrooms in apartment
     * @param numBathrooms nubmer of bathrooms in apartment
     * @param hasShuttle indicates whether shuttle service is offered
     * @param available indicates whether listing is currently available
     * @param hasWasher indicates whether comes with a washer
     * @param hasDryer indicates whether comes with a dryer
     * @param apartmentNumber apartment number e.g. "112A"
     * @param hasParking indicates whether apartment complex has parking
     */
    public Apartment(UUID id, UUID propertyId, String description, double price, int leaseMonths, double squareFootage,
                     boolean isSublease, boolean utilitiesIncluded, int numBedrooms, int numBathrooms,
                     boolean hasShuttle, boolean available, boolean hasWasher, boolean hasDryer, String apartmentNumber, boolean hasParking) {
        super(id, ListingType.APARTMENT, propertyId, description, price, leaseMonths, squareFootage, isSublease, utilitiesIncluded,
                numBedrooms, numBathrooms, hasShuttle, available, hasWasher, hasDryer);
        this.apartmentNumber = apartmentNumber;
        this.hasParking = hasParking;
    }

    /**
     * apartmentNumber accessor
     */
    public String getApartmentNumber() {
        return this.apartmentNumber;
    }

    /**
     * hasParking accessor
     */
    public boolean hasParking() {
        return this.hasParking;
    }

    /**
     * apartmentNumber mutator
     * @param apartmentNumber new apartment number
     */
    public void updateApartmentNumber(String apartmentNumber) {
        this.apartmentNumber = apartmentNumber;
    }

    /**
     * hasParking mutator
     * @param hasParking new hasParking boolean indicator
     */
    public void updateHasParking(boolean hasParking) {
        this.hasParking = hasParking;
    }

    /**
     * returns details string for apartment listing (details appended to generic listing details)
     */
    @Override
    public String getDetails() {
        String details = super.getDetails();
        details += String.format("Apartment number: %s\n", this.apartmentNumber);
        if (this.hasParking) {
            details += "Apartment *has* parking\n";
        } else {
            details += "Apartment *does not have* parking\n";
        }
        return details;
    }
}
