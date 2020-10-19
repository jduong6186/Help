package housingapp.resources;

import housingapp.ResourceManager;
import housingapp.User;
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
        try {
            // read users array from JSON file
            FileReader reader = new FileReader(SysConst.USERS_DATA_FILE);
            JSONParser parser = new JSONParser();
            JSONArray usersJSON = (JSONArray) parser.parse(reader);

            // parse individual user objects from JSONArray
            for (int i=0; i<usersJSON.size(); i++) {
                JSONObject userJSON = (JSONObject) usersJSON.get(i);
                UUID userId = (UUID) userJSON.get(SysConst.USER_ID);
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

                // append user to users
                users.add(new User(userId, firstName, lastName, phone, email, password, listings));
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
