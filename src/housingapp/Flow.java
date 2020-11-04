package housingapp;

/**
 * enum of all flows in the driver (the different "screens" of the application)
 */

public enum Flow {

    HOME, LOG_IN, SIGN_UP, DASHBOARD, // generic
    SEARCH_LISTINGS, VIEW_MY_LISTINGS, CREATE_LISTING, CREATE_REVIEW, VIEW_PROFILE, EDIT_LISTING, // all user types
    VIEW_FAVORITES, VIEW_MY_REVIEWS, // students only
    REGISTER_PROPERTY, VIEW_MY_PROPERTY_REVIEWS // property managers only
}
