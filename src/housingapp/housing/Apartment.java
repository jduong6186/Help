package housingapp.housing;

import housingapp.ListingType;

import java.util.UUID;

public class Apartment extends Listing {

    private String apartmentNumber;
    private boolean hasParking;

    public Apartment(UUID propertyId, String description, double price, int leaseMonths, double squareFootage,
                     boolean petsAllowed, boolean isSublease, boolean utilitiesIncluded, int numBedrooms, int numBathrooms,
                     boolean hasShuttle, boolean available, String apartmentNumber, boolean hasParking) {
        super(ListingType.APARTMENT, propertyId, description, price, leaseMonths, squareFootage, petsAllowed, isSublease, utilitiesIncluded,
                numBedrooms, numBathrooms, hasShuttle, available);
        this.apartmentNumber = apartmentNumber;
        this.hasParking = hasParking;
    }

    public Apartment(UUID id, UUID propertyId, String description, double price, int leaseMonths, double squareFootage,
                     boolean petsAllowed, boolean isSublease, boolean utilitiesIncluded, int numBedrooms, int numBathrooms,
                     boolean hasShuttle, boolean available, String apartmentNumber, boolean hasParking) {
        super(id, ListingType.APARTMENT, propertyId, description, price, leaseMonths, squareFootage, petsAllowed, isSublease, utilitiesIncluded,
                numBedrooms, numBathrooms, hasShuttle, available);
        this.apartmentNumber = apartmentNumber;
        this.hasParking = hasParking;
    }

    public String getApartmentNumber() {
        return this.apartmentNumber;
    }

    public boolean hasParking() {
        return this.hasParking;
    }

    public void updateApartmentNumber(String apartmentNumber) {
        this.apartmentNumber = apartmentNumber;
    }

    public void updateHasParking(boolean hasParking) {
        this.hasParking = hasParking;
    }

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
