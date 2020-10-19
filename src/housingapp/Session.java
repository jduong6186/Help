package housingapp;

import java.time.LocalDateTime;
import java.util.UUID;

public class Session {

    private final UUID token;
    private final UUID userId;
    private final LocalDateTime expiration;

    public Session(UUID userId) {
        this.token = UUID.randomUUID();
        this.userId = userId;
        this.expiration = LocalDateTime.now().plusHours(1);
    }

    public Session(UUID token, UUID userId, LocalDateTime expiration) {
        this.token = token;
        this.userId = userId;
        this.expiration = expiration;
    }

    public UUID getToken() {
        return this.token;
    }

    public UUID getUserId() {
        return this.userId;
    }

    public LocalDateTime getExpiration() {
        return this.expiration;
    }
}
