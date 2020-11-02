package housingapp.rating;

import housingapp.RatingType;

import java.util.UUID;
/**
child class of ratings for the propertymanager's rating
*/
public class PropertyRating extends Rating {

    private int valueStars;
    private int managementStars;
    private int neighborhoodStars;
    /**
    constructor
    */
    public PropertyRating(int stars, String comment, int valueStars, int managementStars, int neighborhoodStars) {
        super(RatingType.PROPERTY_RATING, stars, comment);
        setValueStars(valueStars);
        setManagementStars(managementStars);
        setNeighborhoodStars(neighborhoodStars);
    }
    /**
    UUID constructor
    */
    public PropertyRating(UUID id, int stars, String comment, int valueStars, int managementStars, int neighborhoodStars) {
        super(id, RatingType.PROPERTY_RATING, stars, comment);
        setValueStars(valueStars);
        setManagementStars(managementStars);
        setNeighborhoodStars(neighborhoodStars);
    }
    /**
    returns UUID
    */
    public UUID getId() {
        return super.getId();
    }
    /**
    returns the type of the rating
    */
    public RatingType getType() {
        return super.getType();
    }
    /**
    returns the number of stars for the rating
    */
    public int getStars() {
        return super.getStars();
    }
    /**
    returns the comment for the rating
    */
    public String getComment() {
        return super.getComment();
    }
    /**
    returns the # of stars in the rating
    */
    public int getValueStars() {
        return this.valueStars;
    }
    /**
    returns the rating for the management at the property
    */
    public int getManagementStars() {
        return this.managementStars;
    }
    /**
    returns the rating for the neighborhood at the property
    */
    public int getNeighborhoodStars() {
        return this.neighborhoodStars;
    }
    /**
    set the number of starts in the rating
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
    sets the value of the stars in the rating
    */
    public void setValueStars(int valueStars) {
    	this.valueStars = valueStars;
    }
    /**
    sets the number of stars for the management at the property
    */
    public void setManagementStars(int managementStars) {
    	this.managementStars = managementStars;
    }
    /**
    sets the number of stars for the neighborhood at the property
    */
    public void setNeighborhoodStars(int neighborhoodStars) {
        this.neighborhoodStars = neighborhoodStars;
    }
}
