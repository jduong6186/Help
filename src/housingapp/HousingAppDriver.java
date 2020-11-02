package housingapp;

import housingapp.errors.*;
import housingapp.housing.Apartment;
import housingapp.housing.Listing;
import housingapp.housing.Property;
import housingapp.housing.Townhouse;
import housingapp.query.ListingQuery;
import housingapp.query.ResourceManager;
import housingapp.rating.PropertyRating;
import housingapp.rating.Rating;
import housingapp.rating.StudentRating;
import housingapp.user.PropertyManager;
import housingapp.user.Student;
import housingapp.user.User;

import java.rmi.UnexpectedException;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.UUID;

public class HousingAppDriver {

    private static Scanner keyboardInput = new Scanner(System.in);
    private static Flow currFlow = Flow.HOME;
    private static UserType currUserType;
    private static Session currSession;
    private static String input;
    private static ResourceManager rm = ResourceManager.getInstance();

    public static void main(String[] args) {
        boolean running = true;
        ArrayList<Listing> currListingSearchResults = null;
        UUID currTarget = null;

        System.out.println("-----\nUofSC Off-Campus Housing App\n-----");

        while (running) {
            try {
                switch (currFlow) {
                    case HOME:
                        System.out.println("Select an option:\n(1) Log in\n(2) Sign up\n(3) Continue as guest");
                        input = keyboardInput.nextLine();
                        switch (input) {
                            case "1":
                                currFlow = Flow.LOG_IN;
                                break;
                            case "2":
                                currFlow = Flow.SIGN_UP;
                                break;
                            case "3":
                                currUserType = UserType.GUEST;
                                currFlow = Flow.DASHBOARD;
                                break;
                            default:
                                throw new InvalidInputException();
                        }
                        break;
                    case LOG_IN:
                        System.out.print("Email: ");
                        String email = keyboardInput.next();
                        keyboardInput.nextLine();
                        System.out.print("Password: ");
                        String password = keyboardInput.next();
                        keyboardInput.nextLine();
                        currSession = rm.login(email, password);
                        if (currSession != null) {
                            currUserType = rm.getUserById(currSession.getUserId()).getType();
                            currFlow = Flow.DASHBOARD;
                        } else {
                            throw new InvalidLoginException();
                        }
                        break;
                    case SIGN_UP:
                        System.out.print("Select an account type, 'student' or 'property manager': ");
                        String accountType = keyboardInput.nextLine();

                        if (!accountType.equalsIgnoreCase("student") && !accountType.equalsIgnoreCase("property manager")) {
                            throw new InvalidInputException();
                        }

                        System.out.print("First name: ");
                        String firstName = keyboardInput.next();
                        keyboardInput.nextLine();

                        System.out.print("Last name: ");
                        String lastName = keyboardInput.next();
                        keyboardInput.nextLine();

                        System.out.print("Phone number: ");
                        String phone = keyboardInput.next();
                        keyboardInput.nextLine();

                        System.out.print("Email: ");
                        String newEmail = keyboardInput.next();
                        keyboardInput.nextLine();

                        System.out.print("Password: ");
                        String newPassword = keyboardInput.next();
                        keyboardInput.nextLine();

                        if (accountType.equalsIgnoreCase("student")) {
                            System.out.print("Do you have pets (y/n): ");
                            boolean hasPets = keyboardInput.next().toLowerCase().equals("y");
                            keyboardInput.nextLine();

                            double[] priceRange = promptPriceRange();
                            double priceRangeLower = priceRange[0];
                            double priceRangeUpper = priceRange[1];

                            System.out.print("Max travel distance: ");
                            double maxTravelDistance = Double.parseDouble(keyboardInput.next());
                            keyboardInput.nextLine();

                            System.out.print("Min roommates: ");
                            int minRoommates = Integer.parseInt(keyboardInput.next());
                            keyboardInput.nextLine();

                            System.out.print("Max roommates: ");
                            int maxRoommates = Integer.parseInt(keyboardInput.next());
                            keyboardInput.nextLine();

                            rm.addStudent(new Student(firstName, lastName, phone, newEmail, newPassword, hasPets,
                                    priceRangeLower, priceRangeUpper, maxTravelDistance, minRoommates, maxRoommates));
                            currUserType = UserType.STUDENT;
                            currSession = rm.login(newEmail, newPassword);
                            System.out.println("Student user registered.");
                        } else if (accountType.equalsIgnoreCase("property manager")) {
                            System.out.print("Office address: ");
                            String officeAddress = keyboardInput.nextLine();

                            rm.addPropertyManager(new PropertyManager(firstName, lastName, phone, newEmail, newPassword,
                                    officeAddress));
                            currUserType = UserType.PROPERTY_MANAGER;
                            currSession = rm.login(newEmail, newPassword);
                            System.out.println("Property manager user registered.");
                        } else {
                            throw new InvalidInputException();
                        }
                        currFlow = Flow.DASHBOARD;
                        break;
                    case DASHBOARD:
                        System.out.println("-----\nDashboard\n-----");
                        // validate user session
                        if (currUserType == UserType.GUEST || rm.validateSession(currSession)) {
                            // prompt user for action depending on user type
                            if (currUserType == UserType.STUDENT) {
                                System.out.println(String.format("Select an option:\n(%s) Search listings\n(%s) View " +
                                                "favorites list\n(%s) View my listings\n(%s) View my reviews\n(%s) Create a " +
                                                "listing\n(%s) Create a review\n(%s) View profile\n(%s) Log out", SysConst.CMD_SEARCH_LISTINGS,
                                        SysConst.CMD_VIEW_FAVORITES, SysConst.CMD_VIEW_MY_LISTINGS, SysConst.CMD_VIEW_MY_REVIEWS,
                                        SysConst.CMD_CREATE_LISTING, SysConst.CMD_CREATE_REVIEW, SysConst.CMD_VIEW_PROFILE, SysConst.CMD_LOG_OUT));
                            } else if (currUserType == UserType.PROPERTY_MANAGER) {
                                System.out.println(String.format("Select an option:\n(%s) Search listings\n(%s) View my listings\n(%s)" +
                                                " View my reviews\n(%s) Create a listing\n(%s) Register a property\n(%s) Create a review\n(%s)" +
                                                " View profile\n(%s) Log out", SysConst.CMD_SEARCH_LISTINGS, SysConst.CMD_VIEW_MY_LISTINGS, SysConst.CMD_VIEW_MY_REVIEWS,
                                        SysConst.CMD_CREATE_LISTING, SysConst.CMD_REGISTER_PROPERTY, SysConst.CMD_CREATE_REVIEW, SysConst.CMD_VIEW_PROFILE, SysConst.CMD_LOG_OUT));
                            } else if (currUserType == UserType.GUEST) {
                                System.out.println(String.format("Select an option:\n(%s) Search listings\n(%s) Return home", SysConst.CMD_SEARCH_LISTINGS, SysConst.CMD_LOG_OUT));
                            } else {
                                throw new InvalidSessionDetailsException("Invalid user type: " + currUserType.toString());
                            }

                            // validate action input against user permissions
                            input = keyboardInput.nextLine();
                            switch (input.toLowerCase()) {
                                case SysConst.CMD_SEARCH_LISTINGS:
                                    currFlow = Flow.SEARCH_LISTINGS;
                                    break;
                                case SysConst.CMD_VIEW_FAVORITES:
                                    if (currUserType == UserType.STUDENT) {
                                        currFlow = Flow.VIEW_FAVORITES;
                                    } else {
                                        throw new InvalidPermissionException();
                                    }
                                    break;
                                case SysConst.CMD_VIEW_MY_LISTINGS:
                                    if (currUserType != UserType.GUEST) {
                                        currFlow = Flow.VIEW_MY_LISTINGS;
                                    } else {
                                        throw new InvalidPermissionException();
                                    }
                                    break;
                                case SysConst.CMD_VIEW_MY_REVIEWS:
                                    if (currUserType != UserType.GUEST) {
                                        currFlow = Flow.VIEW_MY_REVIEWS;
                                    } else {
                                        throw new InvalidPermissionException();
                                    }
                                    break;
                                case SysConst.CMD_CREATE_LISTING:
                                    if (currUserType != UserType.GUEST) {
                                        currFlow = Flow.CREATE_LISTING;
                                    } else {
                                        throw new InvalidPermissionException();
                                    }
                                    break;
                                case SysConst.CMD_CREATE_REVIEW:
                                    if (currUserType != UserType.GUEST) {
                                        currFlow = Flow.CREATE_REVIEW;
                                    } else {
                                        throw new InvalidPermissionException();
                                    }
                                    break;
                                case SysConst.CMD_VIEW_PROFILE:
                                    if (currUserType != UserType.GUEST) {
                                        currFlow = Flow.VIEW_PROFILE;
                                    } else {
                                        throw new InvalidPermissionException();
                                    }
                                    break;
                                case SysConst.CMD_REGISTER_PROPERTY:
                                    if (currUserType == UserType.PROPERTY_MANAGER) {
                                        currFlow = Flow.REGISTER_PROPERTY;
                                    } else {
                                        throw new InvalidPermissionException();
                                    }
                                    break;
                                case SysConst.CMD_LOG_OUT:
                                    System.out.println("Logging out.");
                                    currUserType = null;
                                    currSession = null;
                                    currFlow = Flow.HOME;
                                    break;
                                default:
                                    throw new InvalidInputException();
                            }
                        } else {
                            System.out.println("Session expired. Returning to home screen.");
                            currFlow = Flow.HOME;
                            continue;
                        }
                        break;
                    case SEARCH_LISTINGS:
                        if (currUserType == UserType.STUDENT) {
                            System.out.println(String.format("Select a search method:\n(%s) List recommended listings\n(%s) Search by property name\n(%s) Search by listing parameters",
                                    SysConst.CMD_SEARCH_LISTINGS_BY_RECOMMENDED, SysConst.CMD_SEARCH_LISTINGS_BY_PROPERTY, SysConst.CMD_SEARCH_LISTINGS_BY_PARAMETERS));
                        } else if (currUserType == UserType.PROPERTY_MANAGER || currUserType == UserType.GUEST) {
                            System.out.println(String.format("Select a search method:\n(%s) Search by property name\n(%s) Search by listing parameters",
                                    SysConst.CMD_SEARCH_LISTINGS_BY_PROPERTY, SysConst.CMD_SEARCH_LISTINGS_BY_PARAMETERS));
                        } else {
                            throw new InvalidSessionDetailsException("Invalid user type: " + currUserType.toString());
                        }
                        input = keyboardInput.nextLine();
                        ListingQuery query = ListingQuery.getInstance();
                        if (currListingSearchResults == null) {
                            currListingSearchResults = rm.getListings();
                        }
                        switch (input.toLowerCase()) {
                            case SysConst.CMD_SEARCH_LISTINGS_BY_RECOMMENDED:
                                if (currUserType == UserType.STUDENT) {
                                    currListingSearchResults = query.getListingsByRecommended((Student) rm.getUserById(currSession.getUserId()));
                                    printSearchResults(currListingSearchResults);
                                } else {
                                    throw new InvalidPermissionException();
                                }
                                break;
                            case SysConst.CMD_SEARCH_LISTINGS_BY_PROPERTY:
                                System.out.print("Enter a property to search: ");
                                String propertyName = keyboardInput.nextLine();
                                UUID propertyId = rm.getPropertyByName(propertyName).getId();

                                currListingSearchResults = query.getListingsByProperty(currListingSearchResults, propertyId);
                                printSearchResults(currListingSearchResults);
                                break;
                            case SysConst.CMD_SEARCH_LISTINGS_BY_PARAMETERS:
                                System.out.println("Select a parameter to set, or press ENTER to search using current parameters:");
                                System.out.println(String.format("(%s) Price\n(%s) Lease duration\n(%s) Square footage\n(%s) Pet policy\n(%s) Utilities\n(%s) Number of bedrooms\n(%s) Number of bathrooms\n(%s) Shuttle service",
                                        SysConst.CMD_SET_SEARCH_PARAM_PRICE, SysConst.CMD_SET_SEARCH_PARAM_LEASE_DURATION, SysConst.CMD_SET_SEARCH_PARAM_SQUARE_FOOTAGE, SysConst.CMD_SET_SEARCH_PARAM_PET_POLICY, SysConst.CMD_SET_SEARCH_PARAM_UTILITIES,
                                        SysConst.CMD_SET_SEARCH_PARAM_NUM_BEDROOMS, SysConst.CMD_SET_SEARCH_PARAM_NUM_BATHROOMS, SysConst.CMD_SET_SEARCH_PARAM_SHUTTLE_SERVICE));
                                input = keyboardInput.nextLine();
                                // cases for query parameter selections to update
                                switch (input.toLowerCase()) {
                                    case SysConst.CMD_ENTER:
                                        printSearchResults(currListingSearchResults);
                                        break;
                                    case SysConst.CMD_SET_SEARCH_PARAM_PRICE:
                                        double[] priceRange = promptPriceRange();
                                        double priceRangeLower = priceRange[0];
                                        double priceRangeUpper = priceRange[1];
                                        currListingSearchResults = query.getListingsByPrice(currListingSearchResults, priceRangeLower, priceRangeUpper);
                                        System.out.println("Price search param updated.");
                                        currFlow = Flow.SEARCH_LISTINGS;
                                        break;
                                    case SysConst.CMD_SET_SEARCH_PARAM_LEASE_DURATION:
                                        System.out.print("Minimum lease months: ");
                                        int minLeaseMonths = keyboardInput.nextInt();
                                        keyboardInput.nextLine();
                                        System.out.print("Maximum lease months: ");
                                        int maxLeaseMonths = keyboardInput.nextInt();
                                        keyboardInput.nextLine();
                                        currListingSearchResults = query.getListingsByLeaseMonths(currListingSearchResults, minLeaseMonths, maxLeaseMonths);
                                        System.out.println("Lease duration param updated.");
                                        currFlow = Flow.SEARCH_LISTINGS;
                                        break;
                                    case SysConst.CMD_SET_SEARCH_PARAM_SQUARE_FOOTAGE:
                                        System.out.print("Minimum square footage: ");
                                        double minSquareFootage = keyboardInput.nextDouble();
                                        keyboardInput.nextLine();
                                        System.out.print("Maximum square footage: ");
                                        double maxSquareFootage = keyboardInput.nextDouble();
                                        keyboardInput.nextLine();
                                        currListingSearchResults = query.getListingsBySquareFootage(currListingSearchResults, minSquareFootage, maxSquareFootage);
                                        System.out.println("Square footage param updated.");
                                        currFlow = Flow.SEARCH_LISTINGS;
                                        break;
                                    case SysConst.CMD_SET_SEARCH_PARAM_PET_POLICY:
                                        System.out.print("Allows pets (y/n): ");
                                        boolean  petsAllowed = keyboardInput.next().toLowerCase().equals("y");
                                        keyboardInput.nextLine();
                                        currListingSearchResults = query.getListingsByPetsAllowed(currListingSearchResults, petsAllowed);
                                        System.out.println("Pet policy param updated.");
                                        currFlow = Flow.SEARCH_LISTINGS;
                                        break;
                                    case SysConst.CMD_SET_SEARCH_PARAM_UTILITIES:
                                        System.out.print("Utilities included in rent price (y/n): ");
                                        boolean utilitiesIncluded = keyboardInput.next().toLowerCase().equals("y");
                                        keyboardInput.nextLine();
                                        currListingSearchResults = query.getListingsByUtilitiesIncluded(currListingSearchResults, utilitiesIncluded);
                                        System.out.println("Utilities param updated.");
                                        currFlow = Flow.SEARCH_LISTINGS;
                                        break;
                                    case SysConst.CMD_SET_SEARCH_PARAM_NUM_BEDROOMS:
                                        System.out.print("Minimum number of bedrooms: ");
                                        int minNumBedrooms = keyboardInput.nextInt();
                                        keyboardInput.nextLine();
                                        System.out.print("Maximum number of bedrooms: ");
                                        int maxNumBedrooms = keyboardInput.nextInt();
                                        keyboardInput.nextLine();
                                        currListingSearchResults = query.getListingsByNumBedrooms(currListingSearchResults, minNumBedrooms, maxNumBedrooms);
                                        System.out.println("Num bedrooms param updated.");
                                        currFlow = Flow.SEARCH_LISTINGS;
                                        break;
                                    case SysConst.CMD_SET_SEARCH_PARAM_NUM_BATHROOMS:
                                        System.out.print("Minimum number of bathrooms: ");
                                        int minNumBathrooms = keyboardInput.nextInt();
                                        keyboardInput.nextLine();
                                        System.out.print("Maximum number of bathrooms: ");
                                        int maxNumBathrooms = keyboardInput.nextInt();
                                        keyboardInput.nextLine();
                                        currListingSearchResults = query.getListingsByNumBedrooms(currListingSearchResults, minNumBathrooms, maxNumBathrooms);
                                        System.out.println("Num bathrooms param updated.");
                                        currFlow = Flow.SEARCH_LISTINGS;
                                        break;
                                    case SysConst.CMD_SET_SEARCH_PARAM_SHUTTLE_SERVICE:
                                        System.out.print("Has shuttle service (y/n): ");
                                        boolean hasShuttle = keyboardInput.next().toLowerCase().equals("y");
                                        keyboardInput.nextLine();
                                        currListingSearchResults = query.getListingsByHasShuttle(currListingSearchResults, hasShuttle);
                                        System.out.println("Shuttle service param updated.");
                                        currFlow = Flow.SEARCH_LISTINGS;
                                        break;
                                }
                                break;
                            default:
                                throw new InvalidInputException();
                        }
                        break;
                    case VIEW_MY_LISTINGS:
                        if (currUserType == UserType.PROPERTY_MANAGER || currUserType == UserType.STUDENT) {
                            User currUser = rm.getUserById(currSession.getUserId());
                            ArrayList<UUID> userListingIds = currUser.getListings();

                            // iterate over and display all of user's listings
                            for (int i=0; i<userListingIds.size(); i++) {
                                int currListingNum = i+1;
                                Listing currListing = rm.getListingById(userListingIds.get(i));
                                System.out.println(String.format("(%d) %s", currListingNum, currListing.toString()));
                            }

                            System.out.print("Enter a listing number to modify the listing, or press ENTER to return to dashboard: ");
                            input = keyboardInput.nextLine();

                            if (input.isEmpty()) {
                                currFlow = Flow.DASHBOARD;
                            } else {
                                int listingSelection = Integer.parseInt(input);
                                currTarget = userListingIds.get(listingSelection-1);
                                currFlow = Flow.EDIT_LISTING;
                            }
                        } else {
                            throw new InvalidPermissionException();
                        }
                        break;
                    case VIEW_MY_REVIEWS:
                        currFlow = Flow.DASHBOARD;
                        break;
                    case CREATE_LISTING:
                        System.out.print("Enter a type of listing to create (apartment/townhouse): ");
                        String listingTypeStr = keyboardInput.next();
                        keyboardInput.nextLine();

                        UUID propertyId = promptListingPropertyId();
                        if (propertyId == null) {
                            System.out.println("Couldn't find given property. Please register the property first.");
                            currFlow = Flow.DASHBOARD;
                            continue;
                        }
                        String description = promptListingDescription();
                        double price = promptListingPrice();
                        int leaseMonths = promptListingLeaseMonths();
                        double squareFootage = promptListingSquareFootage();
                        boolean isSublease = promptListingIsSublease();
                        boolean utilitiesIncluded = promptListingUtilitiesIncluded();
                        int numBedrooms = promptListingNumBedrooms();
                        int numBathrooms = promptListingNumBathrooms();
                        boolean hasShuttle = promptListingHasShuttle();
                        boolean available = promptListingAvailable();
                        boolean hasWasher = promptListingHasWasher();
                        boolean hasDryer = promptListingHasDryer();

                        if (listingTypeStr.equalsIgnoreCase("apartment")) {
                            String apartmentNumber = promptApartmentNumber();
                            boolean hasParking = promptApartmentHasParking();

                            // add new apartment to rm
                            Apartment newApartment = new Apartment(propertyId, description, price, leaseMonths, squareFootage,
                                    isSublease, utilitiesIncluded, numBedrooms, numBathrooms,
                                    hasShuttle, available, hasWasher, hasDryer, apartmentNumber, hasParking);
                            rm.addApartment(newApartment);

                            // associate new apartment with property
                            Property parentProperty = rm.getPropertyById(propertyId);
                            parentProperty.associateListing(newApartment.getId());
                            rm.updateProperty(propertyId, parentProperty);

                            // associate new apartment with listing user
                            User listingUser = rm.getUserById(currSession.getUserId());
                            listingUser.associateListing(newApartment.getId());
                            if (currUserType == UserType.PROPERTY_MANAGER) {
                                rm.updatePropertyManager(currSession.getUserId(), (PropertyManager) listingUser);
                            } else {
                                rm.updateStudent(currSession.getUserId(), (Student) listingUser);
                            }

                            System.out.println("Apartment listing created.");
                        } else if (listingTypeStr.equalsIgnoreCase("townhouse")) {
                            boolean hasGarage = promptTownhouseHasGarage();
                            boolean hasDriveway = promptTownhouseHasDriveway();
                            boolean hasYard = promptTownhouseHasYard();
                            boolean hasFence = promptTownhouseHasFence();

                            // add new townhouse to rm
                            Townhouse newTownhouse = new Townhouse(propertyId, description, price, leaseMonths, squareFootage,
                                    isSublease, utilitiesIncluded, numBedrooms, numBathrooms,
                                    hasShuttle, available, hasWasher, hasDryer, hasGarage, hasDriveway, hasYard, hasFence);
                            rm.addTownhouse(newTownhouse);

                            // associate new townhouse with property
                            Property parentProperty = rm.getPropertyById(propertyId);
                            parentProperty.associateListing(newTownhouse.getId());
                            rm.updateProperty(propertyId, parentProperty);

                            // associate new townhouse with listing user
                            User listingUser = rm.getUserById(currSession.getUserId());
                            listingUser.associateListing(newTownhouse.getId());
                            if (currUserType == UserType.PROPERTY_MANAGER) {
                                rm.updatePropertyManager(currSession.getUserId(), (PropertyManager) listingUser);
                            } else {
                                rm.updateStudent(currSession.getUserId(), (Student) listingUser);
                            }

                            System.out.println("Townhouse listing created.");
                        } else {
                            throw new InvalidInputException();
                        }
                        currFlow = Flow.DASHBOARD;
                        break;
                    case EDIT_LISTING:
                        Listing listingToEdit = rm.getListingById(currTarget);
                        ListingType listingToEditType = listingToEdit.getType();

                        if (listingToEditType == ListingType.APARTMENT) {
                            Apartment apartmentToEdit = (Apartment) listingToEdit;

                            System.out.println(apartmentToEdit.getDetails());

                            System.out.print("Select an attribute to edit, type 'DELETE' to delete, or press ENTER to return to dashboard:\nProperty\nDescription\nPrice\nLease months\nSquare footage\nIs sublease\nUtilities included\nNum bedrooms\nNum bathrooms\nHas shuttle\nAvailable\nApartment number\nHas parking\nSelection: ");
                            String attributeToEdit = keyboardInput.nextLine();

                            if (attributeToEdit.isEmpty()) {
                                System.out.println("it was empty");
                                rm.updateApartment(currTarget, apartmentToEdit);
                                currFlow = Flow.DASHBOARD;
                            } else if (attributeToEdit.equalsIgnoreCase("delete")) {
                                boolean deletionConfirmed = promptConfirmDeletion();
                                if (deletionConfirmed) {
                                    rm.removeApartment(apartmentToEdit.getId());
                                    // todo: remove listing from parent user as well
                                }
                            } else if (attributeToEdit.equalsIgnoreCase("property")) {
                                UUID newListingPropertyId = promptListingPropertyId();
                                apartmentToEdit.updatePropertyId(newListingPropertyId);
                                System.out.println("Property attribute updated.");
                            } else if (attributeToEdit.equalsIgnoreCase("description")) {
                                String newListingDescription = promptListingDescription();
                                apartmentToEdit.updateDescription(newListingDescription);
                                System.out.println("Description attribute updated.");
                            } else if (attributeToEdit.equalsIgnoreCase("price")) {
                                double newListingPrice = promptListingPrice();
                                apartmentToEdit.updatePrice(newListingPrice);
                                System.out.println("Price attribute updated.");
                            } else if (attributeToEdit.equalsIgnoreCase("lease months")) {
                                int newListingLeaseMonths = promptListingLeaseMonths();
                                apartmentToEdit.updateLeaseMonths(newListingLeaseMonths);
                                System.out.println("Lease months attribute updated.");
                            } else if (attributeToEdit.equalsIgnoreCase("square footage")) {
                                double newListingSquareFootage = promptListingSquareFootage();
                                apartmentToEdit.updateSquareFootage(newListingSquareFootage);
                                System.out.println("Square footage attribute updated.");
                            } else if (attributeToEdit.equalsIgnoreCase("is sublease")) {
                                boolean newListingIsSublease = promptListingIsSublease();
                                apartmentToEdit.updateIsSublease(newListingIsSublease);
                                System.out.println("Is sublease attribute updated.");
                            } else if (attributeToEdit.equalsIgnoreCase("utilities included")) {
                                boolean newListingUtilitiesIncluded = promptListingUtilitiesIncluded();
                                apartmentToEdit.updateUtilitiesIncluded(newListingUtilitiesIncluded);
                                System.out.println("Utilities included attribute updated.");
                            } else if (attributeToEdit.equalsIgnoreCase("num bedrooms")) {
                                int newListingNumBedrooms = promptListingNumBedrooms();
                                apartmentToEdit.updateNumBedrooms(newListingNumBedrooms);
                                System.out.println("Num bedrooms attribute updated.");
                            } else if (attributeToEdit.equalsIgnoreCase("num bathrooms")) {
                                int newListingNumBathrooms = promptListingNumBathrooms();
                                apartmentToEdit.updateNumBathrooms(newListingNumBathrooms);
                                System.out.println("Num bathrooms attribute updated.");
                            } else if (attributeToEdit.equalsIgnoreCase("has shuttle")) {
                                boolean newListingHasShuttle = promptListingHasShuttle();
                                apartmentToEdit.updateHasShuttle(newListingHasShuttle);
                                System.out.println("Has shuttle attribute updated.");
                            } else if (attributeToEdit.equalsIgnoreCase("available")) {
                                boolean newListingAvailable = promptListingAvailable();
                                apartmentToEdit.updateAvailable(newListingAvailable);
                                System.out.println("Available attribute updated.");
                            } else if (attributeToEdit.equalsIgnoreCase("apartment number")) {
                                String apartmentNumber = promptApartmentNumber();
                                apartmentToEdit.updateApartmentNumber(apartmentNumber);
                                System.out.println("Apartment number attribute updated.");
                            } else if (attributeToEdit.equalsIgnoreCase("has parking")) {
                                boolean hasParking = promptApartmentHasParking();
                                apartmentToEdit.updateHasParking(hasParking);
                                System.out.println("Has parking attribute updated.");
                            } else {
                                throw new InvalidInputException();
                            }
                        } else if (listingToEditType == ListingType.TOWNHOUSE) {
                            Townhouse townhouseToEdit = (Townhouse) listingToEdit;

                            System.out.println(townhouseToEdit.getDetails());

                            System.out.println("Select an attribute to edit, type 'DELETE' to delete, or press ENTER to return to dashboard:\nProperty\nDescription\nPrice\nLease months\nSquare footage\nIs sublease\nUtilities included\nNum bedrooms\nNum bathrooms\nHas shuttle\nAvailable\nHas garage\nHas driveway\nHas yard\nHas fence");
                            String attributeToEdit = keyboardInput.nextLine();

                            if (attributeToEdit.isEmpty()) {
                                rm.updateTownhouse(currTarget, townhouseToEdit);
                                currFlow = Flow.DASHBOARD;
                                continue;
                            }

                            switch (attributeToEdit.toLowerCase()) {
                                case "delete":
                                    boolean deletionConfirmed = promptConfirmDeletion();
                                    if (deletionConfirmed) {
                                        rm.removeTownhouse(townhouseToEdit.getId());
                                        // todo: remove listing from parent user as well
                                    }
                                    break;
                                case "property":
                                    UUID newListingPropertyId = promptListingPropertyId();
                                    townhouseToEdit.updatePropertyId(newListingPropertyId);
                                    System.out.println("Property attribute updated.");
                                    break;
                                case "description":
                                    String newListingDescription = promptListingDescription();
                                    townhouseToEdit.updateDescription(newListingDescription);
                                    System.out.println("Description attribute updated.");
                                    break;
                                case "price":
                                    double newListingPrice = promptListingPrice();
                                    townhouseToEdit.updatePrice(newListingPrice);
                                    System.out.println("Price attribute updated.");
                                    break;
                                case "lease months":
                                    int newListingLeaseMonths = promptListingLeaseMonths();
                                    townhouseToEdit.updateLeaseMonths(newListingLeaseMonths);
                                    System.out.println("Lease months attribute updated.");
                                    break;
                                case "square footage":
                                    double newListingSquareFootage = promptListingSquareFootage();
                                    townhouseToEdit.updateSquareFootage(newListingSquareFootage);
                                    System.out.println("Square footage attribute updated.");
                                    break;
                                case "is sublease":
                                    boolean newListingIsSublease = promptListingIsSublease();
                                    townhouseToEdit.updateIsSublease(newListingIsSublease);
                                    System.out.println("Is sublease attribute updated.");
                                    break;
                                case "utilities included":
                                    boolean newListingUtilitiesIncluded = promptListingUtilitiesIncluded();
                                    townhouseToEdit.updateUtilitiesIncluded(newListingUtilitiesIncluded);
                                    System.out.println("Utilities included attribute updated.");
                                    break;
                                case "num bedrooms":
                                    int newListingNumBedrooms = promptListingNumBedrooms();
                                    townhouseToEdit.updateNumBedrooms(newListingNumBedrooms);
                                    System.out.println("Num bedrooms attribute updated.");
                                    break;
                                case "num bathrooms":
                                    int newListingNumBathrooms = promptListingNumBathrooms();
                                    townhouseToEdit.updateNumBathrooms(newListingNumBathrooms);
                                    System.out.println("Num bathrooms attribute updated.");
                                    break;
                                case "has shuttle":
                                    boolean newListingHasShuttle = promptListingHasShuttle();
                                    townhouseToEdit.updateHasShuttle(newListingHasShuttle);
                                    System.out.println("Has shuttle attribute updated.");
                                    break;
                                case "available":
                                    boolean newListingAvailable = promptListingAvailable();
                                    townhouseToEdit.updateAvailable(newListingAvailable);
                                    System.out.println("Available attribute updated.");
                                    break;
                                case "has garage":
                                    boolean hasGarage = promptTownhouseHasGarage();
                                    townhouseToEdit.updateHasGarage(hasGarage);
                                    System.out.println("Has garage attribute updated.");
                                    break;
                                case "has driveway":
                                    boolean hasDriveway = promptTownhouseHasDriveway();
                                    townhouseToEdit.updateHasDriveway(hasDriveway);
                                    System.out.println("Has driveway attribute updated.");
                                    break;
                                case "has yard":
                                    boolean hasYard = promptTownhouseHasYard();
                                    townhouseToEdit.updateHasYard(hasYard);
                                    System.out.println("Has yard attribute updated.");
                                    break;
                                case "has fence":
                                    boolean hasFence = promptTownhouseHasFence();
                                    townhouseToEdit.updateHasFence(hasFence);
                                    System.out.println("Has fence attribute updated.");
                                    break;
                                default:
                                    throw new InvalidInputException();
                            }
                        } else {
                            throw new UnexpectedException("Invalid listing type: " + listingToEditType.name());
                        }
                        break;
                    case CREATE_REVIEW:
                        // todo: set currTarget UUID in preceding flow (likely 'view listing details' or 'view student details'?)
                        if (currUserType == UserType.STUDENT) {
                            Property propertyToReview = rm.getPropertyById(currTarget);
                            //String propertyToReviewName = propertyToReview.getName();

                            int stars = promptRatingStars();
                            String comment = promptRatingComment();
                            int valueStars = promptPropertyRatingValueStars();
                            int managementStars = promptPropertyRatingManagementStars();
                            int neighborhoodStars = promptPropertyRatingNeighborhoodStars();

                            // add new property rating to rm
                            PropertyRating newPropertyRating = new PropertyRating(stars, comment, valueStars, managementStars, neighborhoodStars);
                            rm.addPropertyRating(newPropertyRating);

                            // associate property rating with property
                            propertyToReview.associateRating(newPropertyRating.getId());
                            rm.updateProperty(propertyToReview.getId(), propertyToReview);

                            System.out.println("Property review created.");
                        } else if (currUserType == UserType.PROPERTY_MANAGER) {
                            Student studentToReview = rm.getStudentById(currTarget);
                            //String studentToReviewName = studentToReview.getFirstName() + " " + studentToReview.getLastName();

                            int stars = promptRatingStars();
                            String comment = promptRatingComment();
                            int numLatePayments = promptStudentRatingNumLatePayments();
                            double damagesValue = promptStudentRatingDamagesValue();

                            // add new student rating to rm
                            StudentRating newStudentRating = new StudentRating(stars, comment, numLatePayments, damagesValue);
                            rm.addStudentRating(newStudentRating);

                            // associate student rating with student
                            studentToReview.associateRating(newStudentRating.getId());
                            rm.updateStudent(studentToReview.getId(), studentToReview);

                            System.out.println("Student review created.");
                        } else {
                            currFlow = Flow.DASHBOARD;
                            throw new InvalidPermissionException();
                        }
                        break;
                    case EDIT_REVIEW:
                        Rating ratingToEdit = rm.getRatingById(currTarget);
                        RatingType ratingToEditType = ratingToEdit.getType();

                        if (ratingToEditType == RatingType.PROPERTY_RATING) {
                            // cast to PropertyRating
                            PropertyRating propertyRatingToEdit = (PropertyRating) ratingToEdit;

                            if (currUserType != UserType.STUDENT) {
                                throw new InvalidPermissionException();
                            }
                            System.out.println("Select an attribute to edit or press ENTER to return to dashboard:\nOverall rating\nComment\nValue rating\nManagement rating\nNeighborhood rating");
                            String attributeSelection = keyboardInput.nextLine();

                            switch (attributeSelection) {
                                case SysConst.CMD_ENTER:
                                    rm.updatePropertyRating(propertyRatingToEdit.getId(), propertyRatingToEdit);
                                    currFlow = Flow.DASHBOARD;
                                    break;
                                case "Overall rating":
                                    propertyRatingToEdit.setStars(promptRatingStars());
                                    break;
                                case "Comment":
                                    propertyRatingToEdit.setComment(promptRatingComment());
                                    break;
                                case "Value rating":
                                    propertyRatingToEdit.setValueStars(promptPropertyRatingValueStars());
                                    break;
                                case "Management rating":
                                    propertyRatingToEdit.setManagementStars(promptPropertyRatingManagementStars());
                                    break;
                                case "Neighborhood rating":
                                    propertyRatingToEdit.setNeighborhoodStars(promptPropertyRatingNeighborhoodStars());
                                    break;
                                default:
                                    throw new InvalidInputException();
                            }
                        } else if (ratingToEditType == RatingType.STUDENT_RATING) {
                            // cast to StudentRating
                            StudentRating studentRatingToEdit = (StudentRating) ratingToEdit;

                            if (currUserType != UserType.PROPERTY_MANAGER) {
                                throw new InvalidPermissionException();
                            }
                            System.out.println("Select an attribute to edit or press ENTER to return to dashboard:\nOverall rating\nComment\nNum late payments\nDamages value");
                            String attributeSelection = keyboardInput.nextLine();

                            switch (attributeSelection) {
                                case SysConst.CMD_ENTER:
                                    rm.updateStudentRating(studentRatingToEdit.getId(), studentRatingToEdit);
                                    currFlow = Flow.DASHBOARD;
                                    break;
                                case "Overall rating":
                                    studentRatingToEdit.setStars(promptRatingStars());
                                    break;
                                case "Comment":
                                    studentRatingToEdit.setComment(promptRatingComment());
                                    break;
                                case "Num late payments":
                                    studentRatingToEdit.setNumLatePayments(promptStudentRatingNumLatePayments());
                                    break;
                                case "Damages value":
                                    studentRatingToEdit.setDamagesValue(promptStudentRatingDamagesValue());
                                    break;
                                default:
                                    throw new InvalidInputException();
                            }
                        } else {
                            throw new UnexpectedException("Invalid rating type: " + ratingToEditType.name());
                        }
                        break;
                    case VIEW_PROFILE:
                        break;
                    case VIEW_FAVORITES:
                        if (currUserType == UserType.STUDENT) {
                            Student currStudent = rm.getStudentById(currSession.getUserId());
                            ArrayList<UUID> listingFavorites = currStudent.getListingFavorites();
                            System.out.println("-----\nYour favorites list\n-----");
                            for (int i=0; i<listingFavorites.size(); i++) {
                                int lineNum = i+1;
                                System.out.println(String.format("(%d) %s", lineNum, rm.getListingById(listingFavorites.get(i)).toString()));
                            }

                            boolean showingFavorites = true;
                            while (showingFavorites) {
                                System.out.print("Enter a listing favorite number to view details, or press ENTER to return to dashboard: ");
                                input = keyboardInput.next();
                                keyboardInput.nextLine();
                                if (input.equals(SysConst.CMD_ENTER)) {
                                    currFlow = Flow.DASHBOARD;
                                    showingFavorites = false;
                                } else {
                                    int listingFavoriteIndex = Integer.parseInt(input) - 1;
                                    UUID listingId = listingFavorites.get(listingFavoriteIndex);
                                    System.out.println(rm.getListingById(listingId).getDetails());
                                }
                            }
                        } else {
                            throw new InvalidPermissionException();
                        }
                        break;
                    case REGISTER_PROPERTY:
                    	System.out.print("Name of the property: ");
                    	String propertyName = keyboardInput.nextLine();

                    	System.out.print("Address: ");
                    	String address = keyboardInput.nextLine();

                    	System.out.print("Distance to campus (km): ");
                    	double distanceToCampus = keyboardInput.nextDouble();
                    	keyboardInput.nextLine();

                    	System.out.print("Furnished (y/n): ");
                    	boolean furnished = keyboardInput.next().equalsIgnoreCase("y");
                    	keyboardInput.nextLine();

                    	System.out.print("Pets allowed (y/n): ");
                    	boolean petsAllowed = keyboardInput.next().equalsIgnoreCase("y");
                    	keyboardInput.nextLine();

                    	System.out.print("Has pool (y/n): ");
                    	boolean hasPool = keyboardInput.next().equalsIgnoreCase("y");
                    	keyboardInput.nextLine();

                    	System.out.print("Has gym (y/n): ");
                    	boolean hasGym = keyboardInput.next().equalsIgnoreCase("y");
                    	keyboardInput.nextLine();

                    	System.out.print("Has free Wifi (y/n): ");
                    	boolean hasFreeWifi = keyboardInput.next().equalsIgnoreCase("y");
                    	keyboardInput.nextLine();

                    	// create property and add to resource manager
                    	Property newProperty = new Property(propertyName, address, distanceToCampus, furnished, petsAllowed,
                                hasPool, hasGym, hasFreeWifi);
                    	rm.addProperty(newProperty);

                    	// append property to user and update user in resource manager
                    	PropertyManager currPropertyManager = rm.getPropertyManagerById(currSession.getUserId());
                    	currPropertyManager.associateProperty(newProperty.getId());
                    	rm.updatePropertyManager(currPropertyManager.getId(), currPropertyManager);

                    	System.out.println("Property registered.");
                    	currFlow = Flow.DASHBOARD;
                        break;
                    default:
                        throw new UnexpectedException("Invalid flow: " + currFlow.name());
                }
            } catch (InvalidSessionDetailsException e) {
                currFlow = Flow.HOME;
                System.out.println(e.getMessage());
            } catch (Exception e) {
                e.printStackTrace();
                System.out.println(e.getMessage());
            }
        }
    }

