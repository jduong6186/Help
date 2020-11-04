package housingapp.rating;

import housingapp.RatingType;
import java.util.UUID;

/**
 * describes a student rating
 */

public class StudentRating extends Rating {

    private int numLatePayments;
    private double damagesValue;


    /**
     * constructor for instantiating studentRating at runtime
     * @param stars overall rating in stars 1-5
     * @param comment additional comments by rating user
     * @param numLatePayments number of late payments made by student
     * @param damagesValue value of damages caused by student
     */
    public StudentRating(int stars, String comment, int numLatePayments, double damagesValue) {
        super(RatingType.STUDENT_RATING, stars, comment);
        setNumLatePayments(numLatePayments);
        setDamagesValue(damagesValue);
    }


    /**
     * constructor for instantiating studentRating from JSON input file
     * @param id UUID of studentRating
     * @param stars overall rating in stars 1-5
     * @param comment additional comments by rating user
     * @param numLatePayments number of late payments made by student
     * @param damagesValue value of damages caused by student
     */
    public StudentRating(UUID id, int stars, String comment, int numLatePayments, double damagesValue) {
        super (id, RatingType.STUDENT_RATING, stars, comment);
        setNumLatePayments(numLatePayments);
        setDamagesValue(damagesValue);
    }

    /**
    returns the UUID
    */
    public UUID getId() {
        return super.getId();
    }

    /**
    returns the type of the account
    */
    public RatingType getType() {
        return super.getType();
    }

    /**
    returns the stars (basis of rating system)
    */
    public int getStars() {
        return super.getStars();
    }

    /**
    gets the comment on the rating
    */
    public String getComment() {
        return super.getComment();
    }

    /**
    gets the number of later payments made by the student
    */
    public int getNumLatePayments() {
        return this.numLatePayments;
    }

    /**
    gets the value of damages
    */
    public double getDamagesValue() {
        return this.damagesValue;
    }

    /**
    sets the star # for the rating
    */
    public void setStars(int stars) {
        super.setStars(stars);
    }

    /**
    sets the comment for the rating
    */
    public void setComment(String comment) {
        super.setComment(comment);
    }

    /**
    sets the number of late payments made by the student
    */
    public void setNumLatePayments(int numLatePayments) {
    	this.numLatePayments = numLatePayments;
    }

    /**
    sets the total value of damages for the student
    */
    public void setDamagesValue(double damagesValue) {
    	this.damagesValue = damagesValue;
    }

    /**
     * returns details str of student rating
     */
    @Override
    public String toString() {
        String details = super.toString();
        details += " | Num late payments: " + this.numLatePayments;
        details += " | Damages value: $" + this.damagesValue;
        return details;
    }
}
