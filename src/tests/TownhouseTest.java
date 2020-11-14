package tests;

import housingapp.housing.Townhouse;
import org.junit.*;
import static org.junit.Assert.assertEquals;

public class TownhouseTest {

    private Townhouse townhouse = TestConst.TOWNHOUSE_VALID;

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
        String details = townhouse.getDetails();
        System.out.println(details);
        String trueDetailsStr = "";
        assertEquals(details, trueDetailsStr);
    }
}
