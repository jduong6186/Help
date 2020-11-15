package tests;

import housingapp.Session;
import housingapp.SysConst;
import housingapp.housing.Apartment;
import housingapp.housing.Listing;
import housingapp.housing.Property;
import housingapp.housing.Townhouse;
import housingapp.query.ResourceManager;
import housingapp.rating.PropertyRating;
import housingapp.rating.Rating;
import housingapp.rating.StudentRating;
import housingapp.resources.RscProperty;
import housingapp.user.PropertyManager;
import housingapp.user.Student;
import housingapp.user.User;
import org.json.simple.JSONObject;
import org.junit.*;

import java.util.ArrayList;
import java.util.UUID;

import static org.junit.Assert.*;

public class ResourceManagerTest {

    private static ResourceManager rm = ResourceManager.getInstance();

    private PropertyManager propertyManager = TestConst.PROPERTY_MANAGER_VALID;
    private PropertyManager emptyPropertyManager = TestConst.PROPERTY_MANAGER_EMPTY;

    private Student student = TestConst.STUDENT_VALID;
    private Student emptyStudent = TestConst.STUDENT_EMPTY;

    private Property property = TestConst.PROPERTY_VALID;
    private Property emptyProperty = TestConst.PROPERTY_EMPTY;

    private Apartment apartment = TestConst.APARTMENT_VALID;
    private Apartment emptyApartment = TestConst.APARTMENT_EMPTY;

    private Townhouse townhouse = TestConst.TOWNHOUSE_VALID;
    private Townhouse emptyTownhouse = TestConst.TOWNHOUSE_EMPTY;

    private PropertyRating propertyRating = TestConst.PROPERTY_RATING_VALID;
    private PropertyRating emptyPropertyRating = TestConst.PROPERTY_RATING_EMPTY;

    private StudentRating studentRating = TestConst.STUDENT_RATING_VALID;
    private StudentRating emptyStudentRating = TestConst.STUDENT_RATING_EMPTY;

    private Session session = TestConst.SESSION_VALID;
    private Session nullUserSession = TestConst.SESSION_NULL_USER;

    @BeforeClass
    public static void oneTimeSetup() {

    }

    @AfterClass
    public static void oneTimeTearDown() {

    }

    @Before
    public void setup() {
        // clear all rm data
        rm.clearProperties();
        rm.clearListings();
        rm.clearRatings();
        rm.clearSessions();
        rm.clearUsers();
    }

    @After
    public void tearDown() {

    }

    @Test
    public void testGetValidUserById() {
        rm.addPropertyManager(propertyManager);
        User returnedUser = rm.getUserById(propertyManager.getId());
        assertEquals(propertyManager, returnedUser);
    }

    @Test
    public void testGetEmptyUserById() {
        rm.addPropertyManager(emptyPropertyManager);
        User returnedUser = rm.getUserById(emptyPropertyManager.getId());
        assertEquals(emptyPropertyManager, returnedUser);
    }

    @Test
    public void testGetInvalidUserById() {
        rm.addPropertyManager(propertyManager);
        User returnedUser = rm.getUserById(UUID.randomUUID());
        assertNull(returnedUser);
    }

    @Test
    public void testGetValidStudentById() {
        rm.addStudent(student);
        Student returnedStudent = rm.getStudentById(student.getId());
        assertEquals(student, returnedStudent);
    }

    @Test
    public void testGetEmptyStudentById() {
        rm.addStudent(emptyStudent);
        Student returnedStudent = rm.getStudentById(emptyStudent.getId());
        assertEquals(emptyStudent, returnedStudent);
    }

    @Test
    public void testGetInvalidStudentById() {
        rm.addStudent(student);
        Student returnedStudent = rm.getStudentById(UUID.randomUUID());
        assertNull(returnedStudent);
    }

    @Test
    public void testGetValidStudentByName() {
        rm.addStudent(student);
        Student returnedStudent = rm.getStudentByName(student.getFirstName(), student.getLastName());
        assertEquals(student, returnedStudent);
    }

    @Test
    public void testGetEmptyStudentByName() {
        rm.addStudent(emptyStudent);
        Student returnedStudent = rm.getStudentByName(emptyStudent.getFirstName(), emptyStudent.getLastName());
        assertEquals(emptyStudent, returnedStudent);
    }

