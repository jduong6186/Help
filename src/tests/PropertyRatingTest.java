package tests;

import housingapp.SysConst;
import housingapp.query.ResourceManager;
import housingapp.rating.PropertyRating;
import housingapp.rating.Rating;
import housingapp.resources.RscProperty;
import housingapp.resources.RscRating;
import org.json.simple.JSONObject;
import org.junit.*;

import java.util.ArrayList;
import java.util.Map;
import java.util.UUID;

import static org.junit.Assert.*;

public class PropertyRatingTest {
	
    private PropertyRating propertyRating = TestConst.PROPERTY_RATING_VALID;

    @BeforeClass
    public static void oneTimeSetup() {
    	RscRating.writeRatings();
    }

    @AfterClass
    public static void oneTimeTearDown() {

    }

    @Before
    public void setup() {
    }

    @After
    public void tearDown() {

    }

    @Test
    public void testSetStars() {
    	int test = 1;
    	propertyRating.setStars(test);
    	assertEquals(test, propertyRating.getStars());
    }
    
    @Test
    public void testSetComment() {
    	String test = "test";
    	propertyRating.setComment(test);
    	assertEquals(test, propertyRating.getComment());
    }
    
    @Test
    public void testSetManagementStars() {
    	int test = 1;
    	propertyRating.setManagementStars(test);
    	assertEquals(test, propertyRating.getManagementStars());
    }
    
    @Test
    public void testSetNeighborhoodStars() {
    	int test = 1;
    	propertyRating.setNeighborhoodStars(test);
    	assertEquals(test, propertyRating.getNeighborhoodStars());
    }
}
