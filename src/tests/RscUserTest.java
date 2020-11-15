package tests;

import housingapp.SysConst;
import housingapp.query.ResourceManager;
import housingapp.resources.RscUser;
import housingapp.user.PropertyManager;
import housingapp.user.Student;
import housingapp.user.User;
import org.json.simple.JSONObject;
import org.junit.*;

import java.util.ArrayList;
import java.util.Map;
import java.util.UUID;

import static org.junit.Assert.*;

public class RscUserTest {

    private PropertyManager propertyManager = TestConst.PROPERTY_MANAGER_VALID;
    private Student student = TestConst.STUDENT_VALID;
    private PropertyManager emptyPropertyManager = TestConst.PROPERTY_MANAGER_EMPTY;
    private Student emptyStudent = TestConst.STUDENT_EMPTY;
    private static ResourceManager rm = ResourceManager.getInstance();

    @BeforeClass
    public static void oneTimeSetup() {

    }

    @AfterClass
    public static void oneTimeTearDown() {

    }

    @Before
    public void setup() {
        // clear users data file
        rm.clearUsers();
    }

    @After
    public void tearDown() {

    }

    @Test
    public void testGetUsersEmpty() {
        Map<String, ArrayList<User>> users = RscUser.getUsers();
        assertNotNull(users);
        assertEquals(users.get("propertyManagers").size(), 0);
        assertEquals(users.get("students").size(), 0);
    }

    @Test
    public void testGetUsersPopulated() {
        rm.addPropertyManager(propertyManager);
        rm.addStudent(student);
        Map<String, ArrayList<User>> users = RscUser.getUsers();
        assertNotNull(users);
        assertEquals(users.get("propertyManagers").size(), 1);
        assertEquals(users.get("propertyManagers").get(0).getId(), propertyManager.getId());
        assertEquals(users.get("students").size(), 1);
        assertEquals(users.get("students").get(0).getId(), student.getId());
    }

    @Test
    public void testWriteUsersEmpty() {
        rm.clearUsers();
        RscUser.writeUsers();
        Map<String, ArrayList<User>> users = RscUser.getUsers();
        assertNotNull(users);
        assertEquals(users.get("propertyManagers").size(), 0);
        assertEquals(users.get("students").size(), 0);
    }

    @Test
    public void testWriteUsersPopulated() {
        rm.addPropertyManager(propertyManager);
        rm.addStudent(student);
        RscUser.writeUsers();
        Map<String, ArrayList<User>> users = RscUser.getUsers();
        assertNotNull(users);
        assertEquals(users.get("propertyManagers").size(), 1);
        assertEquals(users.get("propertyManagers").get(0).getId(), propertyManager.getId());
        assertEquals(users.get("students").size(), 1);
        assertEquals(users.get("students").get(0).getId(), student.getId());
    }

    @Test
    public void testGetValidPropertyManagerJSON() {
        JSONObject validPropertyManagerJSON = RscUser.getPropertyManagerJSON(propertyManager);

        assertEquals(UUID.fromString((String) validPropertyManagerJSON.get(SysConst.USER_ID)), propertyManager.getId());
        assertEquals(validPropertyManagerJSON.get(SysConst.USER_FIRST_NAME), propertyManager.getFirstName());
        assertEquals(validPropertyManagerJSON.get(SysConst.USER_LAST_NAME), propertyManager.getLastName());
        assertEquals(validPropertyManagerJSON.get(SysConst.USER_PHONE), propertyManager.getPhone());
        assertEquals(validPropertyManagerJSON.get(SysConst.USER_EMAIL), propertyManager.getEmail());
        assertEquals(validPropertyManagerJSON.get(SysConst.USER_PASSWORD), propertyManager.getPassword());

        assertEquals(validPropertyManagerJSON.get(SysConst.PROPERTY_MANAGER_USER_OFFICE_ADDRESS), propertyManager.getOfficeAddress());
    }

    @Test
    public void testGetEmptyPropertyManagerJSON() {
        JSONObject emptyPropertyManagerJSON = RscUser.getPropertyManagerJSON(emptyPropertyManager);

        assertEquals(emptyPropertyManagerJSON.get(SysConst.USER_FIRST_NAME), "");
        assertEquals(emptyPropertyManagerJSON.get(SysConst.USER_LAST_NAME), "");
        assertEquals(emptyPropertyManagerJSON.get(SysConst.USER_PHONE), "");
        assertEquals(emptyPropertyManagerJSON.get(SysConst.USER_EMAIL), "");

        assertEquals(emptyPropertyManagerJSON.get(SysConst.PROPERTY_MANAGER_USER_OFFICE_ADDRESS), "");
    }

    @Test
    public void testGetValidStudentJSON() {
        JSONObject validStudentJSON = RscUser.getStudentJSON(student);

        assertEquals(UUID.fromString((String) validStudentJSON.get(SysConst.USER_ID)), student.getId());
        assertEquals(validStudentJSON.get(SysConst.USER_FIRST_NAME), student.getFirstName());
        assertEquals(validStudentJSON.get(SysConst.USER_LAST_NAME), student.getLastName());
        assertEquals(validStudentJSON.get(SysConst.USER_PHONE), student.getPhone());
        assertEquals(validStudentJSON.get(SysConst.USER_EMAIL), student.getEmail());
        assertEquals(validStudentJSON.get(SysConst.USER_PASSWORD), student.getPassword());

        assertEquals(validStudentJSON.get(SysConst.STUDENT_USER_HAS_PETS), student.hasPets());
        assertEquals(validStudentJSON.get(SysConst.STUDENT_USER_PRICE_RANGE_LOWER), student.getPriceRangeLower());
        assertEquals(validStudentJSON.get(SysConst.STUDENT_USER_PRICE_RANGE_UPPER), student.getPriceRangeUpper());
        assertEquals(validStudentJSON.get(SysConst.STUDENT_USER_MAX_TRAVEL_DISTANCE), student.getMaxTravelDistance());
        assertEquals(validStudentJSON.get(SysConst.STUDENT_USER_MIN_ROOMMATES), student.getMinRoommates());
        assertEquals(validStudentJSON.get(SysConst.STUDENT_USER_MAX_ROOMMATES), student.getMaxRoommates());
    }

    @Test
    public void testGetEmptyStudentJSON() {
        JSONObject emptyStudentJSON = RscUser.getStudentJSON(emptyStudent);

        assertEquals(emptyStudentJSON.get(SysConst.USER_FIRST_NAME), "");
        assertEquals(emptyStudentJSON.get(SysConst.USER_LAST_NAME), "");
        assertEquals(emptyStudentJSON.get(SysConst.USER_PHONE), "");
        assertEquals(emptyStudentJSON.get(SysConst.USER_EMAIL), "");

        assertEquals(emptyStudentJSON.get(SysConst.STUDENT_USER_HAS_PETS), false);
        assertEquals(emptyStudentJSON.get(SysConst.STUDENT_USER_PRICE_RANGE_LOWER), 0.0);
        assertEquals(emptyStudentJSON.get(SysConst.STUDENT_USER_PRICE_RANGE_UPPER), 0.0);
        assertEquals(emptyStudentJSON.get(SysConst.STUDENT_USER_MAX_TRAVEL_DISTANCE), 0.0);
        assertEquals(emptyStudentJSON.get(SysConst.STUDENT_USER_MIN_ROOMMATES), 0);
        assertEquals(emptyStudentJSON.get(SysConst.STUDENT_USER_MAX_ROOMMATES), 0);
    }
}