    @Test
    public void testGetInvalidStudentByName() {
        rm.addStudent(student);
        Student returnedStudent = rm.getStudentByName("Invalid", "Name");
        assertNull(returnedStudent);
    }

    @Test
    public void testGetValidPropertyManagerById() {
        rm.addPropertyManager(propertyManager);
        PropertyManager returnedPropertyManager = rm.getPropertyManagerById(propertyManager.getId());
        assertEquals(propertyManager, returnedPropertyManager);
    }

    @Test
    public void testGetEmptyPropertyManagerById() {
        rm.addPropertyManager(emptyPropertyManager);
        PropertyManager returnedPropertyManager = rm.getPropertyManagerById(emptyPropertyManager.getId());
        assertEquals(emptyPropertyManager, returnedPropertyManager);
    }

    @Test
    public void testGetInvalidPropertyManagerById() {
        rm.addPropertyManager(propertyManager);
        PropertyManager returnedPropertyManager = rm.getPropertyManagerById(UUID.randomUUID());
        assertNull(returnedPropertyManager);
    }

    @Test
    public void testGetValidPropertyById() {
        rm.addProperty(property);
        Property returnedProperty = rm.getPropertyById(property.getId());
        assertEquals(property, returnedProperty);
    }

    @Test
    public void testGetEmptyPropertyById() {
        rm.addProperty(emptyProperty);
        Property returnedProperty = rm.getPropertyById(emptyProperty.getId());
        assertEquals(emptyProperty, returnedProperty);
    }

    @Test
    public void testGetInvalidPropertyById() {
        rm.addProperty(property);
        Property returnedProperty = rm.getPropertyById(UUID.randomUUID());
        assertNull(returnedProperty);
    }

    @Test
    public void testGetValidPropertyByName() {
        rm.addProperty(property);
        Property returnedProperty = rm.getPropertyByName(property.getName());
        assertEquals(property, returnedProperty);
    }

    @Test
    public void testGetEmptyPropertyByName() {
        rm.addProperty(emptyProperty);
        Property returnedProperty = rm.getPropertyByName(emptyProperty.getName());
        assertEquals(emptyProperty, returnedProperty);
    }

    @Test
    public void testGetInvalidPropertyByName() {
        rm.addProperty(property);
        Property returnedProperty = rm.getPropertyByName("Invalid name");
        assertNull(returnedProperty);
    }

    @Test
    public void testGetValidListingById() {
        rm.addApartment(apartment);
        Listing returnedListing = rm.getListingById(apartment.getId());
        assertEquals(apartment, returnedListing);
    }

    @Test
    public void testGetEmptyListingById() {
        rm.addApartment(emptyApartment);
        Listing returnedListing = rm.getListingById(emptyApartment.getId());
        assertEquals(emptyApartment, returnedListing);
    }

    @Test
    public void testGetInvalidListingById() {
        rm.addApartment(apartment);
        Listing returnedListing = rm.getListingById(UUID.randomUUID());
        assertNull(returnedListing);
    }

    @Test
    public void testGetValidApartmentById() {
        rm.addApartment(apartment);
        Apartment returnedApartment = rm.getApartmentById(apartment.getId());
        assertEquals(apartment, returnedApartment);
    }

    @Test
    public void testGetEmptyApartmentById() {
        rm.addApartment(emptyApartment);
        Apartment returnedApartment = rm.getApartmentById(emptyApartment.getId());
        assertEquals(emptyApartment, returnedApartment);
    }

    @Test
    public void testGetInvalidApartmentById() {
        rm.addApartment(apartment);
        Apartment returnedApartment = rm.getApartmentById(UUID.randomUUID());
        assertNull(returnedApartment);
    }

    @Test
    public void testGetValidTownhouseById() {
        rm.addTownhouse(townhouse);
        Townhouse returnedTownhouse = rm.getTownhouseById(townhouse.getId());
        assertEquals(townhouse, returnedTownhouse);
    }

    @Test
    public void testGetEmptyTownhouseById() {
        rm.addTownhouse(emptyTownhouse);
        Townhouse returnedTownhouse = rm.getTownhouseById(emptyTownhouse.getId());
        assertEquals(emptyTownhouse, returnedTownhouse);
    }

