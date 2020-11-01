package housingapp.rating;

import housingapp.system.RatingType;

import java.security.InvalidParameterException;
import java.util.UUID;

public abstract class Rating {

    private final UUID id;
    private final RatingType type;
    private int stars;
    private String comment;

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

    public UUID getId() {
        return this.id;
    }

    public RatingType getType() {
        return this.type;
    }

    public int getStars() {
        return this.stars;
    }

    public String getComment() {
        return this.comment;
    }

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
}