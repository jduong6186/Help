package housingapp.rating;

import java.security.InvalidParameterException;
import java.util.UUID;

public abstract class Rating {

    private UUID id;
    private int stars;
    private String comment;

    public Rating(int stars, String comment) {
        try {
            setStars(stars);
        } catch (InvalidParameterException e) {
            System.out.println(e.getMessage());
        }
        this.comment = comment;
    }

    public Rating(UUID id, int stars, String comment) {
        this.id = id;
        try {
            setStars(stars);
        } catch (InvalidParameterException e) {
            System.out.println(e.getMessage());
        }
        this.comment = comment;
    }

    public  UUID getId() {
        return this.id;
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
}