package housingapp;

public class PropertyRating extends Rating {

    private int valueStars;
    private int managementStars;
    private int neighborhoodStars;

    public PropertyRating(int stars, String comment, int valueStars, int managementStars, int neighborhoodStars) {
        super(stars, comment);
        this.valueStars = valueStars;
        this.managementStars = managementStars;
        this.neighborhoodStars = neighborhoodStars;
    }

    protected int getValueStars() {
        return this.valueStars;
    }

    protected int getManagementStars() {
        return this.managementStars;
    }

    protected int getNeighborhoodStars() {
        return this.neighborhoodStars;
    }
}
