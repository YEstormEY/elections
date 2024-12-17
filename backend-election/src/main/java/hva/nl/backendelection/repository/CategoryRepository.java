package hva.nl.backendelection.repository;

import hva.nl.backendelection.model.Category;
import org.springframework.data.repository.CrudRepository;

/**
 * The CategoryRepository interface is used to interact with the database using the CRUDRepository.
 */
public interface CategoryRepository extends CrudRepository<Category, Long> {
    Category findByName(String name);
}
