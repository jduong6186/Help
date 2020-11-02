package housingapp.housing;

import housingapp.ListingType;

import java.util.UUID;

public class Townhouse extends Listing {

    private boolean hasGarage;
    private boolean hasDriveway;
    private boolean hasYard;
    private boolean hasFence;

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
