package housingapp;

import java.util.UUID;

public class Listing {

    private final UUID id;
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
        this.id = UUID.randomUUID();
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

    public Listing(UUID id, Property property, String description, double price, int leaseMonths, double squareFootage,
                   boolean petsAllowed, boolean hasGas, boolean hasElectric, boolean hasWater, boolean hasTrash,
                   boolean isSublease, boolean utilitiesIncluded, int numBedrooms, int numBathrooms, boolean hasShuttle,
                   boolean available) {
        this.id = id;
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

    public boolean hasGas() {
        return this.hasGas;
    }

    public boolean hasElectric() {
        return this.hasElectric;
    }

    public boolean hasWater() {
        return this.hasWater;
    }

    public boolean hasTrash() {
        return this.hasTrash;
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
}
