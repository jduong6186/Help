package tests;

import housingapp.housing.Listing;
import housingapp.housing.Property;
import housingapp.query.ResourceManager;
import housingapp.rating.Rating;
import housingapp.resources.RscProperty;
import org.junit.*;

import java.util.ArrayList;
import java.util.UUID;

import static org.junit.Assert.*;

public class PropertyTest {

    private Property property = TestConst.PROPERTY_VALID;
    private ResourceManager rm = ResourceManager.getInstance();

    @BeforeClass
    public static void oneTimeSetup() {

        RscProperty.writeProperties();
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
    public void testAssociateRatingSuccess() {
        Rating rating = TestConst.PROPERTY_RATING_VALID;
        property.associateRating(rating.getId());
        ArrayList<UUID> propertyRatings = property.getRatings();
        for (UUID ratingId : propertyRatings) {
            assertNotNull(rm.getRatingById(ratingId));
        }
    }

    @Test
    public void testAssociateRatingFail() {
        UUID invalidRatingUUID = UUID.randomUUID();
        property.associateRating(invalidRatingUUID);
        ArrayList<UUID> propertyRatings = property.getRatings();
        for (UUID ratingId : propertyRatings) {
            if (ratingId.equals(invalidRatingUUID)) {
                assertNull(rm.getRatingById(ratingId));
            } else {
                assertNotNull(rm.getRatingById(ratingId));
            }
        }
    }

    @Test
    public void testAssociateListingSuccess() {
        Listing listing = TestConst.APARTMENT_VALID;
        property.associateListing(listing.getId());
        ArrayList<UUID> propertyListings = property.getListings();
        for (UUID listingId : propertyListings) {
            assertNotNull(rm.getRatingById(listingId));
        }
    }

    @Test
    public void testAssociateListingFail() {
        UUID invalidListingUUID = UUID.randomUUID();
        property.associateListing(invalidListingUUID);
        ArrayList<UUID> propertyListings = property.getListings();
        for (UUID listingId : propertyListings) {
            if (listingId.equals(invalidListingUUID)) {
                assertNull(rm.getRatingById(listingId));
            } else {
                assertNotNull(rm.getRatingById(listingId));
            }
        }
    }
}
