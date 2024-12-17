package hva.nl.backendelection.repository;

import hva.nl.backendelection.model.Article;
import org.springframework.data.repository.CrudRepository;


/**
 * The ArticleRepository interface is used to interact with the database using the CRUDRepository.
 */
public interface ArticleRepository extends CrudRepository<Article, Long> {

    Article findByarticleId(long articleId);

    Iterable<Article> findByCategoriesName(String categoryName);

}
