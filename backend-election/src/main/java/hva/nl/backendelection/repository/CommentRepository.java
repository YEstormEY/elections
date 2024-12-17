package hva.nl.backendelection.repository;

import hva.nl.backendelection.model.Comment;
import org.springframework.data.repository.CrudRepository;

public interface CommentRepository extends CrudRepository<Comment, Long> {

    Iterable<Comment> findByArticle_ArticleId(Long articleId);
}
