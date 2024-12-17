package hva.nl.backendelection.utility.response;

public class AuthenticationResponse {
    private String token;
    private long expiryTime;
    private Long userId; // Ensure this matches the field in your User entity

    public AuthenticationResponse(String token, long expiryTime, Long userId) {
        this.token = token;
        this.expiryTime = expiryTime;
        this.userId = userId;
    }

    // Getters and setters for token, expiryTime, and userId
    public String getToken() {
        return token;
    }

    public long getExpiryTime() {
        return expiryTime;
    }

    public Long getUserId() {
        return userId;
    }
}

