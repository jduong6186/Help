package housingapp;

public class Student extends User {

	private boolean hasPets;
	private double priceRangeLower;
	private double priceRangeUpper;
	private double maxTravelDistance;
	private int minRoommates;
	private int maxRoommates;
	private Rating[] ratings;
	
	public Student(String name, String phone, String email, String password, boolean hasPets, double priceRangeLower,
					double priceRangeUpper, double maxTravelDistance, int minRoommates, int maxRoommates) {
		super(name, phone, email, password);
		this.hasPets = hasPets;
		this.priceRangeLower = priceRangeLower;
		this.priceRangeUpper = priceRangeUpper;
		this.maxTravelDistance = maxTravelDistance;
		this.minRoommates = minRoommates;
		this.maxRoommates = maxRoommates;
	}
	
	public boolean hasPets() {
		return this.hasPets;
	}
	
	public double getPriceRangeLower() {
		return this.priceRangeLower;
	}

	public double getPriceRangeUpper() {
		return this.priceRangeUpper;
	}
	
	public double getMaxTravelDistance() {
		return this.maxTravelDistance;
	}
	
	public int getMinRoommates() {
		return this.minRoommates;
	}
	
	public int getMaxRoommates() {
		return this.maxRoommates;
	}

	public Rating[] getRatings() {
		return this.ratings;
	}
	
	public double getRatingAverage() {
		double ratingAverage = 0;
		return ratingAverage;
	}
	
	protected void addRating(Rating ratings) {
		//add a rating
	}
	
	protected void removeRating(String ratingId) {
		//remove a rating
	}
}
