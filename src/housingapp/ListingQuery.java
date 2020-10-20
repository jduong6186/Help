package housingapp;

import java.util.ArrayList;
import java.util.UUID;

/**
 * Singleton class which queries listings using given search params
 * @author Andrew Eldridge
 */

// TODO: support chaining multiple filters (pass ArrayList<Listing> as param rather than pulling from rm)
// TODO: implement result prioritizing (ordering by relevance according to params)

public class ListingQuery {

    private static ListingQuery instance;
    private final ResourceManager rm = ResourceManager.getInstance();

    public static ListingQuery getInstance() {
        if (instance == null) {
            instance = new ListingQuery();
        }
        return instance;
    }

    /**
     * filters listings by property
     * @param propertyId UUID of property to filter by
     */
    public ArrayList<Listing> getListingsByProperty(UUID propertyId) {
        ArrayList<Listing> ret = new ArrayList<Listing>();
        for (Listing listing : rm.getListings()) {
            if (listing.getProperty().getId().equals(propertyId)) {
                ret.add(listing);
            }
        }
        return ret;
    }

    /**
     * filters listings by description substr
     * @param substr string to search for in description
     */
    public ArrayList<Listing> getListingsByDescription(String substr) {
        ArrayList<Listing> ret = new ArrayList<Listing>();
        // TODO: implement regex search for substr in listing description
        return ret;
    }

    // todo: implement
    public ArrayList<Listing> getListingsByPrice(double priceLower, double priceUpper) {
        ArrayList<Listing> ret = new ArrayList<Listing>();
        return ret;
    }

    // todo: implement
    public ArrayList<Listing> getListingsByLeaseMonths(int leaseMonthsLower, int leaseMonthsUpper) {
        ArrayList<Listing> ret = new ArrayList<Listing>();
        return ret;
    }

    // todo: implement
    public ArrayList<Listing> getListingsBySquareFootage(double squareFootageLower, double squareFootageUpper) {
        ArrayList<Listing> ret = new ArrayList<Listing>();
        return ret;
    }

    public ArrayList<Listing> getListingsByPetsAllowed() {
        ArrayList<Listing> ret = new ArrayList<Listing>();
        for (Listing listing : rm.getListings()) {
            if (listing.petsAllowed()) {
                ret.add(listing);
            }
        }
        return ret;
    }

    public ArrayList<Listing> getListingsByHasGas() {
        ArrayList<Listing> ret = new ArrayList<Listing>();
        for (Listing listing : rm.getListings()) {
            if (listing.hasGas()) {
                ret.add(listing);
            }
        }
        return ret;
    }

    public ArrayList<Listing> getListingsByHasElectric() {
        ArrayList<Listing> ret = new ArrayList<Listing>();
        for (Listing listing : rm.getListings()) {
            if (listing.hasElectric()) {
                ret.add(listing);
            }
        }
        return ret;
    }

    public ArrayList<Listing> getListingsByHasWater() {
        ArrayList<Listing> ret = new ArrayList<Listing>();
        for (Listing listing : rm.getListings()) {
            if (listing.hasWater()) {
                ret.add(listing);
            }
        }
        return ret;
    }

    public ArrayList<Listing> getListingsByHasTrash() {
        ArrayList<Listing> ret = new ArrayList<Listing>();
        for (Listing listing : rm.getListings()) {
            if (listing.hasTrash()) {
                ret.add(listing);
            }
        }
        return ret;
    }

    public ArrayList<Listing> getListingsByIsSublease() {
        ArrayList<Listing> ret = new ArrayList<Listing>();
        for (Listing listing : rm.getListings()) {
            if (listing.isSublease()) {
                ret.add(listing);
            }
        }
        return ret;
    }

    public ArrayList<Listing> getListingsByUtilitiesIncluded() {
        ArrayList<Listing> ret = new ArrayList<Listing>();
        for (Listing listing : rm.getListings()) {
            if (listing.utilitiesIncluded()) {
                ret.add(listing);
            }
        }
        return ret;
    }

    // todo: implement
    public ArrayList<Listing> getListingsByNumBedrooms(int numBedroomsLower, int numBedroomsUpper) {
        ArrayList<Listing> ret = new ArrayList<Listing>();
        return ret;
    }

    // todo: implement
    public ArrayList<Listing> getListingsByNumBathrooms(int numBathroomsLower, int numBathroomsUpper) {
        ArrayList<Listing> ret = new ArrayList<Listing>();
        return ret;
    }

    public ArrayList<Listing> getListingsByHasShuttle() {
        ArrayList<Listing> ret = new ArrayList<Listing>();
        for (Listing listing : rm.getListings()) {
            if (listing.hasShuttle()) {
                ret.add(listing);
            }
        }
        return ret;
    }

    public ArrayList<Listing> getListingsByAvailable() {
        ArrayList<Listing> ret = new ArrayList<Listing>();
        for (Listing listing : rm.getListings()) {
            if (listing.isAvailable()) {
                ret.add(listing);
            }
        }
        return ret;
    }
}
