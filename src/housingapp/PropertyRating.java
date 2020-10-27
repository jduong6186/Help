package housingapp;

import java.util.Scanner;
import java.util.UUID;

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

    public PropertyRating(UUID id, int stars, String comment, int valueStars, int managementStars, int neighborhoodStars) {
        super(id, stars, comment);
        setValueStars(valueStars);
        setManagementStars(managementStars);
        setNeighborhoodStars(neighborhoodStars);
    }
    
    public void setValueStars(int valueStars) {
    	
    	Scanner keyboard = new Scanner(System.in);
    	this.valueStars = valueStars;
    	
    	while(this.valueStars < 0 || this.valueStars > 5) {
    		
    		System.out.print("Please enter in a valid star amount (0-5): ");
    		this.valueStars = keyboard.nextInt();
    	}
    	
    }
    
    public void setManagementStars(int managementStars) {
    	
    	Scanner keyboard = new Scanner(System.in);
    	this.managementStars = managementStars;
    	
    	while(this.managementStars < 0 || this.managementStars > 5) {
    		
    		System.out.print("Please enter in a valid star amount (0-5): ");
    		this.managementStars = keyboard.nextInt();
    	}
    	
    }

    public void setNeighborhoodStars(int neighborhoodStars) {
	
        Scanner keyboard = new Scanner(System.in);
        this.neighborhoodStars = neighborhoodStars;

        while(this.neighborhoodStars < 0 || this.neighborhoodStars > 5) {

            System.out.print("Please enter in a valid star amount (0-5): ");
            this.neighborhoodStars = keyboard.nextInt();
        }

    }

    public int getValueStars() {
    	return this.valueStars;
    }

    public int getManagementStars() {
        return this.managementStars;
    }

    public int getNeighborhoodStars() {
        return this.neighborhoodStars;
    }
}