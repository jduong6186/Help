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
        this.userMap = new HashMap<String, User>();
        this.sessionMap = new HashMap<UUID, Session>();

        // populate user and session maps with initial values
        if (users != null) {
            for (User user : users) {
                userMap.put(user.getEmail(), user);
            }
        }
        if (sessions != null) {
            for (Session session : sessions) {
                sessionMap.put(session.getToken(), session);
            }
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

    public User getUserById(UUID userId) {
        for (User user : users) {
            if (user.getId().equals(userId)) {
                return user;
            }
        }
        return null;
    }

    public Property getPropertyById(UUID propertyId) {
        for (Property property : properties) {
            if (property.getId().equals(propertyId)) {
                return property;
            }
        }
        return null;
    }

    // todo: implement regex search for property name
    public Property getPropertyByName(String propertyName) {
        for (Property property : properties) {
            if (property.getName().toLowerCase().equals(propertyName.toLowerCase())) {
                return property;
            }
        }
        return null;
    }

    public Listing getListingById(UUID listingId) {
        for (Listing listing : listings) {
            if (listing.getId().equals(listingId)) {
                return listing;
            }
        }
        return null;
    }

    public Session getSessionById(UUID token) {
        if (sessionMap.containsKey(token)) {
            return sessionMap.get(token);
        }
        return null;
    }

    public boolean validateSession(Session session) {
        if (session == null) {
            return false;
        }
        UUID token = session.getToken();
        UUID userId = session.getUserId();
        if (sessionMap.containsKey(token)) {
            return sessionMap.get(token).getUserId().equals(userId);
        } else {
            return false;
        }
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