    private static double[] promptPriceRange() {
        System.out.print("Lower price range: ");
        double priceRangeLower = Double.parseDouble(keyboardInput.next());
        keyboardInput.nextLine();

        System.out.print("Upper price range: ");
        double priceRangeUpper = Double.parseDouble(keyboardInput.next());
        keyboardInput.nextLine();

        return new double[] {priceRangeLower, priceRangeUpper};
    }

    private static void printSearchResults(ArrayList<Listing> searchResults) {
        // print each listing that fits given search params
        System.out.println("-----\nSearch Results\n-----");
        for (int i=0; i<searchResults.size(); i++) {
            int resultNum = i+1;
            System.out.println(String.format("(%d) %s", resultNum, searchResults.get(i).toString()));
        }

        // prompt user to either return to dashboard or view listing details
        boolean showingSearchResults = true;
        while (showingSearchResults) {
            System.out.print("Enter a listing number to view details, or press ENTER to return to dashboard: ");
            input = keyboardInput.nextLine();
            if (input.isEmpty()) {
                currFlow = Flow.DASHBOARD;
                showingSearchResults = false;
            } else {
                int listingIndex = Integer.parseInt(input) - 1;
                System.out.println(searchResults.get(listingIndex).getDetails());
            }
        }
    }

    private static UUID promptListingPropertyId() {
        System.out.println("-----\nFill in the following listing details\n-----");
        System.out.print("Location (property) name: ");
        String propertyName = keyboardInput.nextLine();
        Property foundProperty = rm.getPropertyByName(propertyName);
        if (foundProperty != null) {
            return foundProperty.getId();
        } else {
            return null;
        }
    }

