package hva.nl.backendelection.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "article_categories")
@Getter
@Setter
@NoArgsConstructor
public class ArticleCategories {

    @EmbeddedId
    private ArticleCategoryId id;

    @ManyToOne
    @MapsId("articleId")
    @JoinColumn(name = "article_id")
    private Article article;

    @ManyToOne
    @MapsId("categoryId")
    @JoinColumn(name = "category_id")
    private Category category;

}
