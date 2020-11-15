package tests;

import static org.junit.jupiter.api.Assertions.*;


import org.junit.jupiter.api.Test;
import housingapp.user.*;
import housingapp.housing.*;
import housingapp.rating.*;

class StudentTest {
	
	private Student student = TestConst.STUDENT_VALID;
	private StudentRating studentRating = TestConst.STUDENT_RATING_VALID;
	private Apartment apartment = TestConst.APARTMENT_VALID;
	private Townhouse townHouse = TestConst.TOWNHOUSE_VALID;

	@Test
	void testAddRating() {
		student.associateRating(studentRating.getId());
		assertEquals(1, student.getRatings().size());
	}
	
	@Test
	void testRemoveRating() {
		student.removeRating(studentRating.getId());
		assertEquals(0, student.getRatings().size());
	}
	
	@Test
	void testAddFavorites() {
		student.associateListingFavorite(townHouse.getId());
		assertEquals(1, student.getListingFavorites().size());
	}
	
	@Test
	void testRemoveFavorites() {
		student.removeListingFavorite(townHouse.getId());
		assertEquals(0, student.getListingFavorites().size());
	}
	
	@Test
	void testAddingListing() {
		student.associateListing(apartment.getId());
		assertEquals(1, student.getListings().size());
	}
	
	@Test
	void testRemoveListing() {
		student.removeListing(apartment.getId());
		assertEquals(0, student.getListings().size());
	}
	
	@Test
	void testToString() {
		String details = student.toString();
		String trueDetails = String.format("First name: %s\nLast name: %s\nPhone: %s\nEmail: %s\n", student.getFirstName(), student.getLastName(), student.getPhone(), student.getEmail());
		 trueDetails += "Has pets: " + student.hasPets() + "\n";
	     trueDetails += "Lower price range: " + student.getPriceRangeLower() + "\n";
	     trueDetails += "Upper price range: " + student.getPriceRangeUpper() + "\n";
	     trueDetails += "Min roommates: " + student.getMinRoommates() + "\n";
	     trueDetails += "Max roommates: " + student.getMaxRoommates();
	     assertEquals(trueDetails, details);
	}

}