    private static String promptListingDescription() {
        System.out.print("Short description: ");
        return keyboardInput.nextLine();
    }

    private static double promptListingPrice() {
        System.out.print("Price: $");
        double price = keyboardInput.nextDouble();
        keyboardInput.nextLine();
        return price;
    }

    private static int promptListingLeaseMonths() {
        System.out.print("Lease duration (months): ");
        int leaseMonths = keyboardInput.nextInt();
        keyboardInput.nextLine();
        return leaseMonths;
    }

    private static double promptListingSquareFootage() {
        System.out.print("Square footage: ");
        double squareFootage = keyboardInput.nextDouble();
        keyboardInput.nextLine();
        return squareFootage;
    }

    private static boolean promptListingPetsAllowed() {
        System.out.print("Pets allowed (y/n): ");
        boolean petsAllowed = keyboardInput.next().toLowerCase().equals("y");
        keyboardInput.nextLine();
        return petsAllowed;
    }

    private static boolean promptListingIsSublease() {
        System.out.print("Is sublease (y/n): ");
        boolean isSublease = keyboardInput.next().toLowerCase().equals("y");
        keyboardInput.nextLine();
        return isSublease;
    }

    private static boolean promptListingUtilitiesIncluded() {
        System.out.print("Utilities included (y/n): ");
        boolean utilitiesIncluded = keyboardInput.next().toLowerCase().equals("y");
        keyboardInput.nextLine();
        return utilitiesIncluded;
    }

