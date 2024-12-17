package hva.nl.backendelection.filters;

import hva.nl.backendelection.utility.JwtUtil;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.List;

/**
 * This class is responsible for filtering the incoming requests and checking if the JWT token is valid.
 * The @component annotation is used to indicate that this class is a spring component.
 */
@Component
public class JwtRequestFilter extends OncePerRequestFilter {

    // Autowire the JwtUtil class
    @Autowired
    private JwtUtil jwtUtil;

    /**
     * This method is responsible for filtering the incoming request and checking if the JWT token is valid.
     * If the token is valid, the role is extracted and added to the security context.
     * If the token is expired or an error occurs, the appropriate message is printed.
     *
     * @param request the incoming request object.
     * @param response the response object sent back to the client.
     * @param filterChain the filter chain object to pass the request and response to the next filter.
     * @throws ServletException if an error occurs while processing the request.
     * @throws IOException if an I/O error occurs while processing the request.
     */
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        final String authorizationHeader = request.getHeader("Authorization");

        String username = null;
        String jwt = null;

        if (authorizationHeader != null) {
            jwt = authorizationHeader;
            try {
                username = jwtUtil.extractUsername(jwt);
                Claims claims = jwtUtil.extractAllClaims(jwt);
                String role = claims.get("role", String.class);

                // Add the role to the SecurityContext
                if (role != null && SecurityContextHolder.getContext().getAuthentication() == null) {
                    UsernamePasswordAuthenticationToken authenticationToken =
                            new UsernamePasswordAuthenticationToken(username, null, List.of(() -> "ROLE_" + role));
                    SecurityContextHolder.getContext().setAuthentication(authenticationToken);
                }
            } catch (ExpiredJwtException e) {
                System.out.println("JWT token has expired");
            } catch (Exception e) {
                System.out.println("Error parsing JWT token: " + e.getMessage());
            }
        }

        filterChain.doFilter(request, response);
    }
}
