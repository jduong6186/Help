package housingapp.user;

import housingapp.UserType;

import java.util.ArrayList;
import java.util.UUID;

/**
 * describes property manager user type
 */

public class PropertyManager extends User {
	
	private String officeAddress;
	private ArrayList<UUID> properties;

	/**
	 * constructor for instantiating property manager at runtime
	 * @param firstName user's first name
	 * @param lastName user's last name
	 * @param phone phone number
	 * @param email email address
	 * @param password password (hashed in constructor)
	 * @param officeAddress office address of property manager
	 */
	public PropertyManager(String firstName, String lastName, String phone, String email, String password,
                           String officeAddress) {
		super(UserType.PROPERTY_MANAGER, firstName, lastName, phone, email, password);
		this.officeAddress = officeAddress;
		this.properties = new ArrayList<UUID>();
	}

	/**
	 * constructor for instantiating property manager from JSON input
	 * @param id property manager UUID
	 * @param firstName user's first name
	 * @param lastName user's last name
	 * @param phone phone number
	 * @param email email address
	 * @param password bcrypt password hash
	 * @param officeAddress office address of property manager
	 * @param listings array list of listings created by property manager
	 * @param properties array list of properties registered by property manager
	 */
	public PropertyManager(UUID id, String firstName, String lastName, String phone, String email, String password,
                           String officeAddress, ArrayList<UUID> listings, ArrayList<UUID> properties) {
	    super(id, UserType.PROPERTY_MANAGER, firstName, lastName, phone, email, password, listings);
	    this.officeAddress = officeAddress;
	    this.properties = properties;
    }

    /**
    returns UUID
    */
    public UUID getId() {
	    return super.getId();
    }

    /**
    returns type
    */
    public UserType getType() {
		return super.getType();
	}

    /**
    returns first name
    */
    public String getFirstName() {
	    return super.getFirstName();
    }

    /**
    returns last name
    */
    public String getLastName() {
	    return super.getLastName();
    }

    /**
    returns phone number
    */
    public String getPhone() {
	    return super.getPhone();
    }

    /**
    returns email
    */
    public String getEmail() {
	    return super.getEmail();
    }

    /**
    returns password
    */
    public String getPassword() {
	    return super.getPassword();
    }

    /**
    returns listings
    */
    public ArrayList<UUID> getListings() {
        return super.getListings();
    }

	/**
	returns the office address
	*/
	public String getOfficeAddress() {
		return this.officeAddress;
	}

	/**
	returns the property
	*/
	public ArrayList<UUID> getProperties() {
		return this.properties;
	}

    /**
    updates the first name of the user
    */
	public void updateFirstName(String firstName) {
	    super.updateFirstName(firstName);
    }

    /**
    updates the last name of the user
    */
	public void updateLastName(String lastName) {
	    super.updateLastName(lastName);
    }

    /**
    updates the phone number of the user
    */
	public void updatePhone(String phone) {
	    super.updatePhone(phone);
    }

    /**
    updates the email of the user
    */
	public void updateEmail(String email) {
	    super.updateEmail(email);
    }

	/**
	 * officeAddress mutator
	 * @param officeAddress office address of property manager
	 */
	public void updateOfficeAddress(String officeAddress) {
		this.officeAddress = officeAddress;
	}

    /**
	adds a listing
	*/
	public void associateListing(UUID listingId) {
	    super.associateListing(listingId);
    }

	/**
	removes a listing
	*/
	public void removeListing(UUID listingId) {
	    super.removeListing(listingId);
    }

	/**
	adds a property
	*/
	public void associateProperty(UUID propertyId) {
	    this.properties.add(propertyId);
	}

	/**
	removes a property
	*/
	public void removeProperty(UUID propertyId) {
	    this.properties.remove(propertyId);
    }

	/**
	 * returns property manager details as string
	 */
	@Override
	public String toString() {
		String details = super.toString();
		details += "Office address: " + officeAddress;
		return details;
	}
}
