package housingapp;

public class Interpreter {

    private Session currSession;
    private HashMap<String, Action> actions;

    public Interpreter() {}

    protected boolean interpret(String input) {
        String inputLower = input.toLowerCase();
        if (inputLower.equals("login")) {

        }
        return false;
    }
}
