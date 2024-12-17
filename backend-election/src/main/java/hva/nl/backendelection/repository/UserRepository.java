package hva.nl.backendelection.repository;

import hva.nl.backendelection.model.User;
import org.springframework.data.repository.CrudRepository;

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

}
