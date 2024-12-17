package hva.nl.backendelection.repository;

import hva.nl.backendelection.model.ArticleLikes;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

public interface LikeRepository extends CrudRepository<ArticleLikes, Long> {

    ArticleLikes getByArticle_articleIdAndUser_userId(Long articleId, Long userId);

    Long countByArticle_articleId(Long articleId);

    @Query("SELECT COUNT(al) FROM ArticleLikes al WHERE al.article.articleId = :articleId AND al.user.userId = :userId")
    Long countByArticleIdAndUserId(Long articleId, Long userId);
}
