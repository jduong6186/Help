package housingapp;

public interface Action {

    public void execute(Session currSession);
    public boolean validatePermission(Session currSession);
}
