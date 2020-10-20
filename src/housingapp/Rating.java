package housingapp;

public class Rating {

    private int stars;
    private String comment;

    public Rating(int stars, String comment) {
        this.stars = stars;
        this.comment = comment;
    }

    protected int getStars() {
        return this.stars;
    }

    protected String getComment() {
        return this.comment;
    }
}
