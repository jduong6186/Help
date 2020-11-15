package tests;

import housingapp.SysConst;
import housingapp.housing.Apartment;
import housingapp.housing.Listing;
import housingapp.housing.Townhouse;
import housingapp.query.ResourceManager;
import housingapp.rating.PropertyRating;
import housingapp.rating.Rating;
import housingapp.rating.StudentRating;
import housingapp.resources.RscListing;
import housingapp.resources.RscRating;
import org.json.simple.JSONObject;
import org.junit.*;

import java.util.ArrayList;
import java.util.Map;
import java.util.UUID;

import static org.junit.Assert.*;

public class RscListingTest {

    private Apartment apartment = TestConst.APARTMENT_VALID;
    private Townhouse townhouse = TestConst.TOWNHOUSE_VALID;
    private Apartment emptyApartment = TestConst.APARTMENT_EMPTY;
    private Townhouse emptyTownhouse = TestConst.TOWNHOUSE_EMPTY;
    private static ResourceManager rm = ResourceManager.getInstance();

    @BeforeClass
    public static void oneTimeSetup() {

    }

    @AfterClass
    public static void oneTimeTearDown() {

    }

    @Before
    public void setup() {
        // clear listings data file
        rm.clearListings();
    }

    @After
    public void tearDown() {

    }

    @Test
    public void testGetListingsEmpty() {
        Map<String, ArrayList<Listing>> listings = RscListing.getListings();
        assertNotNull(listings);
        assertEquals(listings.get("apartments").size(), 0);
        assertEquals(listings.get("townhouses").size(), 0);
    }

    @Test
    public void testGetListingsPopulated() {
        rm.addApartment(apartment);
        rm.addTownhouse(townhouse);
        Map<String, ArrayList<Listing>> listings = RscListing.getListings();
        assertNotNull(listings);
        assertEquals(listings.get("apartments").size(), 1);
        assertEquals(listings.get("apartments").get(0).getId(), apartment.getId());
        assertEquals(listings.get("townhouses").size(), 1);
        assertEquals(listings.get("townhouses").get(0).getId(), townhouse.getId());
    }

    @Test
    public void testWriteListingsEmpty() {
        rm.clearListings();
        RscListing.writeListings();
        Map<String, ArrayList<Listing>> listings = RscListing.getListings();
        assertNotNull(listings);
        assertEquals(listings.get("apartments").size(), 0);
        assertEquals(listings.get("townhouses").size(), 0);
    }

    @Test
    public void testWriteListingsPopulated() {
        rm.addApartment(apartment);
        rm.addTownhouse(townhouse);
        RscListing.writeListings();
        Map<String, ArrayList<Listing>> listings = RscListing.getListings();
        assertNotNull(listings);
        assertEquals(listings.get("apartments").size(), 1);
        assertEquals(listings.get("apartments").get(0).getId(), apartment.getId());
        assertEquals(listings.get("townhouses").size(), 1);
        assertEquals(listings.get("townhouses").get(0).getId(), townhouse.getId());
    }

    @Test
    public void testGetValidApartmentJSON() {
        JSONObject validApartmentJSON = RscListing.getApartmentJSON(apartment);

        assertEquals(UUID.fromString((String) validApartmentJSON.get(SysConst.LISTING_ID)), apartment.getId());
        assertEquals(UUID.fromString((String) validApartmentJSON.get(SysConst.LISTING_PROPERTY_ID)), apartment.getPropertyId());
        assertEquals(validApartmentJSON.get(SysConst.LISTING_DESCRIPTION), apartment.getDescription());
        assertEquals(validApartmentJSON.get(SysConst.LISTING_PRICE), apartment.getPrice());
        assertEquals(validApartmentJSON.get(SysConst.LISTING_LEASE_MONTHS), apartment.getLeaseMonths());
        assertEquals(validApartmentJSON.get(SysConst.LISTING_SQUARE_FOOTAGE), apartment.getSquareFootage());
        assertEquals(validApartmentJSON.get(SysConst.LISTING_IS_SUBLEASE), apartment.isSublease());
        assertEquals(validApartmentJSON.get(SysConst.LISTING_UTILITIES_INCLUDED), apartment.utilitiesIncluded());
        assertEquals(validApartmentJSON.get(SysConst.LISTING_NUM_BEDROOMS), apartment.getNumBedrooms());
        assertEquals(validApartmentJSON.get(SysConst.LISTING_NUM_BATHROOMS), apartment.getNumBathrooms());
        assertEquals(validApartmentJSON.get(SysConst.LISTING_HAS_SHUTTLE), apartment.hasShuttle());
        assertEquals(validApartmentJSON.get(SysConst.LISTING_AVAILABLE), apartment.isAvailable());
        assertEquals(validApartmentJSON.get("hasWasher"), apartment.hasWasher());
        assertEquals(validApartmentJSON.get("hasDryer"), apartment.hasDryer());

        assertEquals(validApartmentJSON.get("apartmentNumber"), apartment.getApartmentNumber());
        assertEquals(validApartmentJSON.get("hasParking"), apartment.hasParking());
    }

