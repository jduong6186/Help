package housingapp.housing;

import housingapp.housing.Listing;
import housingapp.housing.Property;

public class Apartment extends Listing {

    private String apartmentNumber;
    private boolean hasParking;

    public Apartment(Property property, String description, double price, int leaseMonths, double squareFootage,
                     boolean petsAllowed, boolean isSublease, boolean utilitiesIncluded, int numBedrooms, int numBathrooms,
                     boolean hasShuttle, boolean available, String apartmentNumber, boolean hasParking) {
        super(property, description, price, leaseMonths, squareFootage, petsAllowed, isSublease, utilitiesIncluded,
                numBedrooms, numBathrooms, hasShuttle, available);
        this.apartmentNumber = apartmentNumber;
        this.hasParking = hasParking;
    }

    protected String getApartmentNumber() {
        return this.apartmentNumber;
    }

    protected boolean hasParking() {
        return this.hasParking;
    }
}
