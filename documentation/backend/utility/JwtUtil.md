# JwtUtil

## Description
The `JwtUtil` class provides utility methods for generating and validating JSON Web Tokens (JWT). It uses the `io.jsonwebtoken` library to handle JWT operations.

## Annotations
- `@Component`: Marks the class as a Spring component, allowing it to be automatically detected and registered by the Spring container. You can inject this component into other beans with using `@Autowired`.

## Methods
    public String extractUsername(String token) {
        return extractClaim(token, Claims::getSubject);
    }

Extracts the username from the JWT token.

- **@Param**
    - `token`: The JWT token from which to extract the username.

- **@Return**
    - `String`: The username extracted from the token.
    - 
`java
  public String extractRole(String token){
  return extractClaim(token, claims -> claims.get("role", String.class));
  }
`

Extracts the role from the JWT token.
- **@Param**
    - `token`: The JWT token from which to extract the role.

- **@Return**
- `String`: The role extracted from the token.


    private Boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

Checks if the token isn't expired.

- **@Param**
    - `token`: The JWT token to check for expiration.

- **@Return**
    - `Boolean`: `true` if the token is expired, `false` otherwise.


    public String generateToken(String username, Long userId, String role) {
        return createToken(username, userId, role);
    }

Generates a JWT token.

- **@Param**
    - `username`: The username to include in the token.
    - `userId`: The user ID to include in the token.
    - `role`: The role to include in the token.

- **@Return**
    - `username, userId, role`: The JWT token generated with the provided information.

