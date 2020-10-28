package housingapp;

import java.util.Scanner;
import java.util.UUID;

public class StudentRating extends Rating {

    private int numLatePayments;
    private double damagesValue;

    public StudentRating(int stars, String comment, int numLatePayments, double damagesValue) {
        super(stars, comment);
        setNumLatePayments(numLatePayments);
        setDamagesValue(damagesValue);
    }

    public StudentRating(UUID id, int stars, String comment, int numLatePayments, double damagesValue) {
        super (id, stars, comment);
        setNumLatePayments(numLatePayments);
        setDamagesValue(damagesValue);
    }
    
    public void setNumLatePayments(int numLatePayments) {
    	
    	Scanner keyboard = new Scanner(System.in);
    	this.numLatePayments = numLatePayments;
    	
    	while(this.numLatePayments < 0 ) {
    		
    		System.out.print("Please enter in a positive amount: ");
    		this.numLatePayments = keyboard.nextInt();
    	}
    }
    
    public void setDamagesValue(double damagesValue) {
    	
    	Scanner keyboard = new Scanner(System.in);
    	this.damagesValue = damagesValue;
    	
    	while(this.damagesValue < 0 ) {
    		
    		System.out.print("Please enter in a positive amount: ");
    		this.damagesValue = keyboard.nextInt();
    	}
    }

    public int getNumLatePayments() {
        return this.numLatePayments;
    }

    public double getDamagesValue() {
        return this.damagesValue;
    }
}