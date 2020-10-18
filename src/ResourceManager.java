import java.util.ArrayList;
import java.util.Map;
import java.util.HashMap;

/**
 * Singleton class for all resource management. Interfaces with JSON reader/writer classes for pulling and storing data
 * @author Andrew Eldridge
 */

public class ResourceManager {

    private ResourceManager instance;
    private ArrayList<User> users;
    private ArrayList<Property> properties;
    private ArrayList<Listing> listings;
    private Map<String, String> sessions;
    private Map<String,User> userMap;

    public ResourceManager() {
        this.users = RscUser.getUsers();
        this.properties = RscProperty.getProperties();
        this.listings = RscListing.getListings();
        this.sessions = RscSession.getSessions();
        this.userMap = new HashMap<String, User>();
    }

    protected ResourceManager getInstance() {
        if (this.instance == null) {
            this.instance = new ResourceManager();
        }
        return this.instance;
    }

    protected ArrayList<User> getUsers() {
        return users;
    }

    protected ArrayList<Property> getProperties() {
        return properties;
    }

    protected ArrayList<Listing> getListings() {
        return listings;
    }

    protected boolean validateSession(String token) {
        return sessions.containsKey(token);
    }

    protected void addUser(User user) {
        users.add(user);
        RscUser.writeUser(user);
    }

    protected void addProperty(Property property) {
        properties.add(property);
        RscProperty.writeProperty(property);
    }

    protected void addListing(Listing listing) {
        listings.add(listing);
        RscListing.writeListing(listing);
    }

    protected Session login(String email, String password) {
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

    protected void addSession(Session session) {
        sessions.put(session.getToken(), session.getUser().getEmail());
    }
}