    @Test
    public void testGetInvalidTownhouseById() {
        rm.addTownhouse(townhouse);
        Townhouse returnedTownhouse = rm.getTownhouseById(UUID.randomUUID());
        assertNull(returnedTownhouse);
    }

    @Test
    public void testGetValidSessionByToken() {
        rm.addSession(session);
        Session returnedSession = rm.getSessionByToken(session.getToken());
        assertEquals(session, returnedSession);
    }

    @Test
    public void testGetNullUserSessionByToken() {
        rm.addSession(nullUserSession);
        Session returnedSession = rm.getSessionByToken(nullUserSession.getToken());
        assertEquals(nullUserSession, returnedSession);
    }

    @Test
    public void testGetInvalidSessionByToken() {
        rm.addSession(session);
        Session returnedSession = rm.getSessionByToken(UUID.randomUUID());
        assertNull(returnedSession);
    }

    @Test
    public void testGetValidRatingById() {
        rm.addPropertyRating(propertyRating);
        Rating returnedRating = rm.getRatingById(propertyRating.getId());
        assertEquals(propertyRating, returnedRating);
    }

    @Test
    public void testGetEmptyRatingById() {
        rm.addPropertyRating(emptyPropertyRating);
        Rating returnedRating = rm.getRatingById(emptyPropertyRating.getId());
        assertEquals(emptyPropertyRating, returnedRating);
    }

    @Test
    public void testGetInvalidRatingById() {
        rm.addPropertyRating(propertyRating);
        Rating returnedRating = rm.getRatingById(UUID.randomUUID());
        assertNull(returnedRating);
    }

    @Test
    public void testGetValidPropertyRatingById() {
        rm.addPropertyRating(propertyRating);
        PropertyRating returnedPropertyRating = rm.getPropertyRatingById(propertyRating.getId());
        assertEquals(propertyRating, returnedPropertyRating);
    }

    @Test
    public void testGetEmptyPropertyRatingById() {
        rm.addPropertyRating(emptyPropertyRating);
        PropertyRating returnedPropertyRating = rm.getPropertyRatingById(emptyPropertyRating.getId());
        assertEquals(emptyPropertyRating, returnedPropertyRating);
    }

    @Test
    public void testGetInvalidPropertyRatingById() {
        rm.addPropertyRating(propertyRating);
        PropertyRating returnedPropertyRating = rm.getPropertyRatingById(UUID.randomUUID());
        assertNull(returnedPropertyRating);
    }

    @Test
    public void testGetValidStudentRatingById() {
        rm.addStudentRating(studentRating);
        StudentRating returnedStudentRating = rm.getStudentRatingById(studentRating.getId());
        assertEquals(studentRating, returnedStudentRating);
    }

    @Test
    public void testGetEmptyStudentRatingById() {
        rm.addStudentRating(emptyStudentRating);
        StudentRating returnedStudentRating = rm.getStudentRatingById(emptyStudentRating.getId());
        assertEquals(emptyStudentRating, returnedStudentRating);
    }

    @Test
    public void testGetInvalidStudentRatingById() {
        rm.addStudentRating(studentRating);
        StudentRating returnedStudentRating = rm.getStudentRatingById(UUID.randomUUID());
        assertNull(returnedStudentRating);
    }

    @Test
    public void testValidateValidSession() {
        rm.addSession(session);
        boolean validated = rm.validateSession(session);
        assertTrue(validated);
    }

    @Test
    public void testValidateInvalidSession() {
        rm.addSession(session);
        boolean validated = rm.validateSession(nullUserSession);
        assertFalse(validated);
    }

    @Test
    public void testAddValidStudent() {
        Student addedStudent = rm.getStudentById(student.getId());
        assertNull(addedStudent);
        rm.addStudent(student);
        addedStudent = rm.getStudentById(student.getId());
        assertEquals(student, addedStudent);
    }

    @Test
    public void testAddEmptyStudent() {
        Student addedStudent = rm.getStudentById(emptyStudent.getId());
        assertNull(addedStudent);
        rm.addStudent(emptyStudent);
        addedStudent = rm.getStudentById(emptyStudent.getId());
        assertEquals(emptyStudent, addedStudent);
    }

