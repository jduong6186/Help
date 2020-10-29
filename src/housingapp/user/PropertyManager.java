package housingapp.user;

import housingapp.housing.Listing;
import housingapp.system.UserType;

import java.util.ArrayList;
import java.util.UUID;

public class PropertyManager extends User {
	
	private String officeAddress;
	private ArrayList<UUID> properties;
	
	public PropertyManager(String firstName, String lastName, String phone, String email, String password,
                           String officeAddress) {
		super(UserType.PROPERTY_MANAGER, firstName, lastName, phone, email, password);
		this.officeAddress = officeAddress;
		this.properties = new ArrayList<UUID>();
	}

	public PropertyManager(UUID id, String firstName, String lastName, String phone, String email, String password,
                           String officeAddress, ArrayList<UUID> listings, ArrayList<UUID> properties) {
	    super(id, UserType.PROPERTY_MANAGER, firstName, lastName, phone, email, password, listings);
	    this.officeAddress = officeAddress;
	    this.properties = properties;
    }

    public UUID getId() {
	    return super.getId();
    }

    public UserType getType() {
		return super.getType();
	}

    public String getFirstName() {
	    return super.getFirstName();
    }

    public String getLastName() {
	    return super.getLastName();
    }

    public String getPhone() {
	    return super.getPhone();
    }

    public String getEmail() {
	    return super.getEmail();
    }

    public String getPassword() {
	    return super.getPassword();
    }

    public ArrayList<UUID> getListings() {
        return super.getListings();
    }

	public String getOfficeAddress() {
		return this.officeAddress;
	}
	
	public ArrayList<UUID> getProperties() {
		return this.properties;
	}

	public void updateFirstName(String firstName) {
	    super.updateFirstName(firstName);
    }

	public void updateLastName(String lastName) {
	    super.updateLastName(lastName);
    }

	public void updatePhone(String phone) {
	    super.updatePhone(phone);
    }

	public void updateEmail(String email) {
	    super.updateEmail(email);
    }

	public void associateListing(UUID listingId) {
	    super.associateListing(listingId);
    }

	public void removeListing(UUID listingId) {
	    super.removeListing(listingId);
    }

	public void associateProperty(UUID propertyId) {
	    this.properties.add(propertyId);
	}

	public void removeProperty(UUID propertyId) {
	    this.properties.remove(propertyId);
    }

	public ArrayList<Listing> getRatings() {
		// TODO Auto-generated method stub
		return null;
	}

	public void removeRating(Listing listing) {
		// TODO Auto-generated method stub
		
	}
}
