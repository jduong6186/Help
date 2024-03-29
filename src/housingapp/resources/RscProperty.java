package housingapp.resources;

import housingapp.housing.Property;
import housingapp.query.ResourceManager;
import housingapp.SysConst;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.UUID;

/**
 * handles JSON read/write functionality for property resources
 */

public class RscProperty {

    /**
     * reads properties from properties.json data file, returns an array list of properties
     */
    public static ArrayList<Property> getProperties() {
        ArrayList<Property> properties = new ArrayList<Property>();
        try {
            // read properties array from JSON file
            FileReader reader = new FileReader(SysConst.PROPERTIES_DATA_FILE);
            JSONParser parser = new JSONParser();
            JSONArray propertiesJSON = (JSONArray) parser.parse(reader);

            // parse individual property objects from JSONArray
            if (propertiesJSON != null) {
                for (int i=0; i<propertiesJSON.size(); i++) {
                    JSONObject propertyJSON = (JSONObject) propertiesJSON.get(i);
                    UUID propertyId = UUID.fromString((String) propertyJSON.get(SysConst.PROPERTY_ID));
                    UUID landlordId = UUID.fromString((String) propertyJSON.get("landlordId"));
                    String name = (String) propertyJSON.get(SysConst.PROPERTY_NAME);
                    String address = (String) propertyJSON.get(SysConst.PROPERTY_ADDRESS);
                    String zipCode = (String) propertyJSON.get("zipCode");
                    double distanceToCampus = (double) propertyJSON.get(SysConst.PROPERTY_DISTANCE_TO_CAMPUS);
                    double damagesCost = (double) propertyJSON.get("damagesCost");
                    boolean furnished = (boolean) propertyJSON.get("furnished");
                    boolean petsAllowed = (boolean) propertyJSON.get("petsAllowed");
                    boolean hasPool = (boolean) propertyJSON.get("hasPool");
                    boolean hasGym = (boolean) propertyJSON.get("hasGym");
                    boolean hasFreeWifi = (boolean) propertyJSON.get("hasFreeWifi");

                    // get rating ids
                    JSONArray ratingsJSON = (JSONArray) propertyJSON.get("ratings");
                    ArrayList<UUID> ratings = new ArrayList<UUID>();
                    if (ratingsJSON != null) {
                        for (int j=0; j<ratingsJSON.size(); j++) {
                            ratings.add(UUID.fromString((String) ratingsJSON.get(j)));
                        }
                    }

                    // get listing ids
                    JSONArray listingsJSON = (JSONArray) propertyJSON.get(SysConst.PROPERTY_LISTINGS);
                    ArrayList<UUID> listings = new ArrayList<UUID>();
                    if (listingsJSON != null) {
                        for (int j=0; j<listingsJSON.size(); j++) {
                            listings.add(UUID.fromString((String) listingsJSON.get(j)));
                        }
                    }

                    // append property to properties
                    properties.add(new Property(propertyId, landlordId, name, address, zipCode, distanceToCampus, damagesCost,
                            furnished, petsAllowed, hasPool, hasGym, hasFreeWifi, ratings, listings));
                }
            }
            return properties;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * writes updated properties from resource manager to properties.json data file
     */
    public static void writeProperties() {
        ResourceManager rm = ResourceManager.getInstance();
        ArrayList<Property> properties = rm.getProperties();
        JSONArray propertiesJSON = new JSONArray();
        if (properties != null) {
            for (int i=0; i<properties.size(); i++) {
                propertiesJSON.add(getPropertyJSON(properties.get(i)));
            }
        }
        try (FileWriter writer = new FileWriter(SysConst.PROPERTIES_DATA_FILE)) {
            writer.write(propertiesJSON.toJSONString());
            writer.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * constructs JSON representation of property
     * @param property instance of a property
     */
    public static JSONObject getPropertyJSON(Property property) {
        // top-level attributes
        JSONObject propertyJSON = new JSONObject();
        UUID propertyId = property.getId();
        if (propertyId != null) {
            propertyJSON.put(SysConst.PROPERTY_ID, propertyId.toString());
        }
        UUID landlordId = property.getLandlordId();
        if (landlordId != null) {
            propertyJSON.put("landlordId", landlordId.toString());
        }
        propertyJSON.put(SysConst.PROPERTY_NAME, property.getName());
        propertyJSON.put(SysConst.PROPERTY_ADDRESS, property.getAddress());
        propertyJSON.put("zipCode", property.getZipCode());
        propertyJSON.put(SysConst.PROPERTY_DISTANCE_TO_CAMPUS, property.getDistanceToCampus());
        propertyJSON.put("damagesCost", property.getDamagesCost());
        propertyJSON.put("furnished", property.isFurnished());
        propertyJSON.put("petsAllowed", property.petsAllowed());
        propertyJSON.put("hasPool", property.hasPool());
        propertyJSON.put("hasGym", property.hasGym());
        propertyJSON.put("hasFreeWifi", property.hasFreeWifi());

        // array of ratings UUIDs
        JSONArray ratingsJSON = new JSONArray();
        ArrayList<UUID> ratings = property.getRatings();
        if (ratings != null) {
            for (int i=0; i<ratings.size(); i++) {
                ratingsJSON.add(ratings.get(i).toString());
            }
        }
        propertyJSON.put("ratings", ratingsJSON);

        // array of listings UUIDs
        JSONArray listingsJSON = new JSONArray();
        ArrayList<UUID> listings = property.getListings();
        if (listings != null) {
            for (int i=0; i<listings.size(); i++) {
                listingsJSON.add(listings.get(i).toString());
            }
        }
        propertyJSON.put(SysConst.PROPERTY_LISTINGS, listingsJSON);

        // return completed JSON obj
        return propertyJSON;
    }
}