    @Test
    public void testAddValidPropertyManager() {
        PropertyManager addedPropertyManager = rm.getPropertyManagerById(propertyManager.getId());
        assertNull(addedPropertyManager);
        rm.addPropertyManager(propertyManager);
        addedPropertyManager = rm.getPropertyManagerById(propertyManager.getId());
        assertEquals(propertyManager, addedPropertyManager);
    }

    @Test
    public void testAddEmptyPropertyManager() {
        PropertyManager addedPropertyManager = rm.getPropertyManagerById(emptyPropertyManager.getId());
        assertNull(addedPropertyManager);
        rm.addPropertyManager(emptyPropertyManager);
        addedPropertyManager = rm.getPropertyManagerById(emptyPropertyManager.getId());
        assertEquals(emptyPropertyManager, addedPropertyManager);
    }

    @Test
    public void testAddValidProperty() {
        Property addedProperty = rm.getPropertyById(property.getId());
        assertNull(addedProperty);
        rm.addProperty(property);
        addedProperty = rm.getPropertyById(property.getId());
        assertEquals(property, addedProperty);
    }

    @Test
    public void testAddEmptyProperty() {
        Property addedProperty = rm.getPropertyById(emptyProperty.getId());
        assertNull(addedProperty);
        rm.addProperty(emptyProperty);
        addedProperty = rm.getPropertyById(emptyProperty.getId());
        assertEquals(emptyProperty, addedProperty);
    }

    @Test
    public void testAddValidApartment() {
        Apartment addedApartment = rm.getApartmentById(apartment.getId());
        assertNull(addedApartment);
        rm.addApartment(apartment);
        addedApartment = rm.getApartmentById(apartment.getId());
        assertEquals(apartment, addedApartment);
    }

    @Test
    public void testAddEmptyApartment() {
        Apartment addedApartment = rm.getApartmentById(emptyApartment.getId());
        assertNull(addedApartment);
        rm.addApartment(emptyApartment);
        addedApartment = rm.getApartmentById(emptyApartment.getId());
        assertEquals(emptyApartment, addedApartment);
    }

    @Test
    public void testAddValidTownhouse() {
        Townhouse addedTownhouse = rm.getTownhouseById(townhouse.getId());
        assertNull(addedTownhouse);
        rm.addTownhouse(townhouse);
        addedTownhouse = rm.getTownhouseById(townhouse.getId());
        assertEquals(townhouse, addedTownhouse);
    }

    @Test
    public void testAddEmptyTownhouse() {
        Townhouse addedTownhouse = rm.getTownhouseById(emptyTownhouse.getId());
        assertNull(addedTownhouse);
        rm.addTownhouse(emptyTownhouse);
        addedTownhouse = rm.getTownhouseById(emptyTownhouse.getId());
        assertEquals(emptyTownhouse, addedTownhouse);
    }

    @Test
    public void testValidLogin() {
        // attempt login with valid details
        Session newSession = rm.login(student.getEmail(), "pass123");
        assertNotNull(newSession);
    }

    @Test
    public void testInvalidLogin() {
        // attempt login with invalid details
        Session newSession = rm.login(student.getEmail(), "invalidpass");
        assertNull(newSession);
    }

    @Test
    public void testAddValidSession() {
        Session addedSession = rm.getSessionByToken(session.getToken());
        assertNull(addedSession);
        rm.addSession(session);
        addedSession = rm.getSessionByToken(session.getToken());
        assertEquals(session, addedSession);
    }

    @Test
    public void testAddNullUserSession() {
        Session addedSession = rm.getSessionByToken(nullUserSession.getToken());
        assertNull(addedSession);
        rm.addSession(nullUserSession);
        addedSession = rm.getSessionByToken(nullUserSession.getToken());
        assertEquals(nullUserSession, addedSession);
    }

    @Test
    public void testAddValidPropertyRating() {
        PropertyRating addedPropertyRating = rm.getPropertyRatingById(propertyRating.getId());
        assertNull(addedPropertyRating);
        rm.addPropertyRating(propertyRating);
        addedPropertyRating = rm.getPropertyRatingById(propertyRating.getId());
        assertEquals(propertyRating, addedPropertyRating);
    }

