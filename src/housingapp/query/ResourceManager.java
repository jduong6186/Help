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

    /**
     * Constructor for resource manager class
     */
    public ResourceManager() {
        this.userMap = RscUser.getUsers();
        if (this.userMap != null) {
            this.students = userMap.get(SysConst.STUDENT_USERS);
            this.propertyManagers = userMap.get(SysConst.PROPERTY_MANAGER_USERS);
        }
        this.users = new ArrayList<User>();
        if (this.students == null) {
            this.students = new ArrayList<User>();
        } else {
            this.users.addAll(students);
        }
        if (this.propertyManagers == null) {
            this.propertyManagers = new ArrayList<User>();
        } else {
            this.users.addAll(propertyManagers);
        }

        this.properties = RscProperty.getProperties();
        if (this.properties == null) {
            this.properties = new ArrayList<Property>();
        }

        this.listingMap = RscListing.getListings();
        if (this.listingMap != null) {
            this.apartments = listingMap.get("apartments");
            this.townhouses = listingMap.get("townhouses");
        }
        if (this.apartments == null) {
            this.apartments = new ArrayList<Listing>();
        }
        if (this.townhouses == null) {
            this.townhouses = new ArrayList<Listing>();
        }

        this.sessions = RscSession.getSessions();

        this.ratingMap = RscRating.getRatings();
        if (this.ratingMap != null) {
            this.propertyRatings = ratingMap.get("propertyRatings");
            this.studentRatings = ratingMap.get("studentRatings");
        }
        if (propertyRatings == null) {
            this.propertyRatings = new ArrayList<Rating>();
        }
        if (studentRatings == null) {
            this.studentRatings = new ArrayList<Rating>();
        }

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

    /**
     * creates an instance of resource manager class
     * @return The newly made resource manager class
     */
    public static ResourceManager getInstance() {
        if (instance == null) {
            instance = new ResourceManager();
        }
        return instance;
    }

    /**
     * Returns map variable with strings and list of users
     * @return the userMap that contains string and list of users
     */
    public Map<String, ArrayList<User>> getUserMap() {
        return userMap;
    }

    /**
     * returns all of the student accounts
     * @return a list of all the student accounts
     */
    public ArrayList<User> getStudents() {
        return students;
    }

    /**
     * returns all of the property manager accounts
     * @return a list of all the property manager accounts
     */
    public ArrayList<User> getPropertyManagers() {
        return propertyManagers;
    }

    /**
     * returns all of the properties registered
     * @return a list of all the properties registered
     */
    public ArrayList<Property> getProperties() {
        return properties;
    }

    /**
     * returns all of the listings registered
     * @return a list of all the listings registered 
     */
    public ArrayList<Listing> getListings() {
        ArrayList<Listing> listings = new ArrayList<Listing>();
        ArrayList<Listing> apartments = this.apartments;
        ArrayList<Listing> townhouses = this.townhouses;
        listings.addAll(apartments);
        listings.addAll(townhouses);
        return listings;
    }

    /**
     * returns map variable of a string and a list of listings
     * @return the listingMap that contains string and list of listings
     */
    public Map<String, ArrayList<Listing>> getListingMap() {
        return listingMap;
    }

    /**
     * returns a list of apartments that are registered
     * @return list of apartments that registered
     */
    public ArrayList<Listing> getApartments() {
        return apartments;
    }

    /**
     * returns a list of apartments that are registered
     * @return list of town houses that are registered
     */
    public ArrayList<Listing> getTownhouses() {
        return townhouses;
    }

    /**
     * returns a list of sessions that are registered
     * @return list of sessions
     */
    public ArrayList<Session> getSessions() {
        return sessions;
    }

    /**
     * rating map that relates string to list of ratings
     * @return map object that relates string to list of ratings
     */
    public Map<String, ArrayList<Rating>> getRatingMap() {
        return ratingMap;
    }

    /**
     * returns the list of property ratings
     * @return list of property ratings
     */
    public ArrayList<Rating> getPropertyRatings() {
        return propertyRatings;
    }

    /**
     * returns the list of student ratings
     * @return list of student ratings
     */
    public ArrayList<Rating> getStudentRatings() {
        return studentRatings;
    }

    // target accessors
    /**
     * Used to get a specific user
     * @param userId the id that pertains to the user being searched for
     * @return the user account that is being searched
     */
    public User getUserById(UUID userId) {
        User ret = getStudentById(userId);
        if (ret == null) {
            ret = getPropertyManagerById(userId);
        }
        return ret;
    }

    /**
     * searches for specific student account
     * @param studentId the unique id that pertains to the student id being searched for
     * @return the student account that is being searched
     */
    public Student getStudentById(UUID studentId) {
        for (User student : students) {
            if (student.getId().equals(studentId)) {
                return (Student) student;
            }
        }
        return null;
    }

    public Student getStudentByName(String firstName, String lastName) {
        for (User student : students) {
            if (student.getFirstName().equalsIgnoreCase(firstName) && student.getLastName().equalsIgnoreCase(lastName)) {
                return (Student) student;
            }
        }
        return null;
    }

    /**
     * searched for a specific property manager account
     * @param propertyManagerId the unique id that pertains to the property manager account being searched for
     * @return the property manager account that is being searched
     */
    public PropertyManager getPropertyManagerById(UUID propertyManagerId) {
        for (User propertyManager : propertyManagers) {
            if (propertyManager.getId().equals(propertyManagerId)) {
                return (PropertyManager) propertyManager;
            }
        }
        return null;
    }

    /**
     * searched for a specific property
     * @param propertyId the unique id that pertains to the property being searched for
     * @return the property that is being searched for
     */
    public Property getPropertyById(UUID propertyId) {
        for (Property property : properties) {
            if (property.getId().equals(propertyId)) {
                return property;
            }
        }
        return null;
    }

    /**
     * Searches through registered properties and matches by name searched
     * @param propertyName user entered name of the property they are looking for
     * @return the property that was serached for by name
     */
    public Property getPropertyByName(String propertyName) {
        for (Property property : properties) {
            if (property.getName().equalsIgnoreCase(propertyName)) {
                return property;
            }
        }
        return null;
    }

    /**
     * searches registered listings by their unique id
     * @param listingId the unique id that pertains to the listing that is being searched for
     * @return the listing that is being searched for
     */
    public Listing getListingById(UUID listingId) {
        Listing ret = getApartmentById(listingId);
        if (ret == null) {
            ret = getTownhouseById(listingId);
        }
        return ret;
    }

    /**
     * searched registered apartments by their unique id
     * @param apartmentId the unique id that pertains to the listing that is being searched for
     * @return the apartment that is being searched for
     */
    public Apartment getApartmentById(UUID apartmentId) {
        for (Listing apartment : apartments) {
            if (apartment.getId().equals(apartmentId)) {
                return (Apartment) apartment;
            }
        }
        return null;
    }

    /**
     * searches registered town houses by their unique id
     * @param townhouseId the unique id that pertains to the town house that is being searched for
     * @return the town house that is being searched for
     */
    public Townhouse getTownhouseById(UUID townhouseId) {
        for (Listing townhouse : townhouses) {
            if (townhouse.getId().equals(townhouseId)) {
                return (Townhouse) townhouse;
            }
        }
        return null;
    }

    /**
     * searches sessions by their id called token
     * @param token the unique id that pertains to the session is being searched for
     * @return the session that is being searched for
     */
    public Session getSessionByToken(UUID token) {
        if (sessionMap.containsKey(token)) {
            return sessionMap.get(token);
        }
        return null;
    }

    /**
     * searches for a specific rating by its unique id
     * @param ratingId the unique id that pertains to the rating that is being searched for
     * @return the rating that is being searched for
     */
    public Rating getRatingById(UUID ratingId) {
        Rating ret = getPropertyRatingById(ratingId);
        if (ret == null) {
            ret = getStudentRatingById(ratingId);
        }
        return ret;
    }

    /**
     * searches registered properties for a specific property by its unique id
     * @param propertyRatingId the unique id that pertains to the property that is being searched for
     * @return the property that is being searched for
     */
    public PropertyRating getPropertyRatingById(UUID propertyRatingId) {
        for (Rating propertyRating : propertyRatings) {
            if (propertyRating.getId().equals(propertyRatingId)) {
                return (PropertyRating) propertyRating;
            }
        }
        return null;
    }

    /**
     * searches student accounts for a specific student account by its unique id
     * @param studentRatingId the unique id that pertains to the student account that is being searched for
     * @return the student account that is being searched for
     */
    public StudentRating getStudentRatingById(UUID studentRatingId) {
        for (Rating studentRating : studentRatings) {
            if (studentRating.getId().equals(studentRatingId)) {
                return (StudentRating) studentRating;
            }
        }
        return null;
    }

    /**
     * making sure a user has the right session
     * @param session the session that was give to the user
     * @return if there is a valid session
     */
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
    /**
     * adds a student to the list of student accounts and writes to json file student account details
     * @param student the student object to be added
     */
    public void addStudent(Student student) {
        students.add(student);
        users.add(student);
        userMap.put(SysConst.STUDENT_USERS, students);
        userEmailMap.put(student.getEmail(), student);
        RscUser.writeUsers();
    }

    /**
     * adds a property manager to the list of property manager accounts and writes to json file property account details
     * @param propertyManager the propertyManager object to be added
     */
    public void addPropertyManager(PropertyManager propertyManager) {
        propertyManagers.add(propertyManager);
        users.add(propertyManager);
        userMap.put(SysConst.PROPERTY_MANAGER_USERS, propertyManagers);
        userEmailMap.put(propertyManager.getEmail(), propertyManager);
        RscUser.writeUsers();
    }

    /**
     * adds a property to the list of properties registered and writes to json file of properties
     * @param property the property object to be added
     */
    public void addProperty(Property property) {
        properties.add(property);
        RscProperty.writeProperties();
    }

    /**
     * adds a apartment to the list of listings registered and writes to json files of listings
     * @param apartment the apartment object to be added
     */
    public void addApartment(Apartment apartment) {
        apartments.add(apartment);
        listingMap.put("apartments", apartments);
        RscListing.writeListings();
    }

    /**
     * adds a town house to the list of listings registered and writes of json file of listings
     * @param townhouse the town house object to be added
     */
    public void addTownhouse(Townhouse townhouse) {
        townhouses.add(townhouse);
        listingMap.put("townhouses", townhouses);
        RscListing.writeListings();
    }

    /**
     * log ins the user
     * @param email email of the user's account
     * @param password password of the user's account
     * @return the session for the user
     */
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

    /**
     * adds a session to the list of sessions
     * @param session session object that is to be added
     */
    private void addSession(Session session) {
        sessions.add(session);
        sessionMap.put(session.getToken(), session);
        RscSession.writeSessions();
    }

    /**
     * adds a rating to a property
     * @param propertyRating the object containing the rating for the property
     */
    public void addPropertyRating(PropertyRating propertyRating) {
        propertyRatings.add(propertyRating);
        ratingMap.put("propertyRatings", propertyRatings);
        RscRating.writeRatings();
    }

    /**
     * adds a rating to a student account
     * @param studentRating the object that is containing the rating for the student
     */
    public void addStudentRating(StudentRating studentRating) {
        studentRatings.add(studentRating);
        ratingMap.put("studentRatings", studentRatings);
        RscRating.writeRatings();
    }

    // update methods
    /**
     * updates a student account with new info
     * @param studentId the id of the student account 
     * @param modifiedStudent object for the new student account replacing the old instance
     */
    public void updateStudent(UUID studentId, Student modifiedStudent) {
        removeStudent(studentId);
        addStudent(modifiedStudent);
    }

    /**
     * update a property manager account with new info
     * @param propertyManagerId the id of the property manager account
     * @param modifiedPropertyManager object for the new property manager account replace the old instance
     */
    public void updatePropertyManager(UUID propertyManagerId, PropertyManager modifiedPropertyManager) {
        removePropertyManager(propertyManagerId);
        addPropertyManager(modifiedPropertyManager);
    }

    /**
     * update a registered property with new info
     * @param propertyId the id of the property to be updated
     * @param modifiedProperty object for the new property to replace the old instance
     */
    public void updateProperty(UUID propertyId, Property modifiedProperty) {
        removeProperty(propertyId);
        addProperty(modifiedProperty);
    }

    /**
     * update a registered apartment with new info
     * @param apartmentId the id of the apartment to be updated
     * @param modifiedApartment object for the new apartment to replace the old instance
     */
    public void updateApartment(UUID apartmentId, Apartment modifiedApartment) {
        removeApartment(apartmentId);
        addApartment(modifiedApartment);
    }

    /**
     * update a registered town house with new info
     * @param townhouseId the id of the town house to be updated
     * @param modifiedTownhouse object for the new town house to replace the old instance
     */
    public void updateTownhouse(UUID townhouseId, Townhouse modifiedTownhouse) {
        removeTownhouse(townhouseId);
        addTownhouse(modifiedTownhouse);
    }

    /**
     * update a property rating with new info
     * @param propertyRatingId the id of the property rating to be updated
     * @param modifiedPropertyRating object for the new property rating to replace the old instance
     */
    public void updatePropertyRating(UUID propertyRatingId, PropertyRating modifiedPropertyRating) {
        removePropertyRating(propertyRatingId);
        addPropertyRating(modifiedPropertyRating);
    }

    /**
     * update a student rating with new info
     * @param studentRatingId the id of the student rating to be updated
     * @param modifiedStudentRating object for the new student rating to replace the old instance
     */
    public void updateStudentRating(UUID studentRatingId, StudentRating modifiedStudentRating) {
        removeStudentRating(studentRatingId);
        addStudentRating(modifiedStudentRating);
    }

    /**
     * remove a student account from the list of accounts
     * @param studentId the unique id that pertains to the student account that is to be deleted
     */
    public void removeStudent(UUID studentId) {
        for (int i=0; i<students.size(); i++) {
            User curr = students.get(i);
            if (curr.getId().equals(studentId)) {
                users.remove(curr);
                students.remove(i);
                userMap.put(SysConst.STUDENT_USERS, students);
                return;
            }
        }
    }

    /**
     * remove a property manager account from the list of accounts
     * @param propertyManagerId the unique id that pertains to the property manager account that is to be deleted
     */
    public void removePropertyManager(UUID propertyManagerId) {
        for (int i=0; i<propertyManagers.size(); i++) {
            User curr = propertyManagers.get(i);
            if (curr.getId().equals(propertyManagerId)) {
                users.remove(curr);
                propertyManagers.remove(i);
                userMap.put(SysConst.PROPERTY_MANAGER_USERS, propertyManagers);
                return;
            }
        }
    }

    /**
     * removes a property from the list of registered properties
     * @param propertyId the unique id that pertains to the property that is to be deleted
     */
    public void removeProperty(UUID propertyId) {
        for (int i=0; i<properties.size(); i++) {
            if (properties.get(i).getId().equals(propertyId)) {
                properties.remove(i);
                return;
            }
        }
    }

    /**
     * removes an apartment from the list of listings
     * @param apartmentId the unique id that pertains to the apartment that is to be deleted
     */
    public void removeApartment(UUID apartmentId) {
        if (apartments != null) {
            for (int i=0; i<apartments.size(); i++) {
                Listing curr = apartments.get(i);
                if (curr.getId().equals(apartmentId)) {
                    apartments.remove(i);
                    listingMap.put("apartments", apartments);
                    return;
                }
            }
        }
    }

    /**
     * removes a town house from the list of listings
     * @param townhouseId the unique id that pertains to the town house that is to be deleted
     */
    public void removeTownhouse(UUID townhouseId) {
        if (townhouses != null) {
            for (int i=0; i<townhouses.size(); i++) {
                Listing curr = townhouses.get(i);
                if (curr.getId().equals(townhouseId)) {
                    townhouses.remove(i);
                    listingMap.put("townhouses", townhouses);
                    return;
                }
            }
        }
    }

    /**
     * removes a rating that was for a property
     * @param propertyRatingId the unique id that pertains to the property rating that is to be deleted
     */
    public void removePropertyRating(UUID propertyRatingId) {
        for (int i=0; i<propertyRatings.size(); i++) {
            Rating curr = propertyRatings.get(i);
            if (curr.getId().equals(propertyRatingId)) {
                //ratings.remove(curr);
                propertyRatings.remove(i);
                ratingMap.put("propertyRatings", propertyRatings);
                return;
            }
        }
    }

    /**
     * removes a rating that was for a student
     * @param studentRatingId the unique id id that pertains to the student rating that is to be deleted
     */
    public void removeStudentRating(UUID studentRatingId) {
        for (int i=0; i<studentRatings.size(); i++) {
            Rating curr = studentRatings.get(i);
            if (curr.getId().equals(studentRatingId)) {
                //ratings.remove(curr);
                studentRatings.remove(i);
                ratingMap.put("studentRatings", studentRatings);
                return;
            }
        }
    }
}