    private static int promptListingNumBedrooms() {
        System.out.print("Number of bedrooms: ");
        int numBedrooms = keyboardInput.nextInt();
        keyboardInput.nextLine();
        return numBedrooms;
    }

    private static int promptListingNumBathrooms() {
        System.out.print("Number of bathrooms: ");
        int numBathrooms = keyboardInput.nextInt();
        keyboardInput.nextLine();
        return numBathrooms;
    }

    private static boolean promptListingHasShuttle() {
        System.out.print("Has shuttle service (y/n): ");
        boolean hasShuttle = keyboardInput.next().toLowerCase().equals("y");
        keyboardInput.nextLine();
        return hasShuttle;
    }

    private static boolean promptListingAvailable() {
        System.out.print("Currently active listing (y/n): ");
        boolean available = keyboardInput.next().toLowerCase().equals("y");
        keyboardInput.nextLine();
        return available;
    }

    private static boolean promptListingHasWasher() {
        System.out.print("Has washer (y/n): ");
        boolean hasWasher = keyboardInput.next().equalsIgnoreCase("y");
        keyboardInput.nextLine();
        return hasWasher;
    }

    private static boolean promptListingHasDryer() {
        System.out.print("Has dryer (y/n): ");
        boolean hasDryer = keyboardInput.next().equalsIgnoreCase("y");
        keyboardInput.nextLine();
        return hasDryer;
    }

