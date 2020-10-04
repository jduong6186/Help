public class Property {

    private String name;
    private String address;
    private double distanceToCampus;
    private Rating[] ratings;
    private Listing[] listings;

    public Property(String name, String address, double distanceToCampus) {
        this.name = name;
        this.address = address;
        this.distanceToCampus = distanceToCampus;
    }

    protected String getName() {
        return this.name;
    }

    protected String getAddress() {
        return this.address;
    }

    protected double getDistanceToCampus() {
        return this.distanceToCampus;
    }

    protected Rating[] getRatings() {
        return this.ratings;
    }

    protected Listing[] getListings() {
        return this.listings;
    }

    protected void addRating(Rating rating) {
        if (this.ratings[this.ratings.length-1] != null) {
            this.ratings = growRatings(this.ratings);
        }
        for (int i=0; i<this.ratings.length; i++) {
            if (this.ratings[i] == null) {
                this.ratings[i] = rating;
                return;
            }
        }
    }

    private Rating[] growRatings(Rating[] ratings) {
        Rating[] newRatings = new Rating[ratings.length*2];
        for (int i=0; i<ratings.length; i++) {
            newRatings[i] = ratings[i];
        }
        return newRatings;
    }

    protected void associateListing(Listing listing) {
        if (this.listings[this.listings.length-1] != null) {
            this.listings = growListings(this.listings);
        }
        for (int i=0; i<this.listings.length; i++) {
            if (this.listings[i] == null) {
                this.listings[i] = listing;
                return;
            }
        }
    }

    private Listing[] growListings(Listing[] listings) {
        Listing[] newListings = new Listing[listings.length*2];
        for (int i=0; i<listings.length; i++) {
            newListings[i] = listings[i];
        }
        return newListings;
    }
}
