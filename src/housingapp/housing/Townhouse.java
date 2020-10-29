package housingapp.housing;

import housingapp.housing.Listing;
import housingapp.housing.Property;

public class Townhouse extends Listing {

    private boolean hasGarage;
    private boolean hasDriveway;
    private boolean hasYard;
    private boolean hasFence;

    public Townhouse(Property property, String description, double price, int leaseMonths, double squareFootage,
                     boolean petsAllowed, boolean isSublease, boolean utilitiesIncluded, int numBedrooms, int numBathrooms,
                     boolean hasShuttle, boolean available, boolean hasGarage, boolean hasDriveway, boolean hasYard,
                     boolean hasFence) {
        super(property, description, price, leaseMonths, squareFootage, petsAllowed, isSublease, utilitiesIncluded,
                numBedrooms, numBathrooms, hasShuttle, available);
        this.hasGarage = hasGarage;
        this.hasDriveway = hasDriveway;
        this.hasYard = hasYard;
        this.hasFence = hasFence;
    }

    protected boolean hasGarage() {
        return this.hasGarage;
    }

    protected boolean hasDriveway() {
        return this.hasDriveway;
    }

    protected boolean hasYard() {
        return this.hasYard;
    }

    protected boolean hasFence() {
        return this.hasFence;
    }
}
