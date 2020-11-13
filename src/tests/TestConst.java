package tests;

import housingapp.housing.Apartment;
import housingapp.housing.Property;
import housingapp.housing.Townhouse;
import housingapp.user.PropertyManager;

public class TestConst {

    static final PropertyManager PROPERTY_MANAGER_VALID = new PropertyManager("John", "Doe", "8032224444",
            "example@gmail.com", "password", "123 office st");
    static final Property PROPERTY_VALID = new Property(PROPERTY_MANAGER_VALID.getId(), "Cayce Cove", "215 Spencer Pl",
            "29033", 10.5, 300.00, true, true, true,
            false, true);
    static final Apartment APARTMENT_VALID = new Apartment(PROPERTY_VALID.getId(), "1bed 1bath at Cayce Cove",
            850.75, 12, 250, false, true, 1,
            1, true, true, true, true, "123A", true);
    static final Townhouse TOWNHOUSE_VALID = new Townhouse(PROPERTY_VALID.getId(), "2bed 2bath townhouse",
            2050.00, 10, 1000, false, false, 2,
            2, false, true, true, true, true, true, true, true);
}
