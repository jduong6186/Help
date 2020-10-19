package housingapp;

public class PropertyManager extends User{
	
	private String officeAddress;
	private Property[] properties;
	
	public PropertyManager(String name, String phone, String email, String password, String officeAddress) {
		super(name, phone, email, password);
		this.officeAddress = officeAddress;
	}
	
	public String getOfficeAddress() {
		return this.officeAddress;
	}
	
	public Property[] getProperties() {
		return this.properties;
	}
	
	protected void addProperty(Property properties) {
		//add a property
	}
	
	protected void removeRating(String propertyId) {
		//remove a property
	}
}
