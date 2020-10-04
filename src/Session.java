import java.util.UUID;

public class Session {

    private String token;
    private User user;

    public Session(User user) {
        this.token = UUID.randomUUID().toString();
        this.user = user;
    }

    protected String getToken() {
        return this.token;
    }

    protected User getUser() {
        return this.user;
    }
}
