package housingapp.query;

import java.util.ArrayList;
import java.util.Map;
import java.util.HashMap;
import java.util.UUID;
import at.favre.lib.crypto.bcrypt.BCrypt;
import housingapp.Session;
import housingapp.housing.Listing;
import housingapp.housing.Property;
import housingapp.rating.PropertyRating;
import housingapp.rating.Rating;
import housingapp.rating.StudentRating;
import housingapp.resources.*;
import housingapp.system.SysConst;
import housingapp.user.PropertyManager;
import housingapp.user.Student;
import housingapp.user.User;

/**
 * Singleton class for all resource management. Interfaces with JSON reader/writer classes for pulling and storing housingapp.data
 * @author Andrew Eldridge
 */

public class ResourceManager {

    private static ResourceManager instance;

    // user attributes
    private Map<String, ArrayList<User>> userMap;
    private ArrayList<User> students;
    private ArrayList<User> propertyManagers;
    private ArrayList<User> users;

    private ArrayList<Property> properties;
    private ArrayList<Listing> listings;
    private ArrayList<Session> sessions;

    // rating attributes
    private Map<String, ArrayList<Rating>> ratingMap;
    private ArrayList<Rating> propertyRatings;
    private ArrayList<Rating> studentRatings;
    private ArrayList<Rating> ratings;

    private Map<String, User> userEmailMap;
    private Map<UUID, Session> sessionMap;

    public ResourceManager() {
        this.userMap = RscUser.getUsers();
        this.students = userMap.get(SysConst.STUDENT_USERS);
        this.propertyManagers = userMap.get(SysConst.PROPERTY_MANAGER_USERS);
        this.users = this.students;
        this.users.addAll(propertyManagers);

        this.properties = RscProperty.getProperties();
        this.listings = RscListing.getListings();
        this.sessions = RscSession.getSessions();

        this.ratingMap = RscRating.getRatings();
        this.propertyRatings = ratingMap.get(SysConst.PROPERTY_RATINGS);
        this.studentRatings = ratingMap.get(SysConst.STUDENT_USER_RATINGS);
        this.ratings = this.propertyRatings;
        this.ratings.addAll(studentRatings);

        this.userEmailMap = new HashMap<String, User>();
        this.sessionMap = new HashMap<UUID, Session>();

        // populate user and session maps with initial values
        if (students != null) {
            for (User student : students) {
                userEmailMap.put(student.getEmail(), student);
            }
        }
        if (propertyManagers != null) {
            for (User propertyManager : propertyManagers) {
                userEmailMap.put(propertyManager.getEmail(), propertyManager);
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

    public Map<String, ArrayList<User>> getUserMap() {
        return userMap;
    }

    public ArrayList<User> getStudents() {
        return students;
    }

    public ArrayList<User> getPropertyManagers() {
        return propertyManagers;
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

    public Map<String, ArrayList<Rating>> getRatingMap() {
        return ratingMap;
    }

    public ArrayList<Rating> getPropertyRatings() {
        return propertyRatings;
    }

    public ArrayList<Rating> getStudentRatings() {
        return studentRatings;
    }

    public ArrayList<Rating> getRatings() {
        return ratings;
    }

    public User getUserById(UUID userId) {
        User ret = getStudentById(userId);
        if (ret == null) {
            ret = getPropertyManagerById(userId);
        }
        return ret;
    }

    public Student getStudentById(UUID studentId) {
        for (User student : students) {
            if (student.getId().equals(studentId)) {
                return (Student) student;
            }
        }
        return null;
    }

    public PropertyManager getPropertyManagerById(UUID propertyManagerId) {
        for (User propertyManager : propertyManagers) {
            if (propertyManager.getId().equals(propertyManagerId)) {
                return (PropertyManager) propertyManager;
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

    public Session getSessionByToken(UUID token) {
        if (sessionMap.containsKey(token)) {
            return sessionMap.get(token);
        }
        return null;
    }

    public Rating getRatingById(UUID ratingId) {
        for (Rating rating : ratings) {
            if (rating.getId().equals(ratingId)) {
                return rating;
            }
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

    public void addStudent(Student student) {
        students.add(student);
        users.add(student);
        userMap.put(SysConst.STUDENT_USERS, students);
        RscUser.writeUsers();
    }

    public void addPropertyManager(PropertyManager propertyManager) {
        propertyManagers.add(propertyManager);
        users.add(propertyManager);
        userMap.put(SysConst.PROPERTY_MANAGER_USERS, propertyManagers);
        RscUser.writeUsers();
    }

    public void addProperty(Property property) {
        properties.add(property);
        RscProperty.writeProperties();
    }

    public void addListing(Listing listing) {
        listings.add(listing);
        RscListing.writeListings();
    }

    public Session login(String email, String password) {
        for (int i=0; i<users.size(); i++) {
            User currUser = users.get(i);
            if (currUser.getEmail().equals(email)) {
                if (BCrypt.verifyer().verify(password.toCharArray(), currUser.getPassword()).verified) {
                    Session newSession = new Session(userEmailMap.get(email).getId());
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

    public void addPropertyRating(PropertyRating propertyRating) {
        propertyRatings.add(propertyRating);
        ratings.add(propertyRating);
        ratingMap.put(SysConst.PROPERTY_RATINGS, propertyRatings);
        RscRating.writeRatings();
    }

    public void addStudentRating(StudentRating studentRating) {
        studentRatings.add(studentRating);
        ratings.add(studentRating);
        ratingMap.put(SysConst.STUDENT_USER_RATINGS, studentRatings);
        RscRating.writeRatings();
    }
}
