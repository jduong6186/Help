package housingapp.resources;

import housingapp.housing.Apartment;
import housingapp.housing.Listing;
import housingapp.housing.Townhouse;
import housingapp.query.ResourceManager;
import housingapp.SysConst;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * handles JSON read/write functionality for listing resources
 */

public class RscListing {

    /**
     * reads listings from listings.json data file, returns a map of listingType:listings
     */
    public static Map<String, ArrayList<Listing>> getListings() {
        Map<String, ArrayList<Listing>> listings = new HashMap<String, ArrayList<Listing>>();
        ArrayList<Listing> apartments = new ArrayList<Listing>();
        ArrayList<Listing> townhouses = new ArrayList<Listing>();
        try {
            // read listings arrays from JSON file
            FileReader reader = new FileReader(SysConst.LISTINGS_DATA_FILE);
            JSONParser parser = new JSONParser();
            JSONObject listingsJSON = (JSONObject) parser.parse(reader);

            // pull apartment and townhouse JSON from listings JSON
            JSONArray apartmentsJSON = (JSONArray) listingsJSON.get("apartments");
            JSONArray townhousesJSON = (JSONArray) listingsJSON.get("townhouses");

            // parse apartments JSON
            if (apartmentsJSON != null) {
                for (int i=0; i<apartmentsJSON.size(); i++) {
                    JSONObject listingJSON = (JSONObject) apartmentsJSON.get(i);
                    UUID listingId = UUID.fromString((String) listingJSON.get(SysConst.LISTING_ID));
                    UUID propertyId = UUID.fromString((String) listingJSON.get(SysConst.LISTING_PROPERTY_ID));
                    String description = (String) listingJSON.get(SysConst.LISTING_DESCRIPTION);
                    double price = (double) listingJSON.get(SysConst.LISTING_PRICE);
                    int leaseMonths = ((Long) listingJSON.get(SysConst.LISTING_LEASE_MONTHS)).intValue();
                    double squareFootage = (double) listingJSON.get(SysConst.LISTING_SQUARE_FOOTAGE);
                    boolean isSublease = (boolean) listingJSON.get(SysConst.LISTING_IS_SUBLEASE);
                    boolean utilitiesIncluded = (boolean) listingJSON.get(SysConst.LISTING_UTILITIES_INCLUDED);
                    int numBedrooms = ((Long) listingJSON.get(SysConst.LISTING_NUM_BEDROOMS)).intValue();
                    int numBathrooms = ((Long) listingJSON.get(SysConst.LISTING_NUM_BATHROOMS)).intValue();
                    boolean hasShuttle = (boolean) listingJSON.get(SysConst.LISTING_HAS_SHUTTLE);
                    boolean available = (boolean) listingJSON.get(SysConst.LISTING_AVAILABLE);
                    boolean hasWasher = (boolean) listingJSON.get("hasWasher");
                    boolean hasDryer = (boolean) listingJSON.get("hasDryer");

                    // apartment-unique attributes
                    String apartmentNumber = (String) listingJSON.get("apartmentNumber");
                    boolean hasParking = (boolean) listingJSON.get("hasParking");

                    apartments.add(new Apartment(listingId, propertyId, description, price, leaseMonths, squareFootage,
                            isSublease, utilitiesIncluded, numBedrooms, numBathrooms, hasShuttle, available, hasWasher, hasDryer,
                            apartmentNumber, hasParking));
                }
            }
            listings.put("apartments", apartments);

            // parse townhouses JSON
            if (townhousesJSON != null) {
                for (int i=0; i<townhousesJSON.size(); i++) {
                    JSONObject listingJSON = (JSONObject) townhousesJSON.get(i);
                    UUID listingId = UUID.fromString((String) listingJSON.get(SysConst.LISTING_ID));
                    UUID propertyId = UUID.fromString((String) listingJSON.get(SysConst.LISTING_PROPERTY_ID));
                    String description = (String) listingJSON.get(SysConst.LISTING_DESCRIPTION);
                    double price = (double) listingJSON.get(SysConst.LISTING_PRICE);
                    int leaseMonths = ((Long) listingJSON.get(SysConst.LISTING_LEASE_MONTHS)).intValue();
                    double squareFootage = (double) listingJSON.get(SysConst.LISTING_SQUARE_FOOTAGE);
                    boolean isSublease = (boolean) listingJSON.get(SysConst.LISTING_IS_SUBLEASE);
                    boolean utilitiesIncluded = (boolean) listingJSON.get(SysConst.LISTING_UTILITIES_INCLUDED);
                    int numBedrooms = ((Long) listingJSON.get(SysConst.LISTING_NUM_BEDROOMS)).intValue();
                    int numBathrooms = ((Long) listingJSON.get(SysConst.LISTING_NUM_BATHROOMS)).intValue();
                    boolean hasShuttle = (boolean) listingJSON.get(SysConst.LISTING_HAS_SHUTTLE);
                    boolean available = (boolean) listingJSON.get(SysConst.LISTING_AVAILABLE);
                    boolean hasWasher = (boolean) listingJSON.get("hasWasher");
                    boolean hasDryer = (boolean) listingJSON.get("hasDryer");

                    // townhouse-unique attributes
                    boolean hasGarage = (boolean) listingJSON.get("hasGarage");
                    boolean hasDriveway = (boolean) listingJSON.get("hasDriveway");
                    boolean hasYard = (boolean) listingJSON.get("hasYard");
                    boolean hasFence = (boolean) listingJSON.get("hasFence");

                    townhouses.add(new Townhouse(listingId, propertyId, description, price, leaseMonths, squareFootage,
                            isSublease, utilitiesIncluded, numBedrooms, numBathrooms, hasShuttle, available, hasWasher, hasDryer,
                            hasGarage, hasDriveway, hasYard, hasFence));
                }
            }
            listings.put("townhouses", townhouses);

            return listings;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * writes updated listings from resource manager to listings.json data file
     */
    public static void writeListings() {
        ResourceManager rm = ResourceManager.getInstance();
        ArrayList<Listing> apartments = rm.getApartments();
        ArrayList<Listing> townhouses = rm.getTownhouses();

        // create apartments JSON
        JSONArray apartmentsJSON = new JSONArray();
        if (apartments != null) {
            for (Listing apartment : apartments) {
                apartmentsJSON.add(getApartmentJSON((Apartment) apartment));
            }
        }

        // create townhouses JSON
        JSONArray townhousesJSON = new JSONArray();
        if (townhouses != null) {
            for (Listing townhouse : townhouses) {
                townhousesJSON.add(getTownhouseJSON((Townhouse) townhouse));
            }
        }

        // create outer listings JSON obj
        JSONObject listingsJSON = new JSONObject();
        listingsJSON.put("apartments", apartmentsJSON);
        listingsJSON.put("townhouses", townhousesJSON);

        // write to data file
        try (FileWriter writer = new FileWriter(SysConst.LISTINGS_DATA_FILE)) {
            writer.write(listingsJSON.toJSONString());
            writer.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * constructs JSON representation of apartment
     * @param listing instance of apartment listing
     */
    public static JSONObject getApartmentJSON(Apartment listing) {
        JSONObject listingJSON = new JSONObject();
        listingJSON.put(SysConst.LISTING_ID, listing.getId().toString());
        listingJSON.put(SysConst.LISTING_PROPERTY_ID, listing.getPropertyId().toString());
        listingJSON.put(SysConst.LISTING_DESCRIPTION, listing.getDescription());
        listingJSON.put(SysConst.LISTING_PRICE, listing.getPrice());
        listingJSON.put(SysConst.LISTING_LEASE_MONTHS, listing.getLeaseMonths());
        listingJSON.put(SysConst.LISTING_SQUARE_FOOTAGE, listing.getSquareFootage());
        listingJSON.put(SysConst.LISTING_IS_SUBLEASE, listing.isSublease());
        listingJSON.put(SysConst.LISTING_UTILITIES_INCLUDED, listing.utilitiesIncluded());
        listingJSON.put(SysConst.LISTING_NUM_BEDROOMS, listing.getNumBedrooms());
        listingJSON.put(SysConst.LISTING_NUM_BATHROOMS, listing.getNumBathrooms());
        listingJSON.put(SysConst.LISTING_HAS_SHUTTLE, listing.hasShuttle());
        listingJSON.put(SysConst.LISTING_AVAILABLE, listing.isAvailable());
        listingJSON.put("hasWasher", listing.hasWasher());
        listingJSON.put("hasDryer", listing.hasDryer());

        // apartment-unique attributes
        listingJSON.put("apartmentNumber", listing.getApartmentNumber());
        listingJSON.put("hasParking", listing.hasParking());

        return listingJSON;
    }

    /**
     * constructs JSON representation of townhouse
     * @param listing instance of townhouse listing
     */
    public static JSONObject getTownhouseJSON(Townhouse listing) {
        JSONObject listingJSON = new JSONObject();
        listingJSON.put(SysConst.LISTING_ID, listing.getId().toString());
        listingJSON.put(SysConst.LISTING_PROPERTY_ID, listing.getPropertyId().toString());
        listingJSON.put(SysConst.LISTING_DESCRIPTION, listing.getDescription());
        listingJSON.put(SysConst.LISTING_PRICE, listing.getPrice());
        listingJSON.put(SysConst.LISTING_LEASE_MONTHS, listing.getLeaseMonths());
        listingJSON.put(SysConst.LISTING_SQUARE_FOOTAGE, listing.getSquareFootage());
        listingJSON.put(SysConst.LISTING_IS_SUBLEASE, listing.isSublease());
        listingJSON.put(SysConst.LISTING_UTILITIES_INCLUDED, listing.utilitiesIncluded());
        listingJSON.put(SysConst.LISTING_NUM_BEDROOMS, listing.getNumBedrooms());
        listingJSON.put(SysConst.LISTING_NUM_BATHROOMS, listing.getNumBathrooms());
        listingJSON.put(SysConst.LISTING_HAS_SHUTTLE, listing.hasShuttle());
        listingJSON.put(SysConst.LISTING_AVAILABLE, listing.isAvailable());
        listingJSON.put("hasWasher", listing.hasWasher());
        listingJSON.put("hasDryer", listing.hasDryer());

        // townhouse-unique attributes
        listingJSON.put("hasGarage", listing.hasGarage());
        listingJSON.put("hasDriveway", listing.hasDriveway());
        listingJSON.put("hasYard", listing.hasYard());
        listingJSON.put("hasFence", listing.hasFence());

        return listingJSON;
    }
}