    @Test
    public void testAddEmptyPropertyRating() {
        PropertyRating addedPropertyRating = rm.getPropertyRatingById(emptyPropertyRating.getId());
        assertNull(addedPropertyRating);
        rm.addPropertyRating(emptyPropertyRating);
        addedPropertyRating = rm.getPropertyRatingById(emptyPropertyRating.getId());
        assertEquals(emptyPropertyRating, addedPropertyRating);
    }

    @Test
    public void testAddValidStudentRating() {
        StudentRating addedStudentRating = rm.getStudentRatingById(studentRating.getId());
        assertNull(addedStudentRating);
        rm.addStudentRating(studentRating);
        addedStudentRating = rm.getStudentRatingById(studentRating.getId());
        assertEquals(studentRating, addedStudentRating);
    }

    @Test
    public void testAddEmptyStudentRating() {
        StudentRating addedStudentRating = rm.getStudentRatingById(emptyStudentRating.getId());
        assertNull(addedStudentRating);
        rm.addStudentRating(emptyStudentRating);
        addedStudentRating = rm.getStudentRatingById(emptyStudentRating.getId());
        assertEquals(emptyStudentRating, addedStudentRating);
    }

    @Test
    public void testUpdateStudent() {
        rm.addStudent(student);
        student.updateFirstName("Dylan");
        student.updateLastName("Ortuno");
        student.updateEmail("example@hotmail.com");
        student.updateMaxTravelDistance(20.4);
        rm.updateStudent(student.getId(), student);
        Student modifiedStudent = rm.getStudentById(student.getId());
        assertEquals(modifiedStudent.getFirstName(), "Dylan");
        assertEquals(modifiedStudent.getLastName(), "Ortuno");
        assertEquals(modifiedStudent.getEmail(), "example@hotmail.com");
        assertEquals(modifiedStudent.getMaxTravelDistance(), 20.4, 0);
    }

    @Test
    public void testUpdatePropertyManager() {
        rm.addPropertyManager(propertyManager);
        propertyManager.updateFirstName("Santiago");
        propertyManager.updateLastName("Gutierrez");
        propertyManager.updateOfficeAddress("215 Spencer Pl");
        rm.updatePropertyManager(propertyManager.getId(), propertyManager);
        PropertyManager modifiedPropertyManager = rm.getPropertyManagerById(propertyManager.getId());
        assertEquals(modifiedPropertyManager.getFirstName(), "Santiago");
        assertEquals(modifiedPropertyManager.getLastName(), "Gutierrez");
        assertEquals(modifiedPropertyManager.getOfficeAddress(), "215 Spencer Pl");
    }

    @Test
    public void testUpdateProperty() {
        rm.addProperty(property);
        property.updateDamagesCost(650.50);
        property.updatePetsAllowed(true);
        rm.updateProperty(property.getId(), property);
        Property modifiedProperty = rm.getPropertyById(property.getId());
        assertEquals(modifiedProperty.getDamagesCost(), 650.50, 0);
        assertTrue(modifiedProperty.petsAllowed());
    }

    @Test
    public void testUpdateApartment() {
        rm.addApartment(apartment);
        apartment.updateApartmentNumber("546B");
        apartment.updateHasParking(false);
        rm.updateApartment(apartment.getId(), apartment);
        Apartment modifiedApartment = rm.getApartmentById(apartment.getId());
        assertEquals(modifiedApartment.getApartmentNumber(), "546B");
        assertFalse(modifiedApartment.hasParking());
    }

    @Test
    public void testUpdateTownhouse() {
        rm.addTownhouse(townhouse);
        townhouse.updateHasFence(true);
        townhouse.updateHasGarage(false);
        townhouse.updateDescription("new description");
        rm.updateTownhouse(townhouse.getId(), townhouse);
        Townhouse modifiedTownhouse = rm.getTownhouseById(townhouse.getId());
        assertTrue(modifiedTownhouse.hasFence());
        assertFalse(modifiedTownhouse.hasGarage());
        assertEquals(modifiedTownhouse.getDescription(), "new description");
    }

    @Test
    public void testUpdatePropertyRating() {
        rm.addPropertyRating(propertyRating);
        propertyRating.setNeighborhoodStars(3);
        propertyRating.setValueStars(5);
        propertyRating.setStars(4);
        rm.updatePropertyRating(propertyRating.getId(), propertyRating);
        PropertyRating modifiedPropertyRating = rm.getPropertyRatingById(propertyRating.getId());
        assertEquals(modifiedPropertyRating.getNeighborhoodStars(), 3);
        assertEquals(modifiedPropertyRating.getValueStars(), 5);
        assertEquals(modifiedPropertyRating.getStars(), 4);
    }

