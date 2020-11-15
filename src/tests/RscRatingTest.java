package tests;

import housingapp.SysConst;
import housingapp.query.ResourceManager;
import housingapp.rating.PropertyRating;
import housingapp.rating.Rating;
import housingapp.rating.StudentRating;
import housingapp.resources.RscRating;
import org.json.simple.JSONObject;
import org.junit.*;

import java.util.ArrayList;
import java.util.Map;
import java.util.UUID;

import static org.junit.Assert.*;

public class RscRatingTest {

    private PropertyRating propertyRating = TestConst.PROPERTY_RATING_VALID;
    private StudentRating studentRating = TestConst.STUDENT_RATING_VALID;
    private PropertyRating emptyPropertyRating = TestConst.PROPERTY_RATING_EMPTY;
    private StudentRating emptyStudentRating = TestConst.STUDENT_RATING_EMPTY;
    private static ResourceManager rm = ResourceManager.getInstance();

    @BeforeClass
    public static void oneTimeSetup() {

    }

    @AfterClass
    public static void oneTimeTearDown() {

    }

    @Before
    public void setup() {
        // clear ratings data file
        rm.clearRatings();
    }

    @After
    public void tearDown() {

    }

    @Test
    public void testGetRatingsEmpty() {
        Map<String, ArrayList<Rating>> ratings = RscRating.getRatings();
        assertNotNull(ratings);
        assertEquals(ratings.get("propertyRatings").size(), 0);
        assertEquals(ratings.get("studentRatings").size(), 0);
    }

    @Test
    public void testGetRatingsPopulated() {
        rm.addPropertyRating(propertyRating);
        rm.addStudentRating(studentRating);
        Map<String, ArrayList<Rating>> ratings = RscRating.getRatings();
        assertNotNull(ratings);
        assertEquals(ratings.get("propertyRatings").size(), 1);
        assertEquals(ratings.get("propertyRatings").get(0).getId(), propertyRating.getId());
        assertEquals(ratings.get("studentRatings").size(), 1);
        assertEquals(ratings.get("studentRatings").get(0).getId(), studentRating.getId());
    }

    @Test
    public void testWriteRatingsEmpty() {
        rm.clearRatings();
        RscRating.writeRatings();
        Map<String, ArrayList<Rating>> ratings = RscRating.getRatings();
        assertNotNull(ratings);
        assertEquals(ratings.get("propertyRatings").size(), 0);
        assertEquals(ratings.get("studentRatings").size(), 0);
    }

    @Test
    public void testWriteRatingsPopulated() {
        rm.addPropertyRating(propertyRating);
        rm.addStudentRating(studentRating);
        RscRating.writeRatings();
        Map<String, ArrayList<Rating>> ratings = RscRating.getRatings();
        assertNotNull(ratings);
        assertEquals(ratings.get("propertyRatings").size(), 1);
        assertEquals(ratings.get("propertyRatings").get(0).getId(), propertyRating.getId());
        assertEquals(ratings.get("studentRatings").size(), 1);
        assertEquals(ratings.get("studentRatings").get(0).getId(), studentRating.getId());
    }

    @Test
    public void testGetValidPropertyRatingJSON() {
        JSONObject validPropertyRatingJSON = RscRating.getPropertyRatingJSON(propertyRating);

        assertEquals(UUID.fromString((String) validPropertyRatingJSON.get(SysConst.RATING_ID)), propertyRating.getId());
        assertEquals(validPropertyRatingJSON.get(SysConst.RATING_STARS), propertyRating.getStars());
        assertEquals(validPropertyRatingJSON.get(SysConst.RATING_COMMENT), propertyRating.getComment());
        assertEquals(validPropertyRatingJSON.get(SysConst.PROPERTY_RATING_VALUE_STARS), propertyRating.getValueStars());
        assertEquals(validPropertyRatingJSON.get(SysConst.PROPERTY_RATING_MANAGEMENT_STARS), propertyRating.getManagementStars());
        assertEquals(validPropertyRatingJSON.get(SysConst.PROPERTY_RATING_NEIGHBORHOOD_STARS), propertyRating.getNeighborhoodStars());
    }

    @Test
    public void testGetEmptyPropertyRatingJSON() {
        JSONObject emptyPropertyRatingJSON = RscRating.getPropertyRatingJSON(emptyPropertyRating);

        assertEquals(emptyPropertyRatingJSON.get(SysConst.RATING_STARS), 0);
        assertEquals(emptyPropertyRatingJSON.get(SysConst.RATING_COMMENT), "");
        assertEquals(emptyPropertyRatingJSON.get(SysConst.PROPERTY_RATING_VALUE_STARS), 0);
        assertEquals(emptyPropertyRatingJSON.get(SysConst.PROPERTY_RATING_MANAGEMENT_STARS), 0);
        assertEquals(emptyPropertyRatingJSON.get(SysConst.PROPERTY_RATING_NEIGHBORHOOD_STARS), 0);
    }

    @Test
    public void testGetValidStudentRatingJSON() {
        JSONObject validStudentRatingJSON = RscRating.getStudentRatingJSON(studentRating);

        assertEquals(UUID.fromString((String) validStudentRatingJSON.get(SysConst.RATING_ID)), studentRating.getId());
        assertEquals(validStudentRatingJSON.get(SysConst.RATING_STARS), studentRating.getStars());
        assertEquals(validStudentRatingJSON.get(SysConst.RATING_COMMENT), studentRating.getComment());
        assertEquals(validStudentRatingJSON.get(SysConst.STUDENT_RATING_NUM_LATE_PAYMENTS), studentRating.getNumLatePayments());
        assertEquals(validStudentRatingJSON.get(SysConst.STUDENT_RATING_DAMAGES_VALUE), studentRating.getDamagesValue());
    }

    @Test
    public void testGetEmptyStudentRatingJSON() {
        JSONObject emptyStudentRatingJSON = RscRating.getStudentRatingJSON(emptyStudentRating);

        assertEquals(emptyStudentRatingJSON.get(SysConst.RATING_STARS), 0);
        assertEquals(emptyStudentRatingJSON.get(SysConst.RATING_COMMENT), "");
        assertEquals(emptyStudentRatingJSON.get(SysConst.STUDENT_RATING_NUM_LATE_PAYMENTS), 0);
        assertEquals(emptyStudentRatingJSON.get(SysConst.STUDENT_RATING_DAMAGES_VALUE), 0.0);
    }
}
