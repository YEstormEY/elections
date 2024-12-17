package hva.nl.backendelection.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class CorsConfig implements WebMvcConfigurer {

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**") // Apply to all endpoints
                .allowedOrigins("http://localhost:3000") // Specify frontend origin
                .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS") // Allowed HTTP methods
                .allowedHeaders("Authorization", "Content-Type", "X-Requested-With", "accept", "Origin",
                        "Access-Control-Request-Method", "Access-Control-Request-Headers") // Allowed headers
                .exposedHeaders("Access-Control-Allow-Origin", "Access-Control-Allow-Credentials") // Exposed headers
                .allowCredentials(true) // Allow cookies and credentials
                .maxAge(3600); // Cache pre-flight request results for 1 hour
    }
}
