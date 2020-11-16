package tests;

import housingapp.Session;
import housingapp.housing.Apartment;
import housingapp.housing.Property;
import housingapp.housing.Townhouse;
import housingapp.rating.PropertyRating;
import housingapp.rating.StudentRating;
import housingapp.user.PropertyManager;
import housingapp.user.Student;

public class TestConst {

    static final PropertyManager PROPERTY_MANAGER_VALID = new PropertyManager("John", "Doe", "8032224444",
            "example@gmail.com", "password", "123 office st");
    static final PropertyManager PROPERTY_MANAGER_EMPTY = new PropertyManager("", "", "",
            "", "", "");

    static final Student STUDENT_VALID = new Student("Andrew", "Eldridge", "1234567890",
            "eldridga@email.sc.edu", "pass123", false, 500.00, 1000.00,
            25.0, 0, 4);
    static final Student STUDENT_EMPTY = new Student("", "", "",
            "", "", false, 0.0, 0.0,
            0.0, 0, 0);

    static final Property PROPERTY_VALID = new Property(PROPERTY_MANAGER_VALID.getId(), "Cayce Cove", "215 Spencer Pl",
            "29033", 10.5, 300.00, true, false, true,
            false, true);
    static final Property PROPERTY_EMPTY = new Property(null, "", "", "", 0.0,
            0.0, false, false, false, false, false);

    static final Apartment APARTMENT_VALID = new Apartment(PROPERTY_VALID.getId(), "1bed 1bath at Cayce Cove",
            850.75, 12, 250, false, true, 1,
            1, true, true, true, true, "123A", true);
    static final Apartment APARTMENT_EMPTY = new Apartment(null, "", 0.0, 0, 0.0,
            false, false, 0, 0, false, false,
            false, false, "", false);

    static final Townhouse TOWNHOUSE_VALID = new Townhouse(PROPERTY_VALID.getId(), "2bed 2bath townhouse",
            2050.00, 10, 1000, false, false, 2,
            2, false, true, true, true, true, true,
            true, true);
    static final Townhouse TOWNHOUSE_EMPTY = new Townhouse(null, "", 0.0, 0, 0.0,
            false, false, 0, 0, false, false,
            false, false, false, false, false, false);

    static final PropertyRating PROPERTY_RATING_VALID = new PropertyRating(5, "good property", 5, 4, 5);
    static final PropertyRating PROPERTY_RATING_EMPTY = new PropertyRating(0, "", 0, 0, 0);

    static final StudentRating STUDENT_RATING_VALID = new StudentRating(3, "a few late payments", 3, 0.0);
    static final StudentRating STUDENT_RATING_EMPTY = new StudentRating(0, "", 0, 0.0);

    static final Session SESSION_VALID = new Session(PROPERTY_MANAGER_VALID.getId());
    static final Session SESSION_NULL_USER = new Session(null);
}
