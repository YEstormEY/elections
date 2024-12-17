package hva.nl.backendelection.model;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@Embeddable
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ArticleCategoryId implements Serializable {

    @Column(name = "article_id")
    private Long articleId;

    @Column(name = "category_id")
    private Long categoryId;
}
