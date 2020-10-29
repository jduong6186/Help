package housingapp.resources;

import housingapp.query.ResourceManager;
import housingapp.user.PropertyManager;
import housingapp.user.Student;
import housingapp.user.User;
import housingapp.system.SysConst;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.UUID;

public class RscUser {

    public static ArrayList<User> getUsers() {
        ArrayList<User> users = new ArrayList<User>();
        ArrayList<Student> students = new ArrayList<Student>();
        ArrayList<PropertyManager> propertyManagers = new ArrayList<PropertyManager>();
        try {
            // read users array from JSON file
            FileReader reader = new FileReader(SysConst.USERS_DATA_FILE);
            JSONParser parser = new JSONParser();
            JSONObject usersFileJSON = (JSONObject) parser.parse(reader);

            // pull students and property managers lists from file
            JSONArray studentsJSON = (JSONArray) usersFileJSON.get(SysConst.STUDENT_USERS);
            JSONArray propertyManagersJOSN = (JSONArray) usersFileJSON.get(SysConst.PROPERTY_MANAGER_USERS);

            // parse students list
            for (int i=0; i<studentsJSON.size(); i++) {
                JSONObject studentJSON = (JSONObject) studentsJSON.get(i);
                UUID userId = (UUID) studentJSON.get(SysConst.USER_ID);
                String typeStr = (String) studentJSON.get(SysConst.USER_TYPE);
                String firstName = (String) studentJSON.get(SysConst.USER_FIRST_NAME);
                String lastName = (String) studentJSON.get(SysConst.USER_LAST_NAME);
                String phone = (String) studentJSON.get(SysConst.USER_PHONE);
                String email = (String) studentJSON.get(SysConst.USER_EMAIL);
                String password = (String) studentJSON.get(SysConst.USER_PASSWORD);

                // get listing ids from attribute
                JSONArray listingsJSON = (JSONArray) studentJSON.get(SysConst.USER_LISTINGS);
                ArrayList<UUID> listings = new ArrayList<UUID>();
                for (int j=0; j<listingsJSON.size(); j++) {
                    listings.add((UUID) listingsJSON.get(j));
                }

                // add student to students list
                User newUser;
                if (typeStr.equalsIgnoreCase("student")) {
                    // get student base attributes
                    boolean hasPets = (boolean) userJSON.get(SysConst.STUDENT_USER_HAS_PETS);
                    double priceRangeLower = (double) userJSON.get(SysConst.STUDENT_USER_PRICE_RANGE_LOWER);
                    double priceRangeUpper = (double) userJSON.get(SysConst.STUDENT_USER_PRICE_RANGE_UPPER);
                    double maxTravelDistance = (double) userJSON.get(SysConst.STUDENT_USER_MAX_TRAVEL_DISTANCE);
                    int minRoommates = (int) userJSON.get(SysConst.STUDENT_USER_MIN_ROOMMATES);
                    int maxRoommates = (int) userJSON.get(SysConst.STUDENT_USER_MAX_ROOMMATES);

                    // parse ratings UUID array
                    JSONArray ratingsJSON = (JSONArray) userJSON.get(SysConst.STUDENT_USER_RATINGS);
                    ArrayList<UUID> ratings = new ArrayList<UUID>();
                    for (int j=0; j<ratingsJSON.size(); j++) {
                        ratings.add((UUID) ratingsJSON.get(j));
                    }

                    // create student obj
                    newUser = new Student(userId, firstName, lastName, phone, email, password, hasPets, priceRangeLower,
                            priceRangeUpper, maxTravelDistance, minRoommates, maxRoommates, ratings, listings);
                } else {
                    // get property manager base attributes
                    String officeAddress = (String) userJSON.get(SysConst.PROPERTY_MANAGER_USER_OFFICE_ADDRESS);

                    // parse properties UUID array
                    JSONArray propertiesJSON = (JSONArray) userJSON.get(SysConst.PROPERTY_MANAGER_USER_PROPERTIES);
                    ArrayList<UUID> properties = new ArrayList<UUID>();
                    for (int j=0; j<propertiesJSON.size(); j++) {
                        properties.add((UUID) propertiesJSON.get(j));
                    }

                    // create property manager obj
                    newUser = new PropertyManager(userId, firstName, lastName, phone, email, password, officeAddress,
                            listings, properties);
                }
                users.add(newUser);
            }

            // parse property managers list

            JSONArray usersJSON = (JSONArray) parser.parse(reader);

            // parse individual user objects from JSONArray
            for (int i=0; i<usersJSON.size(); i++) {
                JSONObject userJSON = (JSONObject) usersJSON.get(i);
                UUID userId = (UUID) userJSON.get(SysConst.USER_ID);
                String typeStr = (String) userJSON.get(SysConst.USER_TYPE);
                String firstName = (String) userJSON.get(SysConst.USER_FIRST_NAME);
                String lastName = (String) userJSON.get(SysConst.USER_LAST_NAME);
                String phone = (String) userJSON.get(SysConst.USER_PHONE);
                String email = (String) userJSON.get(SysConst.USER_EMAIL);
                String password = (String) userJSON.get(SysConst.USER_PASSWORD);

                // get listing ids from attribute
                JSONArray listingsJSON = (JSONArray) userJSON.get(SysConst.USER_LISTINGS);
                ArrayList<UUID> listings = new ArrayList<UUID>();
                for (int j=0; j<listingsJSON.size(); j++) {
                    listings.add((UUID) listingsJSON.get(j));
                }

                // add user of given type to users list
                User newUser;
                if (typeStr.equalsIgnoreCase("student")) {
                    // get student base attributes
                    boolean hasPets = (boolean) userJSON.get(SysConst.STUDENT_USER_HAS_PETS);
                    double priceRangeLower = (double) userJSON.get(SysConst.STUDENT_USER_PRICE_RANGE_LOWER);
                    double priceRangeUpper = (double) userJSON.get(SysConst.STUDENT_USER_PRICE_RANGE_UPPER);
                    double maxTravelDistance = (double) userJSON.get(SysConst.STUDENT_USER_MAX_TRAVEL_DISTANCE);
                    int minRoommates = (int) userJSON.get(SysConst.STUDENT_USER_MIN_ROOMMATES);
                    int maxRoommates = (int) userJSON.get(SysConst.STUDENT_USER_MAX_ROOMMATES);

                    // parse ratings UUID array
                    JSONArray ratingsJSON = (JSONArray) userJSON.get(SysConst.STUDENT_USER_RATINGS);
                    ArrayList<UUID> ratings = new ArrayList<UUID>();
                    for (int j=0; j<ratingsJSON.size(); j++) {
                        ratings.add((UUID) ratingsJSON.get(j));
                    }

                    // create student obj
                    newUser = new Student(userId, firstName, lastName, phone, email, password, hasPets, priceRangeLower,
                            priceRangeUpper, maxTravelDistance, minRoommates, maxRoommates, ratings, listings);
                } else {
                    // get property manager base attributes
                    String officeAddress = (String) userJSON.get(SysConst.PROPERTY_MANAGER_USER_OFFICE_ADDRESS);

                    // parse properties UUID array
                    JSONArray propertiesJSON = (JSONArray) userJSON.get(SysConst.PROPERTY_MANAGER_USER_PROPERTIES);
                    ArrayList<UUID> properties = new ArrayList<UUID>();
                    for (int j=0; j<propertiesJSON.size(); j++) {
                        properties.add((UUID) propertiesJSON.get(j));
                    }

                    // create property manager obj
                    newUser = new PropertyManager(userId, firstName, lastName, phone, email, password, officeAddress,
                            listings, properties);
                }
                users.add(newUser);
            }
            return users;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static void writeUsers() {
        ResourceManager rm = ResourceManager.getInstance();
        ArrayList<User> users = rm.getUsers();
        JSONArray usersJSON = new JSONArray();
        for (int i=0; i<users.size(); i++) {
            usersJSON.add(getUserJSON(users.get(i)));
        }
        try (FileWriter writer = new FileWriter(SysConst.USERS_DATA_FILE)) {
            writer.write(usersJSON.toJSONString());
            writer.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // todo: separate into getStudentJSON and getPropertyManagerJSON
    public static JSONObject getUserJSON(User user) {
        // top-level attributes
        JSONObject userJSON = new JSONObject();
        userJSON.put(SysConst.USER_ID, user.getId());
        userJSON.put(SysConst.USER_FIRST_NAME, user.getFirstName());
        userJSON.put(SysConst.USER_LAST_NAME, user.getLastName());
        userJSON.put(SysConst.USER_PHONE, user.getPhone());
        userJSON.put(SysConst.USER_EMAIL, user.getEmail());
        userJSON.put(SysConst.USER_PASSWORD, user.getPassword());

        // array of listings UUIDs
        JSONArray listingsJSON = new JSONArray();
        ArrayList<UUID> listings = user.getListings();
        for (int i=0; i<listings.size(); i++) {
            listingsJSON.add(listings.get(i));
        }
        userJSON.put(SysConst.USER_LISTINGS, listingsJSON);

        // return completed JSON obj
        return userJSON;
    }
}
