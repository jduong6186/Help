public class StudentRating extends Rating {

    private int numLatePayments;
    private double damagesValue;

    public StudentRating(int stars, String comment, int numLatePayments, double damagesValue) {
        super(stars, comment);
        this.numLatePayments = numLatePayments;
        this.damagesValue = damagesValue;
    }

    protected int getNumLatePayments() {
        return this.numLatePayments;
    }

    protected double getDamagesValue() {
        return this.damagesValue;
    }
}
