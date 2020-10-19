package housingapp;

import java.util.ArrayList;
import java.util.Map;
import java.util.HashMap;
import java.util.UUID;
import at.favre.lib.crypto.bcrypt.BCrypt;
import housingapp.resources.*;

/**
 * Singleton class for all resource management. Interfaces with JSON reader/writer classes for pulling and storing housingapp.data
 * @author Andrew Eldridge
 */

public class ResourceManager {

    private static ResourceManager instance;
    private ArrayList<User> users;
    private ArrayList<Property> properties;
    private ArrayList<Listing> listings;
    private ArrayList<Session> sessions;
    private Map<String, User> userMap;
    private Map<UUID, Session> sessionMap;

    public ResourceManager() {
        this.users = RscUser.getUsers();
        this.properties = RscProperty.getProperties();
        this.listings = RscListing.getListings();
        this.sessions = RscSession.getSessions();

        // populate user and session maps with initial values
        for (int i=0; i<this.users.size(); i++) {
            userMap.put(users.get(i).getEmail(), users.get(i));
        }
        for (int i=0; i<this.sessions.size(); i++) {
            sessionMap.put(sessions.get(i).getToken(), sessions.get(i));
        }
    }

    public static ResourceManager getInstance() {
        if (instance == null) {
            instance = new ResourceManager();
        }
        return instance;
    }

    public ArrayList<User> getUsers() {
        return users;
    }

    public ArrayList<Property> getProperties() {
        return properties;
    }

    public ArrayList<Listing> getListings() {
        return listings;
    }

    public ArrayList<Session> getSessions() {
        return sessions;
    }

    public boolean validateSession(UUID token, UUID userId) {
        for (int i=0 ;i<sessions.size(); i++) {
            Session currSession = sessions.get(i);
            if (currSession.getToken().equals(token) && currSession.getUserId().equals(userId)) {
                return true;
            }
        }
        return false;
    }

    protected void addUser(User user) {
        users.add(user);
        userMap.put(user.getEmail(), user);
        RscUser.writeUsers();
    }

    protected void addProperty(Property property) {
        properties.add(property);
        RscProperty.writeProperties();
    }

    protected void addListing(Listing listing) {
        listings.add(listing);
        RscListing.writeListings();
    }

    protected Session login(String email, String password) {
        for (int i=0; i<users.size(); i++) {
            User currUser = users.get(i);
            if (currUser.getEmail().equals(email)) {
                if (BCrypt.verifyer().verify(password.toCharArray(), currUser.getPassword()).verified) {
                    Session newSession = new Session(userMap.get(email).getId());
                    addSession(newSession);
                    return newSession;
                } else {
                    System.out.println("Invalid login attempt.");
                    return null;
                }
            }
        }
        return null;
    }

    private void addSession(Session session) {
        sessions.add(session);
        sessionMap.put(session.getToken(), session);
        RscSession.writeSessions();
    }
}
