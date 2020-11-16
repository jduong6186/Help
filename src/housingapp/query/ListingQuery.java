package housingapp.query;

import housingapp.housing.Listing;
import housingapp.user.Student;

import java.util.ArrayList;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Singleton class which queries listings using given search params
 * @author Andrew Eldridge, John Michael Falzarano
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

    /**
     * searches for listings based on price range inputed
     * @param listings a list of registered listings
     * @param priceLower the lower price of the price range
     * @param priceUpper the upper price of the price range
     * @return listings that match the search requirements
     */
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

    /**
     * searches for listings based on distance from campus
     * @param listings a list of registered listings
     * @param maxTravelDistance the distance the user is willing to travel to campus
     * @return listings that match the search requirements
     */
    public ArrayList<Listing> getListingsByMaxTravelDistance(ArrayList<Listing> listings, double maxTravelDistance) {
        ArrayList<Listing> ret = new ArrayList<Listing>();
        for (Listing listing : listings) {
            if (maxTravelDistance >= rm.getPropertyById(listing.getPropertyId()).getDistanceToCampus()) {
                ret.add(listing);
            }
        }
        return ret;
    }

    /**
     * searches for listings based on lease month
     * @param listings a list of registered listings
     * @param leaseMonthsLower the min month the user is willing to lease for 
     * @param leaseMonthsUpper the max month the user is willing to lease for 
     * @return listings that match the search requirements
     */
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

    /**
     * searches for the listing based on range of square footage
     * @param listings a list of registered listings
     * @param squareFootageLower the min square footage the user is willing to live in
     * @param squareFootageUpper the max square footage the user is willing to live in
     * @return listings that match the search requirements
     */
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

    /**
     * searches for listings that allow pets
     * @param listings the list of registered listings
     * @param petsAllowed if the user is willing to live with pets
     * @return listings that match the search requirements
     */
    public ArrayList<Listing> getListingsByPetsAllowed(ArrayList<Listing> listings, boolean petsAllowed) {
        ArrayList<Listing> ret = new ArrayList<Listing>();
        for (Listing listing : listings) {
            if (rm.getPropertyById(listing.getPropertyId()).petsAllowed() == petsAllowed) {
                ret.add(listing);
            }
        }
        return ret;
    }

    /**
     * searches for listings that have a washer
     * @param listings the list of registered listings
     * @param hasWasher if the user wants a washer or not
     * @return listings that match the search requirements
     */
    public ArrayList<Listing> getListingsByHasWasher(ArrayList<Listing> listings, boolean hasWasher) {
        ArrayList<Listing> ret = new ArrayList<Listing>();
        for (Listing listing: listings) {
            if (listing.hasWasher() == hasWasher) {
                ret.add(listing);
            }
        }
        return ret;
    }

    /**
     * searches for listings that have a dryer
     * @param listings the list of registered listings
     * @param hasDryer if the user want a dryer or not
     * @return listings that match the search requirements
     */
    public ArrayList<Listing> getListingsByHasDryer(ArrayList<Listing> listings, boolean hasDryer) {
        ArrayList<Listing> ret = new ArrayList<Listing>();
        for (Listing listing: listings) {
            if (listing.hasDryer() == hasDryer) {
                ret.add(listing);
            }
        }
        return ret;
    }

    /**
     * searches for listings available for sublease
     * @param listings the list of registered listings
     * @return listings that match the search requirements
     */
    public ArrayList<Listing> getListingsByIsSublease(ArrayList<Listing> listings) {
        ArrayList<Listing> ret = new ArrayList<Listing>();
        for (Listing listing : listings) {
            if (listing.isSublease()) {
                ret.add(listing);
            }
        }
        return ret;
    }

    /**
     * searches for listings that include utilities in payment
     * @param listings the list of registered listings
     * @param utilitiesIncluded if the user want included utilities
     * @return listings that match the search requirements
     */
    public ArrayList<Listing> getListingsByUtilitiesIncluded(ArrayList<Listing> listings, boolean utilitiesIncluded) {
        ArrayList<Listing> ret = new ArrayList<Listing>();
        for (Listing listing : listings) {
            if (listing.utilitiesIncluded() == utilitiesIncluded) {
                ret.add(listing);
            }
        }
        return ret;
    }

    /**
     * searches for listings that include the number of bedrooms wanted
     * @param listings the list of registered listings
     * @param numBedroomsLower the min number of bedrooms wanted by the user
     * @param numBedroomsUpper the max number of bedrooms wanted by the user
     * @return listings that match the search requirements
     */
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

    /**
     * searched for listings that include the number of bathrooms wanted
     * @param listings the list of registered listings
     * @param numBathroomsLower the min number of bedrooms wanted by the user
     * @param numBathroomsUpper the max number of bedrooms wanted by the user
     * @return listings that match the search requirements
     */
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

    /**
     * searched for listings that have a shuttle or not
     * @param listings the list of registered listings
     * @param hasShuttle if the user wants to have a shuttle available
     * @return listings that match the search requirements
     */
    public ArrayList<Listing> getListingsByHasShuttle(ArrayList<Listing> listings, boolean hasShuttle) {
        ArrayList<Listing> ret = new ArrayList<Listing>();
        for (Listing listing : listings) {
            if (listing.hasShuttle() == hasShuttle) {
                ret.add(listing);
            }
        }
        return ret;
    }

    /**
     * searched for listings that are available
     * @param listings the list of registered listings
     * @return listings that match the search requirements
     */
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
