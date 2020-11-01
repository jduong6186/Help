package housingapp.query;

import java.util.ArrayList;
import java.util.Map;
import java.util.HashMap;
import java.util.UUID;
import at.favre.lib.crypto.bcrypt.BCrypt;
import housingapp.Session;
import housingapp.housing.Apartment;
import housingapp.housing.Listing;
import housingapp.housing.Property;
import housingapp.housing.Townhouse;
import housingapp.rating.PropertyRating;
import housingapp.rating.Rating;
import housingapp.rating.StudentRating;
import housingapp.resources.*;
import housingapp.SysConst;
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
    private Map<String, User> userEmailMap;

    // property attributes
    private ArrayList<Property> properties;

    // listing attributes
    private Map<String, ArrayList<Listing>> listingMap;
    private ArrayList<Listing> apartments;
    private ArrayList<Listing> townhouses;

    // session attributes
    private Map<UUID, Session> sessionMap;
    private ArrayList<Session> sessions;

    // rating attributes
    private Map<String, ArrayList<Rating>> ratingMap;
    private ArrayList<Rating> propertyRatings;
    private ArrayList<Rating> studentRatings;
    //private ArrayList<Rating> ratings;

    public ResourceManager() {
        this.userMap = RscUser.getUsers();
        this.students = userMap.get(SysConst.STUDENT_USERS);
        this.propertyManagers = userMap.get(SysConst.PROPERTY_MANAGER_USERS);
        this.users = new ArrayList<User>();
        this.users.addAll(students);
        this.users.addAll(propertyManagers);

        this.properties = RscProperty.getProperties();

        this.listingMap = RscListing.getListings();
        this.apartments = listingMap.get("apartments");
        this.townhouses = listingMap.get("townhouses");

        this.sessions = RscSession.getSessions();

        this.ratingMap = RscRating.getRatings();
        this.propertyRatings = ratingMap.get(SysConst.PROPERTY_RATINGS);
        this.studentRatings = ratingMap.get(SysConst.STUDENT_USER_RATINGS);
        //this.ratings = this.propertyRatings;
        //this.ratings.addAll(studentRatings);

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

    // general accessors
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
        ArrayList<Listing> listings = new ArrayList<Listing>();
        ArrayList<Listing> apartments = this.apartments;
        ArrayList<Listing> townhouses = this.townhouses;
        listings.addAll(apartments);
        listings.addAll(townhouses);
        return listings;
    }

    public Map<String, ArrayList<Listing>> getListingMap() {
        return listingMap;
    }

    public ArrayList<Listing> getApartments() {
        return apartments;
    }

    public ArrayList<Listing> getTownhouses() {
        return townhouses;
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

    /*
    public ArrayList<Rating> getRatings() {
        return ratings;
    }
     */

    // target accessors
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
            if (property.getName().equalsIgnoreCase(propertyName)) {
                return property;
            }
        }
        return null;
    }

    public Listing getListingById(UUID listingId) {
        Listing ret = getApartmentById(listingId);
        if (ret == null) {
            ret = getTownhouseById(listingId);
        }
        return ret;
    }

    public Apartment getApartmentById(UUID apartmentId) {
        for (Listing apartment : apartments) {
            if (apartment.getId().equals(apartmentId)) {
                return (Apartment) apartment;
            }
        }
        return null;
    }

    public Townhouse getTownhouseById(UUID townhouseId) {
        for (Listing townhouse : townhouses) {
            if (townhouse.getId().equals(townhouseId)) {
                return (Townhouse) townhouse;
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
        Rating ret = getPropertyRatingById(ratingId);
        if (ret == null) {
            ret = getStudentRatingById(ratingId);
        }
        return ret;
    }

    public PropertyRating getPropertyRatingById(UUID propertyRatingId) {
        for (Rating propertyRating : propertyRatings) {
            if (propertyRating.getId().equals(propertyRatingId)) {
                return (PropertyRating) propertyRating;
            }
        }
        return null;
    }

    public StudentRating getStudentRatingById(UUID studentRatingId) {
        for (Rating studentRating : studentRatings) {
            if (studentRating.getId().equals(studentRatingId)) {
                return (StudentRating) studentRating;
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

    // add methods
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

    public void addApartment(Apartment apartment) {
        apartments.add(apartment);
        listingMap.put("apartments", apartments);
        RscListing.writeListings();
    }

    public void addTownhouse(Townhouse townhouse) {
        townhouses.add(townhouse);
        listingMap.put("townhouses", townhouses);
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
        //ratings.add(propertyRating);
        ratingMap.put(SysConst.PROPERTY_RATINGS, propertyRatings);
        RscRating.writeRatings();
    }

    public void addStudentRating(StudentRating studentRating) {
        studentRatings.add(studentRating);
        //ratings.add(studentRating);
        ratingMap.put(SysConst.STUDENT_USER_RATINGS, studentRatings);
        RscRating.writeRatings();
    }

    // update methods
    public void updateStudent(UUID studentId, Student modifiedStudent) {
        removeStudent(studentId);
        addStudent(modifiedStudent);
    }

    public void updatePropertyManager(UUID propertyManagerId, PropertyManager modifiedPropertyManager) {
        removePropertyManager(propertyManagerId);
        addPropertyManager(modifiedPropertyManager);
    }

    public void updateProperty(UUID propertyId, Property modifiedProperty) {
        removeProperty(propertyId);
        addProperty(modifiedProperty);
    }

    public void updateApartment(UUID apartmentId, Apartment modifiedApartment) {
        removeApartment(apartmentId);
        addApartment(modifiedApartment);
    }

    public void updateTownhouse(UUID townhouseId, Townhouse modifiedTownhouse) {
        removeTownhouse(townhouseId);
        addTownhouse(modifiedTownhouse);
    }

    public void updatePropertyRating(UUID propertyRatingId, PropertyRating modifiedPropertyRating) {
        removePropertyRating(propertyRatingId);
        addPropertyRating(modifiedPropertyRating);
    }

    public void updateStudentRating(UUID studentRatingId, StudentRating modifiedStudentRating) {
        removeStudentRating(studentRatingId);
        addStudentRating(modifiedStudentRating);
    }

    public void removeStudent(UUID studentId) {
        for (int i=0; i<students.size(); i++) {
            User curr = students.get(i);
            if (curr.getId().equals(studentId)) {
                userMap.get(SysConst.STUDENT_USERS).remove(curr);
                users.remove(curr);
                students.remove(i);
                return;
            }
        }
    }

    public void removePropertyManager(UUID propertyManagerId) {
        for (int i=0; i<propertyManagers.size(); i++) {
            User curr = propertyManagers.get(i);
            if (curr.getId().equals(propertyManagerId)) {
                userMap.get(SysConst.PROPERTY_MANAGER_USERS).remove(curr);
                users.remove(curr);
                propertyManagers.remove(i);
                return;
            }
        }
    }

    public void removeProperty(UUID propertyId) {
        for (int i=0; i<properties.size(); i++) {
            if (properties.get(i).getId().equals(propertyId)) {
                properties.remove(i);
                return;
            }
        }
    }

    public void removeApartment(UUID apartmentId) {
        for (int i=0; i<apartments.size(); i++) {
            Listing curr = apartments.get(i);
            if (curr.getId().equals(apartmentId)) {
                listingMap.get("apartments").remove(curr);
                apartments.remove(i);
                return;
            }
        }
    }

    public void removeTownhouse(UUID townhouseId) {
        for (int i=0; i<townhouses.size(); i++) {
            Listing curr = townhouses.get(i);
            if (curr.getId().equals(townhouseId)) {
                listingMap.get("townhouses").remove(curr);
                townhouses.remove(i);
                return;
            }
        }
    }

    public void removePropertyRating(UUID propertyRatingId) {
        for (int i=0; i<propertyRatings.size(); i++) {
            Rating curr = propertyRatings.get(i);
            if (curr.getId().equals(propertyRatingId)) {
                ratingMap.get(SysConst.PROPERTY_RATINGS).remove(curr);
                //ratings.remove(curr);
                propertyRatings.remove(i);
                return;
            }
        }
    }

    public void removeStudentRating(UUID studentRatingId) {
        for (int i=0; i<studentRatings.size(); i++) {
            Rating curr = studentRatings.get(i);
            if (curr.getId().equals(studentRatingId)) {
                ratingMap.get(SysConst.STUDENT_USER_RATINGS).remove(curr);
                //ratings.remove(curr);
                studentRatings.remove(i);
                return;
            }
        }
    }
}
