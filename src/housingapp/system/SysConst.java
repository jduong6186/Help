package housingapp.system;

public class SysConst {

    // user constants
    public static final String USERS_DATA_FILE = "src/data/users.json";
    public static final String USER_ID = "id";
    public static final String USER_FIRST_NAME = "firstName";
    public static final String USER_LAST_NAME = "lastName";
    public static final String USER_PHONE = "phone";
    public static final String USER_EMAIL = "email";
    public static final String USER_PASSWORD = "password";
    public static final String USER_LISTINGS = "listings";

    // property constants
    public static final String PROPERTIES_DATA_FILE = "src/data/properties.json";
    public static final String PROPERTY_ID = "id";
    public static final String PROPERTY_NAME = "name";
    public static final String PROPERTY_ADDRESS = "address";
    public static final String PROPERTY_DISTANCE_TO_CAMPUS = "distanceToCampus";
    public static final String PROPERTY_RATINGS = "ratings";
    public static final String PROPERTY_LISTINGS = "listings";

    // listing constants
    public static final String LISTINGS_DATA_FILE = "src/data/listings.json";
    public static final String LISTING_ID = "id";
    public static final String LISTING_PROPERTY = "property";
    public static final String LISTING_DESCRIPTION = "description";
    public static final String LISTING_PRICE = "price";
    public static final String LISTING_LEASE_MONTHS = "leaseMonths";
    public static final String LISTING_SQUARE_FOOTAGE = "squareFootage";
    public static final String LISTING_PETS_ALLOWED = "petsAllowed";
    public static final String LISTING_HAS_GAS = "hasGas";
    public static final String LISTING_HAS_ELECTRIC = "hasElectric";
    public static final String LISTING_HAS_WATER = "hasWater";
    public static final String LISTING_HAS_TRASH = "hasTrash";
    public static final String LISTING_IS_SUBLEASE = "isSublease";
    public static final String LISTING_UTILITIES_INCLUDED = "utilitiesIncluded";
    public static final String LISTING_NUM_BEDROOMS = "numBedrooms";
    public static final String LISTING_NUM_BATHROOMS = "numBathrooms";
    public static final String LISTING_HAS_SHUTTLE = "hasShuttle";
    public static final String LISTING_AVAILABLE = "available";

    // session constants
    public static final String SESSIONS_DATA_FILE = "src/data/sessions.json";
    public static final String SESSION_TOKEN = "token";
    public static final String SESSION_USER_ID = "userId";
    public static final String SESSION_EXPIRATION = "expiration";

    // user input commands
    public static final String CMD_SEARCH_LISTINGS = "search_listings";
    public static final String CMD_VIEW_FAVORITES = "view_favorites";
    public static final String CMD_VIEW_MY_LISTINGS = "view_my_listings";
    public static final String CMD_VIEW_MY_REVIEWS = "view_my_reviews";
    public static final String CMD_CREATE_LISTING = "create_listing";
    public static final String CMD_CREATE_REVIEW = "create_review";
    public static final String CMD_VIEW_PROFILE = "view_profile";
    public static final String CMD_REGISTER_PROPERTY = "register_property";

    // error messages
    public static final String ERR_INVALID_INPUT = "Invalid input. Please choose one of the listed options.";
    public static final String ERR_INVALID_LOGIN = "Invalid email or password. Please try again.";
    public static final String ERR_INVALID_PERMISSION = "Your account doesn't have permission to access that feature.";
}
