package housingapp.rating;

import housingapp.RatingType;

import java.security.InvalidParameterException;
import java.util.UUID;
/**
base class of the ratings
*/
public abstract class Rating {

    private final UUID id;
    private final RatingType type;
    private int stars;
    private String comment;
    /**
    constructor
    */
    public Rating(RatingType type, int stars, String comment) {
        this.id = UUID.randomUUID();
        this.type = type;
        try {
            setStars(stars);
        } catch (InvalidParameterException e) {
            System.out.println(e.getMessage());
        }
        this.comment = comment;
    }
    /**
    UUID constructor
    */
    public Rating(UUID id, RatingType type, int stars, String comment) {
        this.id = id;
        this.type = type;
        try {
            setStars(stars);
        } catch (InvalidParameterException e) {
            System.out.println(e.getMessage());
        }
        this.comment = comment;
    }
    /**
    returns the UUID
    */
    public UUID getId() {
        return this.id;
    }
    /**
    returns the type of ratings
    */
    public RatingType getType() {
        return this.type;
    }
    /**
    returns the star # for the rating
    */
    public int getStars() {
        return this.stars;
    }
    /**
    returns the comment for the rating
    */
    public String getComment() {
        return this.comment;
    }
    /**
    sets the stars between 1-5
    */
    public void setStars(int stars) throws InvalidParameterException {
        if (this.stars<0 || this.stars>5) {
            throw new InvalidParameterException("Parameter 'stars' has illegal value: " + stars);
        } else {
            this.stars = stars;
        }
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    /**
     * returns details str for generic rating (invoked by sub-classes)
     */
    @Override
    public String toString() {
        return "Overall rating: " + this.stars;
    }
}