    private static int promptRatingStars() {
        System.out.print("Overall rating (1-5): ");
        int stars = keyboardInput.nextInt();
        keyboardInput.nextLine();
        return stars;
    }

    private static String promptRatingComment() {
        System.out.print("Enter a short comment for your review: ");
        return keyboardInput.nextLine();
    }

    private static int promptPropertyRatingValueStars() {
        System.out.print("Value rating (1-5): ");
        int valueStars = keyboardInput.nextInt();
        keyboardInput.nextLine();
        return valueStars;
    }

    private static int promptPropertyRatingManagementStars() {
        System.out.print("Management rating (1-5): ");
        int managementStars = keyboardInput.nextInt();
        keyboardInput.nextLine();
        return managementStars;
    }

    private static int promptPropertyRatingNeighborhoodStars() {
        System.out.print("Neighborhood rating (1-5): ");
        int neighborhoodStars = keyboardInput.nextInt();
        keyboardInput.nextLine();
        return neighborhoodStars;
    }

    private static int promptStudentRatingNumLatePayments() {
        System.out.print("Number of late payments made: ");
        int numLatePayments = keyboardInput.nextInt();
        keyboardInput.nextLine();
        return numLatePayments;
    }

    private static double promptStudentRatingDamagesValue() {
        System.out.print("Value of damages caused: ");
        double damagesValue = keyboardInput.nextDouble();
        keyboardInput.nextLine();
        return damagesValue;
    }

