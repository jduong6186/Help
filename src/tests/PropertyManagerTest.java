package tests;

import static org.junit.jupiter.api.Assertions.*;


import org.junit.jupiter.api.Test;
import housingapp.user.*;
import housingapp.housing.*;

class PropertyManagerTest {
	
	private PropertyManager propertyManager = TestConst.PROPERTY_MANAGER_VALID;
	private Property property = TestConst.PROPERTY_VALID;
	private Apartment apartment = TestConst.APARTMENT_VALID;
	

	@Test
	void testAddingProperty() {
		propertyManager.associateProperty(property.getId());
		assertEquals(1, propertyManager.getProperties().size());
	}
	
	@Test
	void testRemoveProperty() {
		propertyManager.removeProperty(property.getId());
		assertEquals(0, propertyManager.getProperties().size());
	}
	
	@Test
	void testAddingListing() {
		propertyManager.associateListing(apartment.getId());
		assertEquals(1, propertyManager.getListings().size());
	}
	
	@Test
	void testRemoveListing() {
		propertyManager.removeListing(apartment.getId());
		assertEquals(0, propertyManager.getListings().size());
	}
	
	@Test
	void testGetDetails() {
		String details = propertyManager.toString();
		String desiredDetails = String.format("First name: %s\nLast name: %s\nPhone: %s\nEmail: %s\n", propertyManager.getFirstName(), propertyManager.getLastName(), propertyManager.getPhone(), propertyManager.getEmail()) + "Office address: " + propertyManager.getOfficeAddress();
		assertEquals(desiredDetails, details);
	}

}
