package housingapp;

/**
 * statically accessible constants
 */

public class SysConst {

    // user constants
    public static final String USERS_DATA_FILE = "src/housingapp/data/users.json";
    public static final String USER_ID = "id";
    public static final String USER_FIRST_NAME = "firstName";
    public static final String USER_LAST_NAME = "lastName";
    public static final String USER_PHONE = "phone";
    public static final String USER_EMAIL = "email";
    public static final String USER_PASSWORD = "password";
    public static final String USER_LISTINGS = "listings";

    // student user constants
    public static final String STUDENT_USERS = "students";
    public static final String STUDENT_USER_HAS_PETS = "hasPets";
    public static final String STUDENT_USER_PRICE_RANGE_LOWER = "priceRangeLower";
    public static final String STUDENT_USER_PRICE_RANGE_UPPER = "priceRangeUpper";
    public static final String STUDENT_USER_MAX_TRAVEL_DISTANCE = "maxTravelDistance";
    public static final String STUDENT_USER_MIN_ROOMMATES = "minRoommates";
    public static final String STUDENT_USER_MAX_ROOMMATES = "maxRoommates";
    public static final String STUDENT_USER_RATINGS = "ratings";
    public static final String STUDENT_USER_LISTING_FAVORITES = "listingFavorites";

    // property manager user constants
    public static final String PROPERTY_MANAGER_USERS = "propertyManagers";
    public static final String PROPERTY_MANAGER_USER_OFFICE_ADDRESS = "officeAddress";
    public static final String PROPERTY_MANAGER_USER_PROPERTIES = "properties";

    // property constants
    public static final String PROPERTIES_DATA_FILE = "src/housingapp/data/properties.json";
    public static final String PROPERTY_ID = "id";
    public static final String PROPERTY_NAME = "name";
    public static final String PROPERTY_ADDRESS = "address";
    public static final String PROPERTY_DISTANCE_TO_CAMPUS = "distanceToCampus";
    public static final String PROPERTY_RATINGS = "ratings";
    public static final String PROPERTY_LISTINGS = "listings";

    // listing constants
    public static final String LISTINGS_DATA_FILE = "src/housingapp/data/listings.json";
    public static final String LISTING_ID = "id";
    public static final String LISTING_PROPERTY_ID = "propertyId";
    public static final String LISTING_DESCRIPTION = "description";
    public static final String LISTING_PRICE = "price";
    public static final String LISTING_LEASE_MONTHS = "leaseMonths";
    public static final String LISTING_SQUARE_FOOTAGE = "squareFootage";
    public static final String LISTING_PETS_ALLOWED = "petsAllowed";
    public static final String LISTING_IS_SUBLEASE = "isSublease";
    public static final String LISTING_UTILITIES_INCLUDED = "utilitiesIncluded";
    public static final String LISTING_NUM_BEDROOMS = "numBedrooms";
    public static final String LISTING_NUM_BATHROOMS = "numBathrooms";
    public static final String LISTING_HAS_SHUTTLE = "hasShuttle";
    public static final String LISTING_AVAILABLE = "available";

    // session constants
    public static final String SESSIONS_DATA_FILE = "src/housingapp/data/sessions.json";
    public static final String SESSION_TOKEN = "token";
    public static final String SESSION_USER_ID = "userId";
    public static final String SESSION_EXPIRATION = "expiration";

    // rating constants
    public static final String RATINGS_DATA_FILE = "src/housingapp/data/ratings.json";
    public static final String RATING_ID = "id";
    public static final String RATING_STARS = "stars";
    public static final String RATING_COMMENT = "comment";
    public static final String PROPERTY_RATING_VALUE_STARS = "valueStars";
    public static final String PROPERTY_RATING_MANAGEMENT_STARS = "managementStars";
    public static final String PROPERTY_RATING_NEIGHBORHOOD_STARS = "neighborhoodStars";
    public static final String STUDENT_RATING_NUM_LATE_PAYMENTS = "numLatePayments";
    public static final String STUDENT_RATING_DAMAGES_VALUE = "damagesValue";

    // user input commands
    public static final String CMD_ENTER = "";
    public static final String CMD_LOG_OUT = "log_out";
    public static final String CMD_SEARCH_LISTINGS = "search_listings";
    public static final String CMD_VIEW_FAVORITES = "view_favorites";
    public static final String CMD_VIEW_MY_LISTINGS = "view_my_listings";
    public static final String CMD_VIEW_MY_REVIEWS = "view_my_reviews";
    public static final String CMD_VIEW_MY_PROPERTY_REVIEWS = "view_my_property_reviews";
    public static final String CMD_CREATE_LISTING = "create_listing";
    public static final String CMD_CREATE_REVIEW = "create_review";
    public static final String CMD_VIEW_PROFILE = "view_profile";
    public static final String CMD_REGISTER_PROPERTY = "register_property";
    public static final String CMD_SEARCH_LISTINGS_BY_RECOMMENDED = "search_by_recommended";
    public static final String CMD_SEARCH_LISTINGS_BY_PROPERTY = "search_by_property";
    public static final String CMD_SEARCH_LISTINGS_BY_PARAMETERS = "search_by_parameters";
    public static final String CMD_SET_SEARCH_PARAM_PRICE = "set_search_param_price";
    public static final String CMD_SET_SEARCH_PARAM_LEASE_DURATION = "set_search_param_lease_duration";
    public static final String CMD_SET_SEARCH_PARAM_SQUARE_FOOTAGE = "set_search_param_square_footage";
    public static final String CMD_SET_SEARCH_PARAM_PET_POLICY = "set_search_param_pet_policy";
    public static final String CMD_SET_SEARCH_PARAM_UTILITIES = "set_search_param_utilities";
    public static final String CMD_SET_SEARCH_PARAM_NUM_BEDROOMS = "set_search_param_num_bedrooms";
    public static final String CMD_SET_SEARCH_PARAM_NUM_BATHROOMS = "set_search_param_num_bathrooms";
    public static final String CMD_SET_SEARCH_PARAM_SHUTTLE_SERVICE = "set_search_param_shuttle_service";

    // error messages
    public static final String ERR_INVALID_INPUT = "Invalid input. Please choose one of the listed options.";
    public static final String ERR_INVALID_LOGIN = "Invalid email or password. Please try again.";
    public static final String ERR_INVALID_PERMISSION = "Your account doesn't have permission to access that feature.";
    public static final String ERR_INVALID_SESSION_DETAILS = "Invalid session details found.";
}
