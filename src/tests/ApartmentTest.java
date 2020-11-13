package tests;

import housingapp.housing.Apartment;
import org.junit.*;
import static org.junit.Assert.assertEquals;

public class ApartmentTest {

    private Apartment apartment = TestConst.APARTMENT_VALID;

    @BeforeClass
    public static void oneTimeSetup() {

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
    public void testGetDetailsSuccess() {
        String details = apartment.getDetails();
        System.out.println(details);
        String trueDetailsStr = "";
        assertEquals(details, trueDetailsStr);
    }
}
