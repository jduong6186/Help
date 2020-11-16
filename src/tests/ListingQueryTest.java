package tests;

import housingapp.housing.Apartment;
import housingapp.housing.Listing;
import housingapp.housing.Property;
import housingapp.query.ListingQuery;
import housingapp.query.ResourceManager;
import housingapp.user.Student;
import org.junit.*;

import java.util.ArrayList;
import java.util.UUID;

import static org.junit.Assert.*;

public class ListingQueryTest {

    private static ResourceManager rm = ResourceManager.getInstance();
    private static ListingQuery query = ListingQuery.getInstance();

    private static Student student = TestConst.STUDENT_VALID;
    private static Student emptyStudent = TestConst.STUDENT_EMPTY;

    private static Apartment apartment = TestConst.APARTMENT_VALID;

    private static Property property = TestConst.PROPERTY_VALID;

    @BeforeClass
    public static void oneTimeSetup() {
        rm.clearListings();
        rm.clearProperties();
        rm.addApartment(apartment);
        rm.addProperty(property);
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
    public void testGetListingsByRecommendedValidStudent() {
        ArrayList<Listing> listings = query.getListingsByRecommended(student);
        assertEquals(listings.size(), 1);
    }

    @Test
    public void testGetListingsByRecommendedEmptyStudent() {
        ArrayList<Listing> listings = query.getListingsByRecommended(emptyStudent);
        assertEquals(listings.size(), 0);
    }

    @Test
    public void testGetListingsByValidProperty() {
        ArrayList<Listing> listings = query.getListingsByProperty(rm.getListings(), property.getId());
        assertEquals(listings.size(), 1);
    }

    @Test
    public void testGetListingsByInvalidProperty() {
        ArrayList<Listing> listings = query.getListingsByProperty(rm.getListings(), UUID.randomUUID());
        assertEquals(listings.size(), 0);
    }

    @Test
    public void testGetListingsByValidDescription() {
        ArrayList<Listing> listings = query.getListingsByDescription(rm.getListings(), apartment.getDescription());
        assertEquals(listings.size(), 1);
    }

    @Test
    public void testGetListingsByInvalidDescription() {
        ArrayList<Listing> listings = query.getListingsByDescription(rm.getListings(), "invalid description substr");
        assertEquals(listings.size(), 0);
    }

    @Test
    public void testGetListingsByValidPrice() {
        ArrayList<Listing> listings = query.getListingsByPrice(rm.getListings(), student.getPriceRangeLower(), student.getPriceRangeUpper());
        assertEquals(listings.size(), 1);
    }

    @Test
    public void testGetListingsByInvalidPrice() {
        ArrayList<Listing> listings = query.getListingsByPrice(rm.getListings(), 1100.00, 1400.00);
        assertEquals(listings.size(), 0);
    }

    @Test
    public void testGetListingsByValidMaxTravelDistance() {
        ArrayList<Listing> listings = query.getListingsByMaxTravelDistance(rm.getListings(), student.getMaxTravelDistance());
        assertEquals(listings.size(), 1);
    }

    @Test
    public void testGetListingsByInvalidMaxTravelDistance() {
        ArrayList<Listing> listings = query.getListingsByMaxTravelDistance(rm.getListings(), 0.0);
        assertEquals(listings.size(), 0);
    }

    @Test
    public void testGetListingsByValidLeaseMonths() {
        ArrayList<Listing> listings = query.getListingsByLeaseMonths(rm.getListings(), 0, 12);
        assertEquals(listings.size(), 1);
    }

    @Test
    public void testGetListingsByInvalidLeaseMonths() {
        ArrayList<Listing> listings = query.getListingsByLeaseMonths(rm.getListings(), 18, 24);
        assertEquals(listings.size(), 0);
    }

    @Test
    public void testGetListingsByValidSquareFootage() {
        ArrayList<Listing> listings = query.getListingsBySquareFootage(rm.getListings(), 200, 300);
        assertEquals(listings.size(), 1);
    }

    @Test
    public void testGetListingsByInvalidSquareFootage() {
        ArrayList<Listing> listings = query.getListingsBySquareFootage(rm.getListings(), 400, 500);
        assertEquals(listings.size(), 0);
    }

    @Test
    public void testGetListingsByValidPetsAllowed() {
        ArrayList<Listing> listings = query.getListingsByPetsAllowed(rm.getListings(), student.hasPets());
        assertEquals(listings.size(), 1);
    }

    @Test
    public void testGetListingsByInvalidPetsAllowed() {
        ArrayList<Listing> listings = query.getListingsByPetsAllowed(rm.getListings(), true);
        assertEquals(listings.size(), 0);
    }

    @Test
    public void testGetListingsByValidHasWasher() {
        ArrayList<Listing> listings = query.getListingsByHasWasher(rm.getListings(), true);
        assertEquals(listings.size(), 1);
    }

    @Test
    public void testGetListingsByInvalidHasWasher() {
        ArrayList<Listing> listings = query.getListingsByHasWasher(rm.getListings(), false);
        assertEquals(listings.size(), 0);
    }

    @Test
    public void testGetListingsByValidHasDryer() {
        ArrayList<Listing> listings = query.getListingsByHasDryer(rm.getListings(), true);
        assertEquals(listings.size(), 1);
    }

    @Test
    public void testGetListingsByInvalidHasDryer() {
        ArrayList<Listing> listings = query.getListingsByHasDryer(rm.getListings(), false);
        assertEquals(listings.size(), 0);
    }

    @Test
    public void testGetListingsByIsSublease() {
        ArrayList<Listing> listings = query.getListingsByIsSublease(rm.getListings());
        assertEquals(listings.size(), 0);
    }

    @Test
    public void testGetListingsByValidUtilitiesIncluded() {
        ArrayList<Listing> listings = query.getListingsByUtilitiesIncluded(rm.getListings(), true);
        assertEquals(listings.size(), 1);
    }

    @Test
    public void testGetListingsByInvalidUtilitiesIncluded() {
        ArrayList<Listing> listings = query.getListingsByUtilitiesIncluded(rm.getListings(), false);
        assertEquals(listings.size(), 0);
    }

    @Test
    public void testGetListingsByValidNumBedrooms() {
        ArrayList<Listing> listings = query.getListingsByNumBedrooms(rm.getListings(), student.getMinRoommates()+1, student.getMaxRoommates()+1);
        assertEquals(listings.size(), 1);
    }

    @Test
    public void testGetListingsByInvalidNumBedrooms() {
        ArrayList<Listing> listings = query.getListingsByNumBedrooms(rm.getListings(), 3, 4);
        assertEquals(listings.size(), 0);
    }

    @Test
    public void testGetListingsByValidNumBathrooms() {
        ArrayList<Listing> listings = query.getListingsByNumBathrooms(rm.getListings(), student.getMinRoommates()+1, student.getMaxRoommates()+1);
        assertEquals(listings.size(), 1);
    }

    @Test
    public void testGetListingsByInvalidNumBathrooms() {
        ArrayList<Listing> listings = query.getListingsByNumBathrooms(rm.getListings(), 3, 4);
        assertEquals(listings.size(), 0);
    }

    @Test
    public void testGetListingsByValidHasShuttle() {
        ArrayList<Listing> listings = query.getListingsByHasShuttle(rm.getListings(), true);
        assertEquals(listings.size(), 1);
    }

    @Test
    public void testGetListingsByInvalidHasShuttle() {
        ArrayList<Listing> listings = query.getListingsByHasShuttle(rm.getListings(), false);
        assertEquals(listings.size(), 0);
    }

    @Test
    public void testGetListingsByAvailable() {
        ArrayList<Listing> listings = query.getListingsByAvailable(rm.getListings());
        assertEquals(listings.size(), 1);
    }
}
