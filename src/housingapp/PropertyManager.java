package housingapp;

import java.util.ArrayList;
import java.util.UUID;

public class PropertyManager extends User{
	
	private String officeAddress;
	private ArrayList<UUID> properties;
	
	public PropertyManager(String name, String phone, String email, String password, String officeAddress) {
		super(name, phone, email, password, officeAddress);
		this.officeAddress = officeAddress;
	}
	
	public String getOfficeAddress() {
		return this.officeAddress;
	}
	
	public ArrayList<UUID> getProperties() {
		return this.properties;
	}
	
	protected void addProperty(UUID properties) {
		this.properties.add(properties);
	}
	
	protected void removeRating(String propertyId) {
		this.properties.remove(propertyId);
	}
}
