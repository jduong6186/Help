import javax.swing.*;
import java.util.Scanner;

public class HousingAppDriver {

    private static Scanner keyboardInput = new Scanner(System.in);
    private static Flow currFlow = Flow.DEFAULT;
    private static Session currSession;

    public static void main(String[] args) {
        Interpreter interpreter = new Interpreter();
        boolean running = true;

        System.out.println("-----\nUofSC Off-Campus Housing App\n-----");

        while (running) {
            switch (currFlow) {
                case DEFAULT:
                    System.out.println("Select an option:\n(1) Log in\n(2) Sign up");
                    String input = keyboardInput.nextLine();
                    switch (input) {
                        case "1":
                            currFlow = Flow.LOGIN;
                        case "2":
                            currFlow = Flow.SIGNUP;
                        default:
                            System.out.println("Invalid input.");
                    }
                case LOGIN:
                    
                    currSession = ResourceManager.login(email, password);
                    if (currSession != null) {
                        currFlow = Flow.DASHBOARD;
                    }
                case SIGNUP:
                    System.out.print("Email: ");
                case DASHBOARD:
                    return;
                case SEARCH:
                    return;
                case FAVORITES:
                    return;
            }
            running = interpreter.interpret(input);
        }
    }

    private static String[] getProfileDetails() {
        System.out.print("Email: ");
        String email = keyboardInput.next();
        keyboardInput.nextLine();
        System.out.print("Password: ");
        String password = PasswordManager.getEncryption(keyboardInput.next());
        keyboardInput.nextLine();
        return new String[] {email, password};
    }
}
