package housingapp;

import housingapp.errors.*;
import housingapp.housing.Listing;
import housingapp.housing.Property;
import housingapp.query.ListingQuery;
import housingapp.query.ResourceManager;
import housingapp.rating.PropertyRating;
import housingapp.rating.Rating;
import housingapp.rating.StudentRating;
import housingapp.system.Flow;
import housingapp.system.RatingType;
import housingapp.system.SysConst;
import housingapp.system.UserType;
import housingapp.user.PropertyManager;
import housingapp.user.Student;

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
                            case "2":
                                currFlow = Flow.SIGN_UP;
                            case "3":
                                currUserType = UserType.GUEST;
                                currFlow = Flow.DASHBOARD;
                            default:
                                throw new InvalidInputException();
                        }
                    case LOG_IN:
                        System.out.print("Email: ");
                        String email = keyboardInput.next();
                        keyboardInput.nextLine();
                        System.out.print("Password: ");
                        String password = String.valueOf(System.console().readPassword());
                        keyboardInput.nextLine();
                        currSession = rm.login(email, password);
                        if (currSession != null) {
                            rm.getUserById(currSession.getUserId());
                            currUserType = rm.getUserById(currSession.getUserId()).getType();
                            currFlow = Flow.DASHBOARD;
                        } else {
                            throw new InvalidLoginException();
                        }
                    case SIGN_UP:
                        System.out.print("Select an account type, 'student' or 'property manager': ");
                        String accountType = keyboardInput.next();
                        keyboardInput.nextLine();

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
                        String newPassword = String.valueOf(System.console().readPassword());
                        keyboardInput.nextLine();

                        if (accountType.toLowerCase().equals("student")) {
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
                        } else if (accountType.toLowerCase().equals("property manager")) {
                            System.out.print("Office address: ");
                            String officeAddress = keyboardInput.next();
                            keyboardInput.nextLine();

                            rm.addPropertyManager(new PropertyManager(firstName, lastName, phone, newEmail, newPassword,
                                    officeAddress));
                        } else {
                            throw new InvalidInputException();
                        }
                    case DASHBOARD:
                        System.out.println("-----\nDashboard\n-----");
                        // validate user session
                        if (rm.validateSession(currSession)) {
                            // prompt user for action depending on user type
                            if (currUserType == UserType.STUDENT) {
                                System.out.println(String.format("Select an option:\n(%s) Search listings\n(%s) View " +
                                                "favorites list\n(%s) View my listings\n(%s) View my reviews\n(%s) Create a " +
                                                "listing\n(%s) Create a review\n(%s) View profile", SysConst.CMD_SEARCH_LISTINGS,
                                        SysConst.CMD_VIEW_FAVORITES, SysConst.CMD_VIEW_MY_LISTINGS, SysConst.CMD_VIEW_MY_REVIEWS,
                                        SysConst.CMD_CREATE_LISTING, SysConst.CMD_CREATE_REVIEW, SysConst.CMD_VIEW_PROFILE));
                            } else if (currUserType == UserType.PROPERTY_MANAGER) {
                                System.out.println(String.format("Select an option:\n(%s) Search listings\n(%s) View my listings\n(%s)" +
                                                " View my reviews\n(%s) Create a listing\n(%s) Register a property\n(%s) Create a review\n(%s)" +
                                                " View profile", SysConst.CMD_SEARCH_LISTINGS, SysConst.CMD_VIEW_MY_LISTINGS, SysConst.CMD_VIEW_MY_REVIEWS,
                                        SysConst.CMD_CREATE_LISTING, SysConst.CMD_REGISTER_PROPERTY, SysConst.CMD_CREATE_REVIEW, SysConst.CMD_VIEW_PROFILE));
                            } else if (currUserType == UserType.GUEST) {
                                System.out.println(String.format("Select an option:\n(%s) Search listings", SysConst.CMD_SEARCH_LISTINGS));
                            } else {
                                throw new InvalidSessionDetailsException("Invalid user type: " + currUserType.toString());
                            }

                            // validate action input against user permissions
                            input = keyboardInput.nextLine();
                            switch (input.toLowerCase()) {
                                case SysConst.CMD_SEARCH_LISTINGS:
                                    currFlow = Flow.SEARCH_LISTINGS;
                                case SysConst.CMD_VIEW_FAVORITES:
                                    if (currUserType == UserType.STUDENT) {
                                        currFlow = Flow.VIEW_FAVORITES;
                                    } else {
                                        throw new InvalidPermissionException();
                                    }
                                    return;
                                case SysConst.CMD_VIEW_MY_LISTINGS:
                                    if (currUserType != UserType.GUEST) {
                                        currFlow = Flow.VIEW_MY_LISTINGS;
                                    } else {
                                        throw new InvalidPermissionException();
                                    }
                                case SysConst.CMD_VIEW_MY_REVIEWS:
                                    if (currUserType != UserType.GUEST) {
                                        currFlow = Flow.VIEW_MY_REVIEWS;
                                    } else {
                                        throw new InvalidPermissionException();
                                    }
                                case SysConst.CMD_CREATE_LISTING:
                                    if (currUserType != UserType.GUEST) {
                                        currFlow = Flow.CREATE_LISTING;
                                    } else {
                                        throw new InvalidPermissionException();
                                    }
                                case SysConst.CMD_CREATE_REVIEW:
                                    if (currUserType != UserType.GUEST) {
                                        currFlow = Flow.CREATE_REVIEW;
                                    } else {
                                        throw new InvalidPermissionException();
                                    }
                                case SysConst.CMD_VIEW_PROFILE:
                                    if (currUserType != UserType.GUEST) {
                                        currFlow = Flow.VIEW_PROFILE;
                                    } else {
                                        throw new InvalidPermissionException();
                                    }
                                case SysConst.CMD_REGISTER_PROPERTY:
                                    if (currUserType == UserType.PROPERTY_MANAGER) {
                                        currFlow = Flow.REGISTER_PROPERTY;
                                    } else {
                                        throw new InvalidPermissionException();
                                    }
                            }
                        } else {
                            System.out.println("Session expired. Returning to home screen.");
                            currFlow = Flow.HOME;
                        }
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
                            case SysConst.CMD_SEARCH_LISTINGS_BY_PROPERTY:
                                System.out.print("Enter a property to search: ");
                                String propertyName = keyboardInput.next();
                                keyboardInput.nextLine();
                                UUID propertyId = rm.getPropertyByName(propertyName).getId();

                                currListingSearchResults = query.getListingsByProperty(currListingSearchResults, propertyId);
                                printSearchResults(currListingSearchResults);
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
                                    case SysConst.CMD_SET_SEARCH_PARAM_PRICE:
                                        double[] priceRange = promptPriceRange();
                                        double priceRangeLower = priceRange[0];
                                        double priceRangeUpper = priceRange[1];
                                        currListingSearchResults = query.getListingsByPrice(currListingSearchResults, priceRangeLower, priceRangeUpper);
                                        System.out.println("Price search param updated.");
                                        currFlow = Flow.SEARCH_LISTINGS;
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
                                    case SysConst.CMD_SET_SEARCH_PARAM_PET_POLICY:
                                        System.out.print("Allows pets (y/n): ");
                                        boolean  petsAllowed = keyboardInput.next().toLowerCase().equals("y");
                                        keyboardInput.nextLine();
                                        currListingSearchResults = query.getListingsByPetsAllowed(currListingSearchResults, petsAllowed);
                                        System.out.println("Pet policy param updated.");
                                        currFlow = Flow.SEARCH_LISTINGS;
                                    case SysConst.CMD_SET_SEARCH_PARAM_UTILITIES:
                                        System.out.print("Utilities included in rent price (y/n): ");
                                        boolean utilitiesIncluded = keyboardInput.next().toLowerCase().equals("y");
                                        keyboardInput.nextLine();
                                        currListingSearchResults = query.getListingsByUtilitiesIncluded(currListingSearchResults, utilitiesIncluded);
                                        System.out.println("Utilities param updated.");
                                        currFlow = Flow.SEARCH_LISTINGS;
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
                                    case SysConst.CMD_SET_SEARCH_PARAM_SHUTTLE_SERVICE:
                                        System.out.print("Has shuttle service (y/n): ");
                                        boolean hasShuttle = keyboardInput.next().toLowerCase().equals("y");
                                        keyboardInput.nextLine();
                                        currListingSearchResults = query.getListingsByHasShuttle(currListingSearchResults, hasShuttle);
                                        System.out.println("Shuttle service param updated.");
                                        currFlow = Flow.SEARCH_LISTINGS;
                                }
                            default:
                                throw new InvalidInputException();
                        }
                    case VIEW_MY_LISTINGS:
                    	int listingIndex = 0;
                    	System.out.println("Would you like to remove this listing (y/n)? ");
                    	String userResponse = keyboardInput.next();
                    	keyboardInput.nextLine();
                    	if (userResponse.equalsIgnoreCase("y")) {
                    		PropertyManager currPropertyManager = (PropertyManager) rm.getUserById(currSession.getUserId());
                    		currPropertyManager.removeListing(currPropertyManager.getListings().get(listingIndex));
                    	}
                    	else {
                    		currFlow = Flow.VIEW_MY_LISTINGS;
                    	}
                    case VIEW_MY_REVIEWS:
                        return;
                    case CREATE_LISTING:
                        Property property = promptListingProperty();
                        String description = promptListingDescription();
                        double price = promptListingPrice();
                        int leaseMonths = promptListingLeaseMonths();
                        double squareFootage = promptListingSquareFootage();
                        boolean petsAllowed = promptListingPetsAllowed();
                        boolean isSublease = promptListingIsSublease();
                        boolean utilitiesIncluded = promptListingUtilitiesIncluded();
                        int numBedrooms = promptListingNumBedrooms();
                        int numBathrooms = promptListingNumBathrooms();
                        boolean hasShuttle = promptListingHasShuttle();
                        boolean available = promptListingAvailable();

                        rm.addListing(new Listing(property, description, price, leaseMonths, squareFootage, petsAllowed,
                                isSublease, utilitiesIncluded, numBedrooms, numBathrooms, hasShuttle, available));
                        currFlow = Flow.DASHBOARD;
                    case EDIT_LISTING:
                        // todo: set currTarget in preceding flow
                        Listing listingToEdit = rm.getListingById(currTarget);
                        System.out.println(listingToEdit.getDetails());

                        System.out.println("Select an attribute to edit or press ENTER to return to dashboard:\nProperty\nDescription\nPrice\nLease months\nSquare footage\nPets allowed\nIs sublease\nUtilities included\nNum bedrooms\nNum bathrooms\nHas shuttle\nAvailable");
                        String attributeToEdit = keyboardInput.nextLine();

                        switch (attributeToEdit) {
                            case SysConst.CMD_ENTER:
                                rm.updateListing(currTarget, listingToEdit);
                                currFlow = Flow.DASHBOARD;
                            case "Property":
                                Property newListingProperty = promptListingProperty();
                                listingToEdit.setProperty(newListingProperty);
                                System.out.println("Property attribute updated.");
                            case "Description":
                                String newListingDescription = promptListingDescription();
                                listingToEdit.setDescription(newListingDescription);
                                System.out.println("Description attribute updated.");
                            case "Price":
                                double newListingPrice = promptListingPrice();
                                listingToEdit.setPrice(newListingPrice);
                                System.out.println("Price attribute updated.");
                            case "Lease months":
                                int newListingLeaseMonths = promptListingLeaseMonths();
                                listingToEdit.setLeaseMonths(newListingLeaseMonths);
                                System.out.println("Lease months attribute updated.");
                            case "Square footage":
                                double newListingSquareFootage = promptListingSquareFootage();
                                listingToEdit.setSquareFootage(newListingSquareFootage);
                                System.out.println("Square footage attribute updated.");
                            case "Pets allowed":
                                boolean newListingPetsAllowed = promptListingPetsAllowed();
                                listingToEdit.setPetsAllowed(newListingPetsAllowed);
                                System.out.println("Pets allowed attribute updated.");
                            case "Is sublease":
                                boolean newListingIsSublease = promptListingIsSublease();
                                listingToEdit.setIsSublease(newListingIsSublease);
                                System.out.println("Is sublease attribute updated.");
                            case "Utilities included":
                                boolean newListingUtilitiesIncluded = promptListingUtilitiesIncluded();
                                listingToEdit.setUtilitiesIncluded(newListingUtilitiesIncluded);
                                System.out.println("Utilities included attribute updated.");
                            case "Num bedrooms":
                                int newListingNumBedrooms = promptListingNumBedrooms();
                                listingToEdit.setNumBedrooms(newListingNumBedrooms);
                                System.out.println("Num bedrooms attribute updated.");
                            case "Num bathrooms":
                                int newListingNumBathrooms = promptListingNumBathrooms();
                                listingToEdit.setNumBathrooms(newListingNumBathrooms);
                                System.out.println("Num bathrooms attribute updated.");
                            case "Has shuttle":
                                boolean newListingHasShuttle = promptListingHasShuttle();
                                listingToEdit.setHasShuttle(newListingHasShuttle);
                                System.out.println("Has shuttle attribute updated.");
                            case "Available":
                                boolean newListingAvailable = promptListingAvailable();
                                listingToEdit.setAvailable(newListingAvailable);
                                System.out.println("Available attribute updated.");
                            default:
                                throw new InvalidInputException();
                        }
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

                            rm.addPropertyRating(new PropertyRating(stars, comment, valueStars, managementStars, neighborhoodStars));
                        } else if (currUserType == UserType.PROPERTY_MANAGER) {
                            Student studentToReview = rm.getStudentById(currTarget);
                            //String studentToReviewName = studentToReview.getFirstName() + " " + studentToReview.getLastName();

                            int stars = promptRatingStars();
                            String comment = promptRatingComment();
                            int numLatePayments = promptStudentRatingNumLatePayments();
                            double damagesValue = promptStudentRatingDamagesValue();

                            rm.addStudentRating(new StudentRating(stars, comment, numLatePayments, damagesValue));
                        } else {
                            currFlow = Flow.DASHBOARD;
                            throw new InvalidPermissionException();
                        }
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
                                case "Overall rating":
                                    propertyRatingToEdit.setStars(promptRatingStars());
                                case "Comment":
                                    propertyRatingToEdit.setComment(promptRatingComment());
                                case "Value rating":
                                    propertyRatingToEdit.setValueStars(promptPropertyRatingValueStars());
                                case "Management rating":
                                    propertyRatingToEdit.setManagementStars(promptPropertyRatingManagementStars());
                                case "Neighborhood rating":
                                    propertyRatingToEdit.setNeighborhoodStars(promptPropertyRatingNeighborhoodStars());
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
                                case "Overall rating":
                                    studentRatingToEdit.setStars(promptRatingStars());
                                case "Comment":
                                    studentRatingToEdit.setComment(promptRatingComment());
                                case "Num late payments":
                                    studentRatingToEdit.setNumLatePayments(promptStudentRatingNumLatePayments());
                                case "Damages value":
                                    studentRatingToEdit.setDamagesValue(promptStudentRatingDamagesValue());
                                default:
                                    throw new InvalidInputException();
                            }
                        } else {
                            throw new UnexpectedException("Invalid rating type: " + ratingToEditType.name());
                        }
                    case VIEW_PROFILE:
                        return;
                    case VIEW_FAVORITES:
                        return;
                    case REGISTER_PROPERTY:
                    	String newPropertyName;
                    	String newPropertyAddress;
                    	double newDistanceToCampus;
                    	
                    	System.out.print("Please enter name of the property: ");
                    	newPropertyName = keyboardInput.next();
                    	keyboardInput.nextLine();
                    	System.out.print("Please enter the address of the property: ");
                    	newPropertyAddress = keyboardInput.next();
                    	keyboardInput.nextLine();
                    	System.out.print("Please enter the distance of the property from campus: ");
                    	newDistanceToCampus = keyboardInput.nextDouble();
                    	Property newProperty = new Property(newPropertyName, newPropertyAddress, newDistanceToCampus);
                    	rm.addProperty(newProperty);
                    	PropertyManager currPropertyManager = (PropertyManager) rm.getUserById(currSession.getUserId());
                    	currPropertyManager.associateProperty(newProperty.getId());
                        return;
                }
            } catch (InvalidSessionDetailsException e) {
                currFlow = Flow.HOME;
                System.out.println(e.getMessage());
            } catch (Exception e) {
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
            input = keyboardInput.next();
            keyboardInput.nextLine();
            if (input.equals(SysConst.CMD_ENTER)) {
                currFlow = Flow.DASHBOARD;
                showingSearchResults = false;
            } else {
                int listingIndex = Integer.parseInt(input) - 1;
                System.out.println(searchResults.get(listingIndex).getDetails());
            }
        }
    }

    private static Property promptListingProperty() {
        System.out.println("-----\nFill in the following listing details\n-----");
        System.out.print("Location (property) name: ");
        String propertyName = keyboardInput.next();
        keyboardInput.nextLine();
        return rm.getPropertyByName(propertyName);
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
}
