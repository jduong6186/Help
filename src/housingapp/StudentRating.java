package housingapp;

import java.util.Scanner;

public class StudentRating extends Rating {

    private int numLatePayments;
    private double damagesValue;

    public StudentRating(int stars, String comment, int numLatePayments, double damagesValue) {
        super(stars, comment);
        setNumLatePayments(numLatePayments);
        setDamagesValue(damagesValue);
    }
    
    protected void setNumLatePayments(int numLatePayments) {
    	
    	Scanner keyboard = new Scanner(System.in);
    	this.numLatePayments = numLatePayments;
    	
    	while(this.numLatePayments < 0 ) {
    		
    		System.out.print("Please enter in a positive amount: ");
    		this.numLatePayments = keyboard.nextInt();
    	}
    }
    
    protected void setDamagesValue(double damagesValue) {
    	
    	Scanner keyboard = new Scanner(System.in);
    	this.damagesValue = damagesValue;
    	
    	while(this.damagesValue < 0 ) {
    		
    		System.out.print("Please enter in a positive amount: ");
    		this.damagesValue = keyboard.nextInt();
    	}
    }

    protected int getNumLatePayments() {
        return this.numLatePayments;
    }

    protected double getDamagesValue() {
        return this.damagesValue;
    }
}