    private static String promptApartmentNumber() {
        System.out.print("Apartment number: ");
        String apartmentNumber = keyboardInput.next();
        keyboardInput.nextLine();
        return apartmentNumber;
    }

    private static boolean promptApartmentHasParking() {
        System.out.print("Has parking (y/n): ");
        boolean hasParking = keyboardInput.next().equalsIgnoreCase("y");
        keyboardInput.nextLine();
        return hasParking;
    }

    private static boolean promptTownhouseHasGarage() {
        System.out.println("Has garage (y/n): ");
        boolean hasGarage = keyboardInput.next().equalsIgnoreCase("y");
        keyboardInput.nextLine();
        return hasGarage;
    }

    private static boolean promptTownhouseHasDriveway() {
        System.out.println("Has driveway (y/n): ");
        boolean hasDriveway = keyboardInput.next().equalsIgnoreCase("y");
        keyboardInput.nextLine();
        return hasDriveway;
    }

    private static boolean promptTownhouseHasYard() {
        System.out.println("Has yard (y/n): ");
        boolean hasYard = keyboardInput.next().equalsIgnoreCase("y");
        keyboardInput.nextLine();
        return hasYard;
    }

    private static boolean promptTownhouseHasFence() {
        System.out.println("Has fence (y/n): ");
        boolean hasFence = keyboardInput.next().equalsIgnoreCase("y");
        keyboardInput.nextLine();
        return hasFence;
    }

    private static boolean promptConfirmDeletion() {
        System.out.println("Are you sure you wish to delete this object (y/n)? ");
        boolean deletionConfirmed = keyboardInput.next().equalsIgnoreCase("y");
        keyboardInput.nextLine();
        return deletionConfirmed;
    }
}
