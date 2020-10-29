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
import housingapp.system.SysConst;
import housingapp.system.UserType;
import housingapp.user.PropertyManager;
import housingapp.user.Student;
import housingapp.user.User;

import java.util.ArrayList;
import java.util.Scanner;
import java.util.UUID;

public class HousingAppDriver {

    private static Scanner keyboardInput = new Scanner(System.in);
    private static Flow currFlow = Flow.HOME;
    private static UserType currUserType;
    private static Session currSession;
    private static String input;

    public static void main(String[] args) {
        ResourceManager rm = ResourceManager.getInstance();
        boolean running = true;
        ArrayList<Listing> currListingSearchResults = null;
        UUID currTarget = null;

        System.out.println("-----\nUofSC Off-Campus Housing App\n-----");

        while (running) {
            try {
                switch (currFlow) {
                    case HOME:
                        System.out.println("Select an option:\n(1) Log in\n(2) Sign up");
                        input = keyboardInput.nextLine();
                        switch (input) {
                            case "1":
                                currFlow = Flow.LOG_IN;
                            case "2":
                                currFlow = Flow.SIGN_UP;
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
                            currUserType = rm.getUserById(currSession.getUserId()).getUserType();
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

                        User newUser;
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

                            newUser = new Student(firstName, lastName, phone, newEmail, newPassword, hasPets,
                                    priceRangeLower, priceRangeUpper, maxTravelDistance, minRoommates, maxRoommates);
                        } else if (accountType.toLowerCase().equals("property manager")) {
                            System.out.print("Office address: ");
                            String officeAddress = keyboardInput.next();
                            keyboardInput.nextLine();

                            newUser = new PropertyManager(firstName, lastName, phone, newEmail, newPassword, officeAddress);
                        } else {
                            throw new InvalidInputException();
                        }
                        rm.addUser(newUser);
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
                            } else {
                                System.out.println("Invalid user type: " + currUserType.toString() + ". Returning to home screen.");
                                currFlow = Flow.HOME;
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
                                    currFlow = Flow.VIEW_MY_LISTINGS;
                                case SysConst.CMD_VIEW_MY_REVIEWS:
                                    currFlow = Flow.VIEW_MY_REVIEWS;
                                case SysConst.CMD_CREATE_LISTING:
                                    currFlow = Flow.CREATE_LISTING;
                                case SysConst.CMD_CREATE_REVIEW:
                                    currFlow = Flow.CREATE_REVIEW;
                                case SysConst.CMD_VIEW_PROFILE:
                                    currFlow = Flow.VIEW_PROFILE;
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
                        System.out.println(String.format("Select a search method:\n(%s) List recommended listings\n(%s) Search by property name\n(%s) Search by listing parameters",
                                SysConst.CMD_SEARCH_LISTINGS_BY_RECOMMENDED, SysConst.CMD_SEARCH_LISTINGS_BY_PROPERTY, SysConst.CMD_SEARCH_LISTINGS_BY_PARAMETERS));
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
                    	System.out.println("Would you like to remove this listing (yes or no)? ");
                    	String userResponse = keyboardInput.next();
                    	keyboardInput.nextLine();
                    	if (userResponse.equalsIgnoreCase("yes")) {
                    		PropertyManager currPropertyManager = (PropertyManager) rm.getUserById(currSession.getUserId());
                    		currPropertyManager.removeListing(currPropertyManager.getListings().get(listingIndex));
                    	}
                    	else {
                    		currFlow = Flow.VIEW_MY_LISTINGS;
                    	}
                        return;
                    case VIEW_MY_REVIEWS:
                    	int reviewIndex = 0;
                    	System.out.println("Would you like to remove this review (yes or no)? ");
                    	String response = keyboardInput.next();
                    	keyboardInput.nextLine();
                    	if (response.equalsIgnoreCase("yes") && currUserType == UserType.PROPERTY_MANAGER) {
                    		PropertyManager currPropertyManager = (PropertyManager) rm.getUserById(currSession.getUserId());
                    		currPropertyManager.removeRating(currPropertyManager.getRatings().get(reviewIndex));
                    	}
                    	else if (response.equalsIgnoreCase("yes") && currUserType == UserType.STUDENT) {
                    		Student currStudent = (Student) rm.getUserById(currSession.getUserId());
                    		currStudent.removeRating(currStudent.getRatings().get(reviewIndex));
                    	}
                    	else {
                    		currFlow = Flow.VIEW_MY_REVIEWS;
                    	}
                        return;
                    case CREATE_LISTING:
                        System.out.println("-----\nFill in the following listing details\n-----");
                        System.out.print("Location (property) name: ");
                        String propertyName = keyboardInput.next();
                        keyboardInput.nextLine();
                        Property property = rm.getPropertyByName(propertyName);

                        System.out.print("Short description: ");
                        String description = keyboardInput.nextLine();

                        System.out.print("Price: $");
                        double price = keyboardInput.nextDouble();
                        keyboardInput.nextLine();

                        System.out.print("Lease duration (months): ");
                        int leaseMonths = keyboardInput.nextInt();
                        keyboardInput.nextLine();

                        System.out.print("Square footage: ");
                        double squareFootage = keyboardInput.nextDouble();
                        keyboardInput.nextLine();

                        System.out.print("Pets allowed (y/n): ");
                        boolean petsAllowed = keyboardInput.next().toLowerCase().equals("y");
                        keyboardInput.nextLine();

                        System.out.print("Is sublease (y/n): ");
                        boolean isSublease = keyboardInput.next().toLowerCase().equals("y");
                        keyboardInput.nextLine();

                        System.out.print("Utilities included (y/n): ");
                        boolean utilitiesIncluded = keyboardInput.next().toLowerCase().equals("y");
                        keyboardInput.nextLine();

                        System.out.print("Number of bedrooms: ");
                        int numBedrooms = keyboardInput.nextInt();
                        keyboardInput.nextLine();

                        System.out.print("Number of bathrooms: ");
                        int numBathrooms = keyboardInput.nextInt();
                        keyboardInput.nextLine();

                        System.out.print("Has shuttle service (y/n): ");
                        boolean hasShuttle = keyboardInput.next().toLowerCase().equals("y");
                        keyboardInput.nextLine();

                        System.out.print("Currently active listing (y/n): ");
                        boolean available = keyboardInput.next().toLowerCase().equals("y");
                        keyboardInput.nextLine();

                        Listing newListing = new Listing(property, description, price, leaseMonths, squareFootage, petsAllowed,
                                isSublease, utilitiesIncluded, numBedrooms, numBathrooms, hasShuttle, available);
                        rm.addListing(newListing);
                        currFlow = Flow.DASHBOARD;
                    case EDIT_LISTING:
                        return;
                    case CREATE_REVIEW:
                        if (currUserType == UserType.STUDENT) {
                            Property propertyToReview = rm.getPropertyById(currTarget);
                            String propertyToReviewName = propertyToReview.getName();

                            System.out.print(String.format("Overall rating for %s (1-5): ", propertyToReviewName));
                            int stars = keyboardInput.nextInt();
                            keyboardInput.nextLine();

                            System.out.print("Enter a short comment for your review: ");
                            String comment = keyboardInput.nextLine();

                            System.out.print(String.format("Rate the value of %s (1-5): ", propertyToReviewName));
                            int valueStars = keyboardInput.nextInt();
                            keyboardInput.nextLine();

                            System.out.print(String.format("Rate the management of %s (1-5): ", propertyToReviewName));
                            int managementStars = keyboardInput.nextInt();
                            keyboardInput.nextLine();

                            System.out.print(String.format("Rate the neighborhood of %s (1-5): ", propertyToReviewName));
                            int neighborhoodStars = keyboardInput.nextInt();
                            keyboardInput.nextLine();

                            Rating propertyRating = new PropertyRating(stars, comment, valueStars, managementStars, neighborhoodStars);
                            rm.addRating(propertyRating);
                        } else if (currUserType == UserType.PROPERTY_MANAGER) {
                        	User studentToReview = rm.getUserById(currTarget);
                            String studentToReviewName = studentToReview.getLastName();

                            System.out.print(String.format("Overall rating for %s (1-5): ", studentToReviewName));
                            int stars = keyboardInput.nextInt();
                            keyboardInput.nextLine();
                            
                            System.out.print("Enter a short comment for your review: ");
                            String comment = keyboardInput.nextLine();
                            
                            System.out.print(String.format("Number of late payments (Any postitive num): ", studentToReviewName));
                            int numLatePayments = keyboardInput.nextInt();
                            keyboardInput.nextLine();
                            
                            System.out.print(String.format("Value of damages caused by %s (Any positive num): ", studentToReviewName));
                            int damagesValue = keyboardInput.nextInt();
                            keyboardInput.nextLine();
                            
                            Rating studentRating = new StudentRating(stars, comment, numLatePayments,  damagesValue);
                            rm.addRating(studentRating);
                        } else {
                            currFlow = Flow.HOME;
                            throw new InvalidPermissionException();
                        }
                    case EDIT_REVIEW:
                        return;
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
                    case UNREGISTER_PROPERTY:
                    	String oldPropertyAddress;
                    	String oldPropertyName;
                    	double oldDistanceToCampus;
                    	
                    	System.out.print("Please enter name of the property: ");
                    	oldPropertyName = keyboardInput.next();
                    	keyboardInput.nextLine();
                    	System.out.print("Please enter the address of the property: ");
                    	oldPropertyAddress = keyboardInput.next();
                    	keyboardInput.nextLine();
                    	System.out.print("Please enter the distance of the property from campus: ");
                    	oldDistanceToCampus = keyboardInput.nextDouble();
                    	//display properties by list and select like reviews/listings
                        return;
                }
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
}