    @Test
    public void testUpdateStudentRating() {
        rm.addStudentRating(studentRating);
        studentRating.setDamagesValue(75.16);
        studentRating.setNumLatePayments(1);
        rm.updateStudentRating(studentRating.getId(), studentRating);
        StudentRating modifiedStudentRating = rm.getStudentRatingById(studentRating.getId());
        assertEquals(modifiedStudentRating.getDamagesValue(), 75.16, 0);
        assertEquals(modifiedStudentRating.getNumLatePayments(), 1);
    }

    @Test
    public void testRemoveStudent() {
        rm.addStudent(student);
        assertEquals(rm.getStudents().size(), 1);

        rm.removeStudent(student.getId());
        assertEquals(rm.getStudents().size(), 0);
    }

    @Test
    public void testRemovePropertyManager() {
        rm.addPropertyManager(propertyManager);
        assertEquals(rm.getPropertyManagers().size(), 1);

        rm.removePropertyManager(propertyManager.getId());
        assertEquals(rm.getPropertyManagers().size(), 0);
    }

    @Test
    public void testRemoveProperty() {
        rm.addProperty(property);
        assertEquals(rm.getProperties().size(), 1);

        rm.removeProperty(property.getId());
        assertEquals(rm.getProperties().size(), 0);
    }

    @Test
    public void testRemoveApartment() {
        rm.addApartment(apartment);
        assertEquals(rm.getApartments().size(), 1);

        rm.removeApartment(apartment.getId());
        assertEquals(rm.getApartments().size(), 0);
    }

    @Test
    public void testRemoveTownhouse() {
        rm.addTownhouse(townhouse);
        assertEquals(rm.getTownhouses().size(), 1);

        rm.removeTownhouse(townhouse.getId());
        assertEquals(rm.getTownhouses().size(), 0);
    }

    @Test
    public void testRemovePropertyRating() {
        rm.addPropertyRating(propertyRating);
        assertEquals(rm.getPropertyRatings().size(), 1);

        rm.removePropertyRating(propertyRating.getId());
        assertEquals(rm.getPropertyRatings().size(), 0);
    }

    @Test
    public void testRemoveStudentRating() {
        rm.addStudentRating(studentRating);
        assertEquals(rm.getStudentRatings().size(), 1);

        rm.removeStudentRating(studentRating.getId());
        assertEquals(rm.getStudentRatings().size(), 0);
    }

    @Test
    public void testClearUsers() {
        rm.addPropertyManager(propertyManager);
        rm.addStudent(student);
        assertEquals(rm.getPropertyManagers().size(), 1);
        assertEquals(rm.getStudents().size(), 1);

        rm.clearUsers();
        assertEquals(rm.getPropertyManagers().size(), 0);
        assertEquals(rm.getStudents().size(), 0);
    }

    @Test
    public void testClearProperties() {
        rm.addProperty(property);
        assertEquals(rm.getProperties().size(), 1);

        rm.clearProperties();
        assertEquals(rm.getProperties().size(), 0);
    }

    @Test
    public void testClearListings() {
        rm.addApartment(apartment);
        rm.addTownhouse(townhouse);
        assertEquals(rm.getApartments().size(), 1);
        assertEquals(rm.getTownhouses().size(), 1);

        rm.clearListings();
        assertEquals(rm.getApartments().size(), 0);
        assertEquals(rm.getTownhouses().size(), 0);
    }

    @Test
    public void testClearSessions() {
        rm.addSession(session);
        assertEquals(rm.getSessions().size(), 1);

        rm.clearSessions();
        assertEquals(rm.getSessions().size(), 0);
    }

    @Test
    public void testClearRatings() {
        rm.addPropertyRating(propertyRating);
        rm.addStudentRating(studentRating);
        assertEquals(rm.getPropertyRatings().size(), 1);
        assertEquals(rm.getStudentRatings().size(), 1);

        rm.clearRatings();
        assertEquals(rm.getPropertyRatings().size(), 0);
        assertEquals(rm.getStudentRatings().size(), 0);
    }
}
