package housingapp.system;

public enum Flow {

    HOME, LOG_IN, SIGN_UP, DASHBOARD, // generic
    SEARCH_LISTINGS, VIEW_MY_LISTINGS, VIEW_MY_REVIEWS, CREATE_LISTING, CREATE_REVIEW, VIEW_PROFILE, EDIT_LISTING, EDIT_REVIEW, UNREGISTER_PROPERTY, // all user types
    VIEW_FAVORITES, // students only
    REGISTER_PROPERTY // property managers only
}
