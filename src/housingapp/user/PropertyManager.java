package housingapp.user;

import housingapp.system.UserType;
import housingapp.user.User;

import java.util.ArrayList;
import java.util.UUID;

public class PropertyManager extends User {
	
	private String officeAddress;
	private ArrayList<UUID> properties;
	
	public PropertyManager(String firstName, String lastName, String phone, String email, String password,
                           String officeAddress) {
		super(firstName, lastName, phone, email, password);
		this.officeAddress = officeAddress;
		this.properties = new ArrayList<UUID>();
	}

	public PropertyManager(UUID id, String firstName, String lastName, String phone, String email, String password,
                           String officeAddress, ArrayList<UUID> listings, ArrayList<UUID> properties) {
	    super(id, firstName, lastName, phone, email, password, listings);
	    this.officeAddress = officeAddress;
	    this.properties = properties;
    }

	public UserType getUserType() {
		return UserType.PROPERTY_MANAGER;
	}

    public UUID getId() {
	    return super.getId();
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
}
