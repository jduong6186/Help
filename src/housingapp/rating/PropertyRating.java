package housingapp.rating;

import housingapp.RatingType;

import java.util.UUID;

public class PropertyRating extends Rating {

    private int valueStars;
    private int managementStars;
    private int neighborhoodStars;

    public PropertyRating(int stars, String comment, int valueStars, int managementStars, int neighborhoodStars) {
        super(RatingType.PROPERTY_RATING, stars, comment);
        setValueStars(valueStars);
        setManagementStars(managementStars);
        setNeighborhoodStars(neighborhoodStars);
    }

    public PropertyRating(UUID id, int stars, String comment, int valueStars, int managementStars, int neighborhoodStars) {
        super(id, RatingType.PROPERTY_RATING, stars, comment);
        setValueStars(valueStars);
        setManagementStars(managementStars);
        setNeighborhoodStars(neighborhoodStars);
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

    public int getValueStars() {
        return this.valueStars;
    }

    public int getManagementStars() {
        return this.managementStars;
    }

    public int getNeighborhoodStars() {
        return this.neighborhoodStars;
    }

    public void setStars(int stars) {
        super.setStars(stars);
    }

    public void setComment(String comment) {
        super.setComment(comment);
    }
    
    public void setValueStars(int valueStars) {
    	this.valueStars = valueStars;
    }
    
    public void setManagementStars(int managementStars) {
    	this.managementStars = managementStars;
    }

    public void setNeighborhoodStars(int neighborhoodStars) {
        this.neighborhoodStars = neighborhoodStars;
    }
}