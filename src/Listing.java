public class Listing {

    private Property property;
    private String description;
    private double price;
    private int leaseMonths;
    private double squareFootage;
    private boolean petsAllowed;
    private boolean hasGas;
    private boolean hasElectric;
    private boolean hasWater;
    private boolean hasTrash;
    private boolean isSublease;
    private boolean utilitiesIncluded;
    private int numBedrooms;
    private int numBathrooms;
    private boolean hasShuttle;
    private boolean available;

    public Listing(Property property, String description, double price, int leaseMonths, double squareFootage,
                   boolean petsAllowed, boolean hasGas, boolean hasElectric, boolean hasWater, boolean hasTrash,
                   boolean isSublease, boolean utilitiesIncluded, int numBedrooms, int numBathrooms, boolean hasShuttle,
                   boolean available) {
        this.property = property;
        this.description = description;
        this.price = price;
        this.leaseMonths = leaseMonths;
        this.squareFootage = squareFootage;
        this.petsAllowed = petsAllowed;
        this.hasGas = hasGas;
        this.hasElectric = hasElectric;
        this.hasWater = hasWater;
        this.hasTrash = hasTrash;
        this.isSublease = isSublease;
        this.utilitiesIncluded = utilitiesIncluded;
        this.numBedrooms = numBedrooms;
        this.numBathrooms = numBathrooms;
        this.hasShuttle = hasShuttle;
        this.available = available;
    }

    protected Property getProperty() {
        return this.property;
    }

    protected String getDescription() {
        return this.description;
    }

    protected double getPrice() {
        return this.price;
    }

    protected int getLeaseMonths() {
        return this.leaseMonths;
    }

    protected double getSquareFootage() {
        return this.squareFootage;
    }

    protected boolean petsAllowed() {
        return this.petsAllowed;
    }

    protected boolean hasGas() {
        return this.hasGas;
    }

    protected boolean hasElectric() {
        return this.hasElectric;
    }

    protected boolean hasWater() {
        return this.hasWater;
    }

    protected boolean hasTrash() {
        return this.hasTrash;
    }

    protected boolean isSublease() {
        return this.isSublease;
    }

    protected boolean utilitiesIncluded() {
        return this.utilitiesIncluded;
    }

    protected int getNumBedrooms() {
        return this.numBedrooms;
    }

    protected int getNumBathrooms() {
        return this.numBathrooms;
    }

    protected boolean hasShuttle() {
        return this.hasShuttle;
    }

    protected boolean isAvailable() {
        return this.available;
    }
}
