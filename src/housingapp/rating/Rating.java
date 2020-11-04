package housingapp.rating;

import housingapp.RatingType;

import java.security.InvalidParameterException;
import java.util.UUID;

/**
 * describes an abstract rating
 */

public abstract class Rating {

    private final UUID id;
    private final RatingType type;
    private int stars;
    private String comment;

    /**
     * constructor for instantiating rating at runtime
     * @param type type of rating (property/student)
     * @param stars overall rating in stars 1-5
     * @param comment additional comments by rating user
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
     * constructor for instantiating rating from JSON input file
     * @param id UUID of rating
     * @param type type of rating (property/student)
     * @param stars overall rating in stars 1-5
     * @param comment additional comments by rating user
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
     * stars mutator
     * @param stars new stars (1-5)
     * @throws InvalidParameterException if stars is not in valid range (1-5), error thrown
     */
    public void setStars(int stars) throws InvalidParameterException {
        if (this.stars<0 || this.stars>5) {
            throw new InvalidParameterException("Parameter 'stars' has illegal value: " + stars);
        } else {
            this.stars = stars;
        }
    }

    /**
     * comment mutator
     * @param comment new comment
     */
    public void setComment(String comment) {
        this.comment = comment;
    }

    /**
     * returns string representation of abstract rating
     */
    @Override
    public String toString() {
        return "Overall rating: " + this.stars;
    }
}
