import static org.junit.jupiter.api.Assertions.*;

import housingapp.data.*;
import housingapp.errors.*;
import housingapp.housing.*;
import housingapp.query.*;
import housingapp.rating.*;
import housingapp.resources.*;
import housingapp.user.*;
import java.util.*;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;



import org.junit.jupiter.api.Test;

class RscListingLoaderTest {
	
	private ResourceManager rm = ResourceManager.getInstance();
	private ArrayList<Listing> apartmentList = rm.getApartments();
	private ArrayList<Listing> townhouseList = rm.getTownhouses();
	private Map<String, ArrayList<Listing>> listingsMap = rm.getListingMap();
	
	@BeforeEach
	public void setup() {
		apartmentList.clear();
		townhouseList.clear();
		UUID apartmentID = UUID.fromString("b8c88c81-8808-4271-a2a7-602de25cad95");
		apartmentList.add(new Apartment(apartmentID, "test apartment", 400.00, 9, 1700.00, false, true, 4, 4, true, true, true, true, "3704", true));
		UUID townhouseID = UUID.fromString("0b2465cd-70b2-482e-b542-8026ad555faa");
		townhouseList.add(new Townhouse(townhouseID, "test town house", 500.00, 12, 1500.00, false, true, 4, 4, false, true, true, true, true, true, true, true));
		RscListing.writeListings();
		
	}
	
	@AfterEach
	public void tearDown() {
		ResourceManager.getInstance().getListingMap().clear();
		RscListing.writeListings();
	}

	@Test
	void testGetNumberOfListings() {
		listingsMap = RscListing.getListings();
		int numberOfListings = listingsMap.get(apartmentList).size() + listingsMap.get(townhouseList).size();
		assertEquals(2, numberOfListings);
		
	}
	
	@Test
	void testGetApartmentNumber() {
		listingsMap = RscListing.getListings();
		assertEquals("4", listingsMap.get(apartmentList).get(0).getNumBedrooms());
	}

}
