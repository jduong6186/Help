package tests;

import housingapp.SysConst;
import housingapp.query.ResourceManager;
import housingapp.rating.Rating;
import housingapp.rating.StudentRating;
import housingapp.resources.RscProperty;
import housingapp.resources.RscRating;
import org.json.simple.JSONObject;
import org.junit.*;

import java.util.ArrayList;
import java.util.Map;
import java.util.UUID;

import static org.junit.Assert.*;

public class StudentRatingTest {
	
    private StudentRating studentRating = TestConst.STUDENT_RATING_VALID;

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
    	studentRating.setStars(test);
    	assertEquals(test, studentRating.getStars());
    }
    
    @Test
    public void testSetComment() {
    	String test = "test";
    	studentRating.setComment(test);
    	assertEquals(test, studentRating.getComment());
    }

    @Test
    public void testSetNumLatePayments() {
    	int test = 1;
    	studentRating.setNumLatePayments(test);
    	assertEquals(test, studentRating.getNumLatePayments());
    }
    
    /**
     * The third variable is a delta, since assetEquals with two doubles is deprecated
     * It essentially lets the method know that the numbers can be in a _ range of the value
     */
    @Test
    public void testSetDamagesValue() {
    	double test = 1;
    	studentRating.setDamagesValue(test);
    	assertEquals(test, studentRating.getDamagesValue(), .00001);
    }
}
