package housingapp.query;

import housingapp.housing.Listing;
import housingapp.user.Student;

import java.util.ArrayList;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Singleton class which queries listings using given search params
 * @authors Andrew Eldridge, John Michael Falzarano
 */

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
     * returns listings which satisfy all recommendation settings on student profile
     * @param studentUser student object to retrieve query preferences from
     */
    public ArrayList<Listing> getListingsByRecommended(Student studentUser) {
        // track most recent filtered values and most recent non-null filtered values
        ArrayList<Listing> ret = getListingsByAvailable(rm.getListings());
        ArrayList<Listing> nonNullRet = ret;

        // cascade filters until either all filters are applied or a null list is returned
        ret = getListingsByPrice(ret, studentUser.getPriceRangeLower(), studentUser.getPriceRangeUpper());
        if (ret != null) {
            nonNullRet = ret;
            ret = getListingsByMaxTravelDistance(ret, studentUser.getMaxTravelDistance());
            if (ret != null) {
                nonNullRet = ret;
                ret = getListingsByNumBedrooms(ret, studentUser.getMinRoommates()+1, studentUser.getMaxRoommates()+1);
                if (ret != null) {
                    nonNullRet = ret;
                    if (studentUser.hasPets()) {
                        ret = getListingsByPetsAllowed(ret, true);
                        if (ret != null) {
                            // all filters applied successfully, return listings
                            return ret;
                        }
                    }
                }
            }
        }
        // failed to apply all filters, return last non-null filtered results
        return nonNullRet;
    }

    /**
     * filters listings by property
     * @param propertyId UUID of property to filter by
     */
    public ArrayList<Listing> getListingsByProperty(ArrayList<Listing> listings, UUID propertyId) {
        ArrayList<Listing> ret = new ArrayList<Listing>();
        for (Listing listing : listings) {
            if (listing.getPropertyId().equals(propertyId)) {
                ret.add(listing);
            }
        }
        return ret;
    }

    /**
     * filters listings by description substr
     * @param substr string to search for in description
     */
    public ArrayList<Listing> getListingsByDescription(ArrayList<Listing> listings, String substr) {
        ArrayList<Listing> ret = new ArrayList<Listing>();
        for (Listing listing : listings) {
        	String listingsByDescription = listing.getDescription();
        	Pattern description = Pattern.compile(listingsByDescription);
        	Matcher filter = description.matcher(substr);
        	if (filter.find()) {
        		ret.add(listing);
        	}
        }
        return ret;
    }

    public ArrayList<Listing> getListingsByPrice(ArrayList<Listing> listings, double priceLower, double priceUpper) {
        ArrayList<Listing> ret = new ArrayList<Listing>();
        for (Listing listing : listings) {
            double listingPrice = listing.getPrice();
            if (listingPrice >= priceLower && listingPrice <= priceUpper) {
                ret.add(listing);
            }
        }
        return ret;
    }

    public ArrayList<Listing> getListingsByMaxTravelDistance(ArrayList<Listing> listings, double maxTravelDistance) {
        ArrayList<Listing> ret = new ArrayList<Listing>();
        for (Listing listing : listings) {
            if (maxTravelDistance >= rm.getPropertyById(listing.getPropertyId()).getDistanceToCampus()) {
                ret.add(listing);
            }
        }
        return ret;
    }


    public ArrayList<Listing> getListingsByLeaseMonths(ArrayList<Listing> listings, int leaseMonthsLower, int leaseMonthsUpper) {
        ArrayList<Listing> ret = new ArrayList<Listing>();
        for (Listing listing : listings) {
        	int listingMonth = listing.getLeaseMonths();
        	if (listingMonth >= leaseMonthsLower && listingMonth <= leaseMonthsUpper) {
                ret.add(listing);
        	}
        }
        return ret;
    }


    public ArrayList<Listing> getListingsBySquareFootage(ArrayList<Listing> listings, double squareFootageLower, double squareFootageUpper) {
        ArrayList<Listing> ret = new ArrayList<Listing>();
        for (Listing listing : listings) {
        	int listingFootage = listing.getLeaseMonths();
        	if (listingFootage >= squareFootageLower && listingFootage <= squareFootageUpper) {
                ret.add(listing);
        	}
        }
        return ret;
    }

    public ArrayList<Listing> getListingsByPetsAllowed(ArrayList<Listing> listings, boolean petsAllowed) {
        ArrayList<Listing> ret = new ArrayList<Listing>();
        for (Listing listing : listings) {
            if (rm.getPropertyById(listing.getPropertyId()).petsAllowed() == petsAllowed) {
                ret.add(listing);
            }
        }
        return ret;
    }

    public ArrayList<Listing> getListingsByIsSublease(ArrayList<Listing> listings) {
        ArrayList<Listing> ret = new ArrayList<Listing>();
        for (Listing listing : listings) {
            if (listing.isSublease()) {
                ret.add(listing);
            }
        }
        return ret;
    }

    public ArrayList<Listing> getListingsByUtilitiesIncluded(ArrayList<Listing> listings, boolean utilitiesIncluded) {
        ArrayList<Listing> ret = new ArrayList<Listing>();
        for (Listing listing : listings) {
            if (listing.utilitiesIncluded() == utilitiesIncluded) {
                ret.add(listing);
            }
        }
        return ret;
    }

    public ArrayList<Listing> getListingsByNumBedrooms(ArrayList<Listing> listings, int numBedroomsLower, int numBedroomsUpper) {
        ArrayList<Listing> ret = new ArrayList<Listing>();
        for (Listing listing : listings) {
        	int listingNumBedrooms = listing.getNumBedrooms();
            if (numBedroomsLower <= listingNumBedrooms && numBedroomsUpper >= listingNumBedrooms) {
                ret.add(listing);
            }
        }
        return ret;
    }

    public ArrayList<Listing> getListingsByNumBathrooms(ArrayList<Listing> listings, int numBathroomsLower, int numBathroomsUpper) {
        ArrayList<Listing> ret = new ArrayList<Listing>();
    	for (Listing listing : listings) {
    		int listingNumBathrooms = listing.getNumBathrooms();
    		if (numBathroomsLower <= listingNumBathrooms && numBathroomsUpper >= listingNumBathrooms) {
    			ret.add(listing);
    		}
    	}
        return ret;
    }

    public ArrayList<Listing> getListingsByHasShuttle(ArrayList<Listing> listings, boolean hasShuttle) {
        ArrayList<Listing> ret = new ArrayList<Listing>();
        for (Listing listing : listings) {
            if (listing.hasShuttle() == hasShuttle) {
                ret.add(listing);
            }
        }
        return ret;
    }

    public ArrayList<Listing> getListingsByAvailable(ArrayList<Listing> listings) {
        ArrayList<Listing> ret = new ArrayList<Listing>();
        for (Listing listing : listings) {
            if (listing.isAvailable()) {
                ret.add(listing);
            }
        }
        return ret;
    }
}
