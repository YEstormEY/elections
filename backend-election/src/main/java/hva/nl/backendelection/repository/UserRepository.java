package hva.nl.backendelection.repository;

import hva.nl.backendelection.model.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * This interface extends the CrudRepository interface and is used to interact with the database.
 */
public interface UserRepository extends CrudRepository<User, Long> {
    /**
     * This method is used to find a user by its username.
     *
     * @param username The username of the user.
     * @return The user with the given username.
     */
    User findByUsername(String username);

    /**
     * This method is used to find a user by its email.
     *
     * @param email The email of the user.
     * @return The user with the given email.
     */
    User findByEmail(String email);

    /**
     * This method is used to find a user by its id.
     *
     * @param userId The id of the user.
     * @return The user with the given id.
     */
    User findByUserId(long userId);

    @Query("SELECT u FROM User u WHERE LOWER(u.email) LIKE LOWER(CONCAT('%', :search, '%'))")
    List<User> findByEmailContaining(@Param("search") String search);

    @Query("SELECT u FROM User u WHERE LOWER(u.username) LIKE LOWER(CONCAT('%', :search, '%'))")
    List<User> findByUsernameContaining(@Param("search") String search);

}
