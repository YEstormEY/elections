# WebSecurityConfig
    @Configuration
    public class WebSecurityConfig {

the **@Configuration** annotation is used to define a configuration class, the class is a source of bean definitions. This class will be used to configure the Spring Security settings.

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
the **@Bean** annotation is used to define a bean, a bean is an object that is instantiated, assembled, and otherwise managed by a Spring container. In this case the bean returns a new instance of BCryptPasswordEncoder. It will provide extra security to the passwords inserted into the database.

