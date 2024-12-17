# JwtRequestFilter

the `JwtRequestFilter` class is a Spring `OncePerRequestFilter` that intercepts all incoming HTTP requests and checks if the request contains a valid JWT token in the `Authorization` header.

## Annotations
- `@Component`: This annotation marks the class as a Spring component.
- `@Autowired`: This annotation injects the `JwtUtil` bean into the filter.
- `@Override`: This annotation indicates that the method is an override of a superclass method.

## Key features
- Extracts the JWT token from the `Authorization` header of the incoming request.
- Validates and extracts the username and role from the JWT token.
- Sets the user's authentication in the Spring Security context if the token is valid.
- Handles exceptions thrown during the JWT token validation process.

## Methods
- `doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)`
- This method intercepts the incoming HTTP request, extracts the JWT token from the `Authorization` header, validates the token, and sets the user's authentication in the Spring Security context if the token is valid.
- **Parameters**:
  - `HttpServletRequest request`: The incoming HTTP request.
  - `HttpServletResponse response`: The HTTP response.
  - `FilterChain filterChain`: The filter chain to process the request.
- **Key steps**:
  1. Retrieves the JWT token from the `Authorization` header.
  2. Uses the JWTutil to extract the username and parse the JWT claims to get the role.
  3. Creates an `Authentication` object with the username and role and sets it in the Spring Security context.
  4. Handles exceptions such as `ExpiredJwtException` and other JWT-related exceptions.
  5. passes the request and response to the next filter in the chain.

  