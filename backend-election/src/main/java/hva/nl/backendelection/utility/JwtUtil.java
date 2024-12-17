package hva.nl.backendelection.utility;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.function.Function;
import java.util.Base64;

@Component
public class JwtUtil {

    private String SECRET_KEY = "super_geheime_code";  // Replace with a strong secret key

    // Base64 encoding of the secret key (Optional: ensures it's encoded properly)
    private String getEncodedSecretKey() {
        return Base64.getEncoder().encodeToString(SECRET_KEY.getBytes());
    }

    // Extract username from the token
    public String extractUsername(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    //Extract role from the token
    public String extractRole(String token){
        return extractClaim(token, claims -> claims.get("role", String.class));
    }

    // Extract user_id from the token
    public Long extractUserId(String token) {
        return extractClaim(token, claims -> claims.get("user_id", Long.class));  // Use "user_id" here
    }

    // Extract expiration date from the token
    public Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    // Extract a specific claim from the token
    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    // Extract all claims from the token
    public Claims extractAllClaims(String token) {
        return Jwts.parser()
                .setSigningKey(getEncodedSecretKey())
                .parseClaimsJws(token)
                .getBody();
    }

    // Check if the token is expired
    private Boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    // Generate a JWT token for the given username and user_id
    public String generateToken(String username, Long userId, String role) {
        return createToken(username, userId, role);
    }

    // Create a JWT token with username and user_id as claims
    private String createToken(String subject, Long userId, String role) {
        return Jwts.builder()
                .setSubject(subject)
                .claim("user_id", userId)
                .claim("role", role)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 10))  // 10 hours expiration
                .signWith(SignatureAlgorithm.HS256, getEncodedSecretKey())  // Use encoded secret key
                .compact();
    }


    // Validate the token: Check if username matches and token is not expired
    public Boolean validateToken(String token, String username) {
        final String extractedUsername = extractUsername(token);
        return (extractedUsername.equals(username) && !isTokenExpired(token));
    }
}
