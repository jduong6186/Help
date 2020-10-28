package housingapp;

import java.util.Scanner;
import java.util.UUID;

public class Rating {

    private UUID id;
    private int stars;
    private String comment;

    public Rating(int stars, String comment) {
        setStars(stars);
        this.comment = comment;
    }

    public Rating(UUID id, int stars, String comment) {
        this.id = id;
        setStars(stars);
        this.comment = comment;
    }

    protected void setStars(int stars) {
    	
    	Scanner keyboard = new Scanner(System.in);
    	this.stars = stars;
    	
    	while(this.stars < 0 || this.stars > 5) {
    		
    		System.out.print("Please enter in a valid star amount (0-5): ");
    		this.stars = keyboard.nextInt();
    	}
    	
    }

    public UUID getId() {
        return this.id;
    }
    
    public int getStars() {
        return this.stars;
    }

    public String getComment() {
        return this.comment;
    }
}