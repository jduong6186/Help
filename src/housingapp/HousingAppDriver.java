package housingapp;

import housingapp.system.Flow;
import housingapp.system.SysConst;
import housingapp.system.UserType;
import java.util.Scanner;

public class HousingAppDriver {

    private static Scanner keyboardInput = new Scanner(System.in);
    private static Flow currFlow = Flow.HOME;
    private static UserType currUserType;
    private static Session currSession;

    public static void main(String[] args) {
        ResourceManager rm = ResourceManager.getInstance();
        boolean running = true;
        String input;

        System.out.println("-----\nUofSC Off-Campus Housing App\n-----");

        while (running) {
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
                            System.out.println(SysConst.ERR_INVALID_INPUT);
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
                        currFlow = Flow.DASHBOARD;
                    } else {
                        System.out.println(SysConst.ERR_INVALID_LOGIN);
                    }
                case SIGN_UP:
                    System.out.print("First name: ");
                    String firstName = keyboardInput.next();
                    keyboardInput.nextLine();
                    System.out.print("Last name: ");
                    String lastName = keyboardInput.next();
                    keyboardInput.nextLine();
                    System.out.print("Phone number: ");
                    String phoneNumber = keyboardInput.next();
                    keyboardInput.nextLine();
                    System.out.print("Email: ");
                    String newEmail = keyboardInput.next();
                    keyboardInput.nextLine();
                    System.out.print("Password: ");
                    String newPassword = keyboardInput.next();
                    keyboardInput.nextLine();
                    User newUser = new User(firstName, lastName, phoneNumber, newEmail, newPassword);
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
                                    System.out.println(SysConst.ERR_INVALID_PERMISSION);
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
                                    System.out.println(SysConst.ERR_INVALID_PERMISSION);
                                }
                        }
                    } else {
                        System.out.println("Session expired. Returning to home screen.");
                        currFlow = Flow.HOME;
                    }
                case SEARCH_LISTINGS:
                    return;
                case VIEW_FAVORITES:
                    return;
            }
        }
    }
}
