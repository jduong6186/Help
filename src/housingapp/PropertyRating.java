package housingapp;

import java.util.Scanner;

public class PropertyRating extends Rating {

    private int valueStars;
    private int managementStars;
    private int neighborhoodStars;

    public PropertyRating(int stars, String comment, int valueStars, int managementStars, int neighborhoodStars) {
        super(stars, comment);
        setValueStars(valueStars);
        setManagementStars(managementStars);
        setNeighborhoodStars(neighborhoodStars);
    }
    
    protected void setValueStars(int valueStars) {
    	
    	Scanner keyboard = new Scanner(System.in);
    	this.valueStars = valueStars;
    	
    	while(this.valueStars < 0 || this.valueStars > 5) {
    		
    		System.out.print("Please enter in a valid star amount (0-5): ");
    		this.valueStars = keyboard.nextInt();
    	}
    	
    }
    
protected void setManagementStars(int managementStars) {
    	
    	Scanner keyboard = new Scanner(System.in);
    	this.managementStars = managementStars;
    	
    	while(this.managementStars < 0 || this.managementStars > 5) {
    		
    		System.out.print("Please enter in a valid star amount (0-5): ");
    		this.managementStars = keyboard.nextInt();
    	}
    	
    }

protected void setNeighborhoodStars(int neighborhoodStars) {
	
	Scanner keyboard = new Scanner(System.in);
	this.neighborhoodStars = neighborhoodStars;
	
	while(this.neighborhoodStars < 0 || this.neighborhoodStars > 5) {
		
		System.out.print("Please enter in a valid star amount (0-5): ");
		this.neighborhoodStars = keyboard.nextInt();
	}
	
}

    protected int getValueStars() {
    	return this.valueStars;
    }

    protected int getManagementStars() {
        return this.managementStars;
    }

    protected int getNeighborhoodStars() {
        return this.neighborhoodStars;
    }
}