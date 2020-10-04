import java.util.Map;
import java.util.HashMap;

public class ResourceManager {

    // default values while not pulling from db
    private static final User[] DEFAULT_USERS = new User[] {
            new User("Andrew Eldridge", "8033708127", "eldridga@email.sc.edu", "hashedPass"),
            new User("Test User", "9999999999", "test@gmail.com", "hashedPass")
    };
    private static final Property[] DEFAULT_PROPERTIES = new Property[] {
            new Property("Cayce Cove", "215 Spencer Place", 10),
            new Property("Redtail on the River", "500 Alexander Road", 9.5)
    };
    private static final Listing[] DEFAULT_LISTINGS = new Listing[] {};

    private static User[] users = DEFAULT_USERS;
    private static HashMap<String,User> userMap = new HashMap<String,User>();
    private static Property[] properties = DEFAULT_PROPERTIES;
    private static Listing[] listings = DEFAULT_LISTINGS;
    private static Map<String, String> sessions = new HashMap<String,String>();

    static {
        for (int i=0; i<DEFAULT_USERS.length; i++) {
            userMap.put(DEFAULT_USERS[i].getEmail(), DEFAULT_USERS[i]);
        }
    }

    protected static User[] getUsers() {
        return users;
    }

    protected static Property[] getProperties() {
        return properties;
    }

    protected static Listing[] getListings() {
        return listings;
    }

    protected static boolean validateSession(String token) {
        return sessions.containsKey(token);
    }

    protected static void addUser(User user) {
        if (users[users.length-1] != null) {
            users = growUsers(users);
        }
        for (int i=0; i<users.length; i++) {
            if (users[i] == null) {
                users[i] = user;
                return;
            }
        }
    }

    protected static void addProperty(Property property) {
        if (properties[properties.length-1] != null) {
            properties = growProperties(properties);
        }
        for (int i=0; i<properties.length; i++) {
            if (properties[i] == null) {
                properties[i] = property;
                return;
            }
        }
    }

    protected static void addListing(Listing listing) {
        if (listings[listings.length-1] != null) {
            listings = growListings(listings);
        }
        for (int i=0; i<listings.length; i++) {
            if (listings[i] == null) {
                listings[i] = listing;
                return;
            }
        }
    }

    protected static Session login(String email, String password) {
        boolean loginValid = userMap.get(email).validateLogin(email, password);
        if (loginValid) {
            Session newSession = new Session(userMap.get(email));
            addSession(newSession);
            return newSession;
        } else {
            System.out.println("Invalid login attempt.");
            return null;
        }
    }

    protected static void addSession(Session session) {
        sessions.put(session.getToken(), session.getUser().getEmail());
    }

    private static User[] growUsers(User[] users) {
        User[] newUsers = new User[users.length*2];
        for (int i=0; i<users.length; i++) {
            newUsers[i] = users[i];
        }
        return newUsers;
    }

    private static Property[] growProperties(Property[] properties) {
        Property[] newProperties = new Property[properties.length*2];
        for (int i=0; i<properties.length; i++) {
            newProperties[i] = properties[i];
        }
        return newProperties;
    }

    private static Listing[] growListings(Listing[] listings) {
        Listing[] newListings = new Listing[listings.length*2];
        for (int i=0; i<listings.length; i++) {
            newListings[i] = listings[i];
        }
        return newListings;
    }
}
