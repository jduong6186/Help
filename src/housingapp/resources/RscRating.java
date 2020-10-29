package housingapp.resources;

import housingapp.query.ResourceManager;
import housingapp.rating.Rating;
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

public class RscRating {

    public static ArrayList<Rating> getRatings() {
        ArrayList<Rating> ratings = new ArrayList<Rating>();
        try {
            // read ratings array from JSON file
            FileReader reader = new FileReader(SysConst.RATINGS_DATA_FILE);
            JSONParser parser = new JSONParser();
            JSONArray ratingsJSON = (JSONArray) parser.parse(reader);

            // parse individual rating objects from JSONArray
            for (int i=0; i<ratingsJSON.size(); i++) {
                JSONObject ratingJSON = (JSONObject) ratingsJSON.get(i);
                UUID ratingId = (UUID) ratingJSON.get(SysConst.RATING_ID);
                int stars = (int) ratingJSON.get(SysConst.RATING_STARS);
                String comment = (String) ratingJSON.get(SysConst.RATING_COMMENT);
                int valueStars = (int) ratingJSON.get(SysConst.RATING_VALUE_STARS);
                int managementStars = (int) ratingJSON.get(SysConst.RATING_MANAGEMENT_STARS);
                int neighborhoodStars = (int) ratingJSON.get(SysConst.RATING_NEIGHBORHOOD_STARS);

                // append rating to ratings
                ratings.add(new User(userId, firstName, lastName, phone, email, password, listings));
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
        JSONArray ratingsJSON = new JSONArray();
        for (int i=0; i<users.size(); i++) {
            ratingsJSON.add(getratingJSON(users.get(i)));
        }
        try (FileWriter writer = new FileWriter(SysConst.USERS_DATA_FILE)) {
            writer.write(ratingsJSON.toJSONString());
            writer.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static JSONObject getratingJSON(User user) {
        // top-level attributes
        JSONObject ratingJSON = new JSONObject();
        ratingJSON.put(SysConst.USER_ID, user.getId());
        ratingJSON.put(SysConst.USER_FIRST_NAME, user.getFirstName());
        ratingJSON.put(SysConst.USER_LAST_NAME, user.getLastName());
        ratingJSON.put(SysConst.USER_PHONE, user.getPhone());
        ratingJSON.put(SysConst.USER_EMAIL, user.getEmail());
        ratingJSON.put(SysConst.USER_PASSWORD, user.getPassword());

        // array of listings UUIDs
        JSONArray listingsJSON = new JSONArray();
        ArrayList<UUID> listings = user.getListings();
        for (int i=0; i<listings.size(); i++) {
            listingsJSON.add(listings.get(i));
        }
        ratingJSON.put(SysConst.USER_LISTINGS, listingsJSON);

        // return completed JSON obj
        return ratingJSON;
    }
}
