package hva.nl.backendelection.repository;

import hva.nl.backendelection.model.ArticleCategories;
import hva.nl.backendelection.model.ArticleCategoryId;
import org.springframework.data.repository.CrudRepository;



public interface ArticleCategoryRepository extends CrudRepository<ArticleCategories, ArticleCategoryId> {

}
