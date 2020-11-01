package housingapp.resources;

import housingapp.query.ResourceManager;
import housingapp.user.PropertyManager;
import housingapp.user.Student;
import housingapp.user.User;
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

public class RscUser {

    public static Map<String, ArrayList<User>> getUsers() {
        Map<String, ArrayList<User>> users = new HashMap<String, ArrayList<User>>();
        ArrayList<User> students = new ArrayList<User>();
        ArrayList<User> propertyManagers = new ArrayList<User>();
        try {
            // read users array from JSON file
            FileReader reader = new FileReader(SysConst.USERS_DATA_FILE);
            JSONParser parser = new JSONParser();
            JSONObject usersFileJSON = (JSONObject) parser.parse(reader);

            // pull students and property managers lists from file
            JSONArray studentsJSON = (JSONArray) usersFileJSON.get(SysConst.STUDENT_USERS);
            JSONArray propertyManagersJSON = (JSONArray) usersFileJSON.get(SysConst.PROPERTY_MANAGER_USERS);

            // parse students list
            for (int i=0; i<studentsJSON.size(); i++) {
                JSONObject studentJSON = (JSONObject) studentsJSON.get(i);
                UUID userId = UUID.fromString((String) studentJSON.get(SysConst.USER_ID));
                String firstName = (String) studentJSON.get(SysConst.USER_FIRST_NAME);
                String lastName = (String) studentJSON.get(SysConst.USER_LAST_NAME);
                String phone = (String) studentJSON.get(SysConst.USER_PHONE);
                String email = (String) studentJSON.get(SysConst.USER_EMAIL);
                String password = (String) studentJSON.get(SysConst.USER_PASSWORD);

                // student-unique attributes
                boolean hasPets = (boolean) studentJSON.get(SysConst.STUDENT_USER_HAS_PETS);
                double priceRangeLower = (double) studentJSON.get(SysConst.STUDENT_USER_PRICE_RANGE_LOWER);
                double priceRangeUpper = (double) studentJSON.get(SysConst.STUDENT_USER_PRICE_RANGE_UPPER);
                double maxTravelDistance = (double) studentJSON.get(SysConst.STUDENT_USER_MAX_TRAVEL_DISTANCE);
                int minRoommates = ((Long) studentJSON.get(SysConst.STUDENT_USER_MIN_ROOMMATES)).intValue();
                int maxRoommates = ((Long) studentJSON.get(SysConst.STUDENT_USER_MAX_ROOMMATES)).intValue();

                // parse ratings UUID array
                JSONArray ratingsJSON = (JSONArray) studentJSON.get(SysConst.STUDENT_USER_RATINGS);
                ArrayList<UUID> ratings = new ArrayList<UUID>();
                for (int j=0; j<ratingsJSON.size(); j++) {
                    ratings.add((UUID) ratingsJSON.get(j));
                }

                // parse listing favorites UUID array
                JSONArray listingFavoritesJSON = (JSONArray) studentJSON.get(SysConst.STUDENT_USER_LISTING_FAVORITES);
                ArrayList<UUID> listingFavorites = new ArrayList<UUID>();
                for (int j=0; j<listingFavoritesJSON.size(); j++) {
                    listingFavorites.add(UUID.fromString((String) listingFavoritesJSON.get(j)));
                }

                // get listing ids from attribute
                JSONArray listingsJSON = (JSONArray) studentJSON.get(SysConst.USER_LISTINGS);
                ArrayList<UUID> listings = new ArrayList<UUID>();
                for (int j=0; j<listingsJSON.size(); j++) {
                    listings.add(UUID.fromString((String) listingsJSON.get(j)));
                }

                // add student to students list;
                students.add(new Student(userId, firstName, lastName, phone, email, password, hasPets, priceRangeLower,
                        priceRangeUpper, maxTravelDistance, minRoommates, maxRoommates, ratings, listingFavorites, listings));
            }
            users.put(SysConst.STUDENT_USERS, students);

            // parse property managers list
            for (int i=0; i<propertyManagersJSON.size(); i++) {
                JSONObject propertyManagerJSON = (JSONObject) propertyManagersJSON.get(i);
                UUID userId = UUID.fromString((String) propertyManagerJSON.get(SysConst.USER_ID));
                String firstName = (String) propertyManagerJSON.get(SysConst.USER_FIRST_NAME);
                String lastName = (String) propertyManagerJSON.get(SysConst.USER_LAST_NAME);
                String phone = (String) propertyManagerJSON.get(SysConst.USER_PHONE);
                String email = (String) propertyManagerJSON.get(SysConst.USER_EMAIL);
                String password = (String) propertyManagerJSON.get(SysConst.USER_PASSWORD);

                // property manager-unique attributes
                String officeAddress = (String) propertyManagerJSON.get(SysConst.PROPERTY_MANAGER_USER_OFFICE_ADDRESS);

                // get listing ids from attribute
                JSONArray listingsJSON = (JSONArray) propertyManagerJSON.get(SysConst.USER_LISTINGS);
                ArrayList<UUID> listings = new ArrayList<UUID>();
                for (int j=0; j<listingsJSON.size(); j++) {
                    listings.add((UUID) listingsJSON.get(j));
                }

                // parse properties UUID array
                JSONArray propertiesJSON = (JSONArray) propertyManagerJSON.get(SysConst.PROPERTY_MANAGER_USER_PROPERTIES);
                ArrayList<UUID> properties = new ArrayList<UUID>();
                for (int j=0; j<propertiesJSON.size(); j++) {
                    properties.add((UUID) propertiesJSON.get(j));
                }

                // add property manager to property managers list;
                propertyManagers.add(new PropertyManager(userId, firstName, lastName, phone, email, password, officeAddress,
                        listings, properties));
            }
            users.put(SysConst.PROPERTY_MANAGER_USERS, propertyManagers);

            return users;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static void writeUsers() {
        ResourceManager rm = ResourceManager.getInstance();

        // first, populate students JSONArray
        ArrayList<User> students = rm.getStudents();
        JSONArray studentsJSON = new JSONArray();
        for (int i=0; i<students.size(); i++) {
            studentsJSON.add(getStudentJSON((Student) students.get(i)));
        }

        // second, populate propertyManagers JSONArray
        ArrayList<User> propertyManagers = rm.getPropertyManagers();
        JSONArray propertyManagersJSON = new JSONArray();
        for (int i=0; i<propertyManagers.size(); i++) {
            propertyManagersJSON.add(getPropertyManagerJSON((PropertyManager) propertyManagers.get(i)));
        }

        // third, create usersJSON object
        JSONObject usersJSON = new JSONObject();
        usersJSON.put(SysConst.STUDENT_USERS, studentsJSON);
        usersJSON.put(SysConst.PROPERTY_MANAGER_USERS, propertyManagersJSON);

        // last, write to data file
        try (FileWriter writer = new FileWriter(SysConst.USERS_DATA_FILE)) {
            writer.write(usersJSON.toJSONString());
            writer.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static JSONObject getStudentJSON(Student student) {
        // top-level attributes
        JSONObject studentJSON = new JSONObject();
        studentJSON.put(SysConst.USER_ID, student.getId());
        studentJSON.put(SysConst.USER_FIRST_NAME, student.getFirstName());
        studentJSON.put(SysConst.USER_LAST_NAME, student.getLastName());
        studentJSON.put(SysConst.USER_PHONE, student.getPhone());
        studentJSON.put(SysConst.USER_EMAIL, student.getEmail());
        studentJSON.put(SysConst.USER_PASSWORD, student.getPassword());

        // student-unique attributes
        studentJSON.put(SysConst.STUDENT_USER_HAS_PETS, student.hasPets());
        studentJSON.put(SysConst.STUDENT_USER_PRICE_RANGE_LOWER, student.getPriceRangeLower());
        studentJSON.put(SysConst.STUDENT_USER_PRICE_RANGE_UPPER, student.getPriceRangeUpper());
        studentJSON.put(SysConst.STUDENT_USER_MAX_TRAVEL_DISTANCE, student.getMaxTravelDistance());
        studentJSON.put(SysConst.STUDENT_USER_MIN_ROOMMATES, student.getMinRoommates());
        studentJSON.put(SysConst.STUDENT_USER_MAX_ROOMMATES, student.getMaxRoommates());

        // array of rating UUIDs
        JSONArray ratingsJSON = new JSONArray();
        ArrayList<UUID> ratings = student.getRatings();
        for (int i=0; i<ratings.size(); i++) {
            ratingsJSON.add(ratings.get(i));
        }
        studentJSON.put(SysConst.STUDENT_USER_RATINGS, ratings);

        // array of listing favorite UUIDs
        JSONArray listingFavoritesJSON = new JSONArray();
        ArrayList<UUID> listingFavorites = student.getListingFavorites();
        for (int i=0; i<listingFavorites.size(); i++) {
            listingFavoritesJSON.add(listingFavorites.get(i));
        }

        // array of listings UUIDs
        JSONArray listingsJSON = new JSONArray();
        ArrayList<UUID> listings = student.getListings();
        for (int i=0; i<listings.size(); i++) {
            listingsJSON.add(listings.get(i));
        }
        studentJSON.put(SysConst.USER_LISTINGS, listingsJSON);

        // return completed JSON obj
        return studentJSON;
    }

    public static JSONObject getPropertyManagerJSON(PropertyManager propertyManager) {
        // top-level attributes
        JSONObject propertyManagerJSON = new JSONObject();
        propertyManagerJSON.put(SysConst.USER_ID, propertyManager.getId());
        propertyManagerJSON.put(SysConst.USER_FIRST_NAME, propertyManager.getFirstName());
        propertyManagerJSON.put(SysConst.USER_LAST_NAME, propertyManager.getLastName());
        propertyManagerJSON.put(SysConst.USER_PHONE, propertyManager.getPhone());
        propertyManagerJSON.put(SysConst.USER_EMAIL, propertyManager.getEmail());
        propertyManagerJSON.put(SysConst.USER_PASSWORD, propertyManager.getPassword());

        // property manager-unique attributes
        propertyManagerJSON.put(SysConst.PROPERTY_MANAGER_USER_OFFICE_ADDRESS, propertyManager.getOfficeAddress());

        // array of property UUIDs
        JSONArray propertiesJSON = new JSONArray();
        ArrayList<UUID> properties = propertyManager.getProperties();
        for (int i=0; i<properties.size(); i++) {
            propertiesJSON.add(properties.get(i));
        }
        propertyManagerJSON.put(SysConst.PROPERTY_MANAGER_USER_PROPERTIES, properties);

        // array of listings UUIDs
        JSONArray listingsJSON = new JSONArray();
        ArrayList<UUID> listings = propertyManager.getListings();
        for (int i=0; i<listings.size(); i++) {
            listingsJSON.add(listings.get(i));
        }
        propertyManagerJSON.put(SysConst.USER_LISTINGS, listingsJSON);

        // return completed JSON obj
        return propertyManagerJSON;
    }
}
