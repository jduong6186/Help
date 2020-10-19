package housingapp.resources;

import housingapp.Listing;
import housingapp.Property;
import housingapp.ResourceManager;
import housingapp.system.SysConst;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.UUID;

public class RscListing {

    public static ArrayList<Listing> getListings() {
        ArrayList<Listing> listings = new ArrayList<Listing>();
        try {
            // read users array from JSON file
            FileReader reader = new FileReader(SysConst.LISTINGS_DATA_FILE);
            JSONParser parser = new JSONParser();
            JSONArray listingsJSON = (JSONArray) parser.parse(reader);

            // parse individual listing objects from JSONArray
            for (int j=0; j<listingsJSON.size(); j++) {
                JSONObject listingJSON = (JSONObject) listingsJSON.get(j);
                UUID listingId = (UUID) listingJSON.get(SysConst.LISTING_ID);
                Property property = (Property) listingJSON.get(SysConst.LISTING_PROPERTY);
                String description = (String) listingJSON.get(SysConst.LISTING_DESCRIPTION);
                double price = (double) listingJSON.get(SysConst.LISTING_PRICE);
                int leaseMonths = (int) listingJSON.get(SysConst.LISTING_LEASE_MONTHS);
                double squareFootage = (double) listingJSON.get(SysConst.LISTING_SQUARE_FOOTAGE);
                boolean petsAllowed = (boolean) listingJSON.get(SysConst.LISTING_PETS_ALLOWED);
                boolean hasGas = (boolean) listingJSON.get(SysConst.LISTING_HAS_GAS);
                boolean hasElectric = (boolean) listingJSON.get(SysConst.LISTING_HAS_ELECTRIC);
                boolean hasWater = (boolean) listingJSON.get(SysConst.LISTING_HAS_WATER);
                boolean hasTrash = (boolean) listingJSON.get(SysConst.LISTING_HAS_TRASH);
                boolean isSublease = (boolean) listingJSON.get(SysConst.LISTING_IS_SUBLEASE);
                boolean utilitiesIncluded = (boolean) listingJSON.get(SysConst.LISTING_UTILITIES_INCLUDED);
                int numBedrooms = (int) listingJSON.get(SysConst.LISTING_NUM_BEDROOMS);
                int numBathrooms = (int) listingJSON.get(SysConst.LISTING_NUM_BATHROOMS);
                boolean hasShuttle = (boolean) listingJSON.get(SysConst.LISTING_HAS_SHUTTLE);
                boolean available = (boolean) listingJSON.get(SysConst.LISTING_AVAILABLE);
                listings.add(new Listing(listingId, property, description, price, leaseMonths, squareFootage,
                        petsAllowed, hasGas, hasElectric, hasWater, hasTrash, isSublease, utilitiesIncluded,
                        numBedrooms, numBathrooms, hasShuttle, available));
            }
            return listings;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static void writeListings() {
        ResourceManager rm = ResourceManager.getInstance();
        ArrayList<Listing> listings = rm.getListings();
        JSONArray listingsJSON = new JSONArray();
        for (int i=0; i<listings.size(); i++) {
            listingsJSON.add(getListingJSON(listings.get(i)));
        }
        try (FileWriter writer = new FileWriter(SysConst.LISTINGS_DATA_FILE)) {
            writer.write(listingsJSON.toJSONString());
            writer.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static JSONObject getListingJSON(Listing listing) {
        JSONObject listingJSON = new JSONObject();
        listingJSON.put(SysConst.LISTING_ID, listing.getId());
        listingJSON.put(SysConst.LISTING_PROPERTY, listing.getProperty());
        listingJSON.put(SysConst.LISTING_DESCRIPTION, listing.getDescription());
        listingJSON.put(SysConst.LISTING_PRICE, listing.getPrice());
        listingJSON.put(SysConst.LISTING_LEASE_MONTHS, listing.getLeaseMonths());
        listingJSON.put(SysConst.LISTING_SQUARE_FOOTAGE, listing.getSquareFootage());
        listingJSON.put(SysConst.LISTING_PETS_ALLOWED, listing.petsAllowed());
        listingJSON.put(SysConst.LISTING_HAS_GAS, listing.hasGas());
        listingJSON.put(SysConst.LISTING_HAS_ELECTRIC, listing.hasElectric());
        listingJSON.put(SysConst.LISTING_HAS_WATER, listing.hasWater());
        listingJSON.put(SysConst.LISTING_HAS_TRASH, listing.hasTrash());
        listingJSON.put(SysConst.LISTING_IS_SUBLEASE, listing.isSublease());
        listingJSON.put(SysConst.LISTING_UTILITIES_INCLUDED, listing.utilitiesIncluded());
        listingJSON.put(SysConst.LISTING_NUM_BEDROOMS, listing.getNumBedrooms());
        listingJSON.put(SysConst.LISTING_NUM_BATHROOMS, listing.getNumBathrooms());
        listingJSON.put(SysConst.LISTING_HAS_SHUTTLE, listing.hasShuttle());
        listingJSON.put(SysConst.LISTING_AVAILABLE, listing.isAvailable());
        return listingJSON;
    }
}