    @Test
    public void testGetEmptyApartmentJSON() {
        JSONObject emptyApartmentJSON = RscListing.getApartmentJSON(emptyApartment);

        assertNull(emptyApartmentJSON.get(SysConst.LISTING_PROPERTY_ID));
        assertEquals(emptyApartmentJSON.get(SysConst.LISTING_DESCRIPTION), "");
        assertEquals(emptyApartmentJSON.get(SysConst.LISTING_PRICE), 0.0);
        assertEquals(emptyApartmentJSON.get(SysConst.LISTING_LEASE_MONTHS), 0);
        assertEquals(emptyApartmentJSON.get(SysConst.LISTING_SQUARE_FOOTAGE), 0.0);
        assertEquals(emptyApartmentJSON.get(SysConst.LISTING_IS_SUBLEASE), false);
        assertEquals(emptyApartmentJSON.get(SysConst.LISTING_UTILITIES_INCLUDED), false);
        assertEquals(emptyApartmentJSON.get(SysConst.LISTING_NUM_BEDROOMS), 0);
        assertEquals(emptyApartmentJSON.get(SysConst.LISTING_NUM_BATHROOMS), 0);
        assertEquals(emptyApartmentJSON.get(SysConst.LISTING_HAS_SHUTTLE), false);
        assertEquals(emptyApartmentJSON.get(SysConst.LISTING_AVAILABLE), false);
        assertEquals(emptyApartmentJSON.get("hasWasher"), false);
        assertEquals(emptyApartmentJSON.get("hasDryer"), false);

        assertEquals(emptyApartmentJSON.get("apartmentNumber"), "");
        assertEquals(emptyApartmentJSON.get("hasParking"), false);
    }

    @Test
    public void testGetValidTownhouseJSON() {
        JSONObject validTownhouseJSON = RscListing.getTownhouseJSON(townhouse);

        assertEquals(UUID.fromString((String) validTownhouseJSON.get(SysConst.LISTING_ID)), townhouse.getId());
        assertEquals(UUID.fromString((String) validTownhouseJSON.get(SysConst.LISTING_PROPERTY_ID)), townhouse.getPropertyId());
        assertEquals(validTownhouseJSON.get(SysConst.LISTING_DESCRIPTION), townhouse.getDescription());
        assertEquals(validTownhouseJSON.get(SysConst.LISTING_PRICE), townhouse.getPrice());
        assertEquals(validTownhouseJSON.get(SysConst.LISTING_LEASE_MONTHS), townhouse.getLeaseMonths());
        assertEquals(validTownhouseJSON.get(SysConst.LISTING_SQUARE_FOOTAGE), townhouse.getSquareFootage());
        assertEquals(validTownhouseJSON.get(SysConst.LISTING_IS_SUBLEASE), townhouse.isSublease());
        assertEquals(validTownhouseJSON.get(SysConst.LISTING_UTILITIES_INCLUDED), townhouse.utilitiesIncluded());
        assertEquals(validTownhouseJSON.get(SysConst.LISTING_NUM_BEDROOMS), townhouse.getNumBedrooms());
        assertEquals(validTownhouseJSON.get(SysConst.LISTING_NUM_BATHROOMS), townhouse.getNumBathrooms());
        assertEquals(validTownhouseJSON.get(SysConst.LISTING_HAS_SHUTTLE), townhouse.hasShuttle());
        assertEquals(validTownhouseJSON.get(SysConst.LISTING_AVAILABLE), townhouse.isAvailable());
        assertEquals(validTownhouseJSON.get("hasWasher"), townhouse.hasWasher());
        assertEquals(validTownhouseJSON.get("hasDryer"), townhouse.hasDryer());

        assertEquals(validTownhouseJSON.get("hasGarage"), townhouse.hasGarage());
        assertEquals(validTownhouseJSON.get("hasDriveway"), townhouse.hasDriveway());
        assertEquals(validTownhouseJSON.get("hasYard"), townhouse.hasYard());
        assertEquals(validTownhouseJSON.get("hasFence"), townhouse.hasFence());
    }

    @Test
    public void testGetEmptyTownhouseJSON() {
        JSONObject emptyTownhouseJSON = RscListing.getTownhouseJSON(emptyTownhouse);

        assertNull(emptyTownhouseJSON.get(SysConst.LISTING_PROPERTY_ID));
        assertEquals(emptyTownhouseJSON.get(SysConst.LISTING_DESCRIPTION), "");
        assertEquals(emptyTownhouseJSON.get(SysConst.LISTING_PRICE), 0.0);
        assertEquals(emptyTownhouseJSON.get(SysConst.LISTING_LEASE_MONTHS), 0);
        assertEquals(emptyTownhouseJSON.get(SysConst.LISTING_SQUARE_FOOTAGE), 0.0);
        assertEquals(emptyTownhouseJSON.get(SysConst.LISTING_IS_SUBLEASE), false);
        assertEquals(emptyTownhouseJSON.get(SysConst.LISTING_UTILITIES_INCLUDED), false);
        assertEquals(emptyTownhouseJSON.get(SysConst.LISTING_NUM_BEDROOMS), 0);
        assertEquals(emptyTownhouseJSON.get(SysConst.LISTING_NUM_BATHROOMS), 0);
        assertEquals(emptyTownhouseJSON.get(SysConst.LISTING_HAS_SHUTTLE), false);
        assertEquals(emptyTownhouseJSON.get(SysConst.LISTING_AVAILABLE), false);
        assertEquals(emptyTownhouseJSON.get("hasWasher"), false);
        assertEquals(emptyTownhouseJSON.get("hasDryer"), false);

        assertEquals(emptyTownhouseJSON.get("hasGarage"), false);
        assertEquals(emptyTownhouseJSON.get("hasDriveway"), false);
        assertEquals(emptyTownhouseJSON.get("hasYard"), false);
        assertEquals(emptyTownhouseJSON.get("hasFence"), false);
    }
}
