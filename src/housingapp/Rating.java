package housingapp;

import java.util.Scanner;

public class Rating {

    private int stars;
    private String comment;

    public Rating(int stars, String comment) {
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
    
    protected int getStars() {
        return this.stars;
    }

    protected String getComment() {
        return this.comment;
    }
}