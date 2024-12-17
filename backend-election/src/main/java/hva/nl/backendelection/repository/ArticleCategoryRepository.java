package hva.nl.backendelection.repository;

import hva.nl.backendelection.model.ArticleCategories;
import hva.nl.backendelection.model.ArticleCategoryId;
import org.springframework.data.repository.CrudRepository;

import java.util.List;


public interface ArticleCategoryRepository extends CrudRepository<ArticleCategories, ArticleCategoryId> {

    List<ArticleCategories> findByArticle_ArticleId(Long articleId);
}
