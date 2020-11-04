package housingapp.rating;

import housingapp.RatingType;
import java.util.UUID;

/**
 * describes a property rating
 */

public class PropertyRating extends Rating {

    private int valueStars;
    private int managementStars;
    private int neighborhoodStars;


    /**
     * constructor for instantiating propertyRating at runtime
     * @param stars overall rating in stars 1-5
     * @param comment additional comments by rating user
     * @param valueStars value rating of property 1-5
     * @param managementStars management rating of property 1-5
     * @param neighborhoodStars neighborhood rating of property 1-5
     */
    public PropertyRating(int stars, String comment, int valueStars, int managementStars, int neighborhoodStars) {
        super(RatingType.PROPERTY_RATING, stars, comment);
        setValueStars(valueStars);
        setManagementStars(managementStars);
        setNeighborhoodStars(neighborhoodStars);
    }


    /**
     * constructor for instantiating propertyRating from JSON input file
     * @param id UUID of property rating
     * @param stars overall rating in stars 1-5
     * @param comment additional comments by rating user
     * @param valueStars value rating of property 1-5
     * @param managementStars management rating of property 1-5
     * @param neighborhoodStars neighborhood rating of property 1-5
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

    /**
     * returns details str for property rating
     */
    @Override
    public String toString() {
        String details = super.toString();
        details += " | Value rating: " + this.valueStars;
        details += " | Management rating: " + this.managementStars;
        details += " | Neighborhood raint: " + this.neighborhoodStars;
        return details;
    }
}
