package housingapp.rating;

import housingapp.RatingType;

import java.util.UUID;

public class StudentRating extends Rating {

    private int numLatePayments;
    private double damagesValue;

    public StudentRating(int stars, String comment, int numLatePayments, double damagesValue) {
        super(RatingType.STUDENT_RATING, stars, comment);
        setNumLatePayments(numLatePayments);
        setDamagesValue(damagesValue);
    }

    public StudentRating(UUID id, int stars, String comment, int numLatePayments, double damagesValue) {
        super (id, RatingType.STUDENT_RATING, stars, comment);
        setNumLatePayments(numLatePayments);
        setDamagesValue(damagesValue);
    }

    public UUID getId() {
        return super.getId();
    }

    public RatingType getType() {
        return super.getType();
    }

    public int getStars() {
        return super.getStars();
    }

    public String getComment() {
        return super.getComment();
    }

    public int getNumLatePayments() {
        return this.numLatePayments;
    }

    public double getDamagesValue() {
        return this.damagesValue;
    }

    public void setStars(int stars) {
        super.setStars(stars);
    }

    public void setComment(String comment) {
        super.setComment(comment);
    }
    
    public void setNumLatePayments(int numLatePayments) {
    	this.numLatePayments = numLatePayments;
    }
    
    public void setDamagesValue(double damagesValue) {
    	this.damagesValue = damagesValue;
    }
}