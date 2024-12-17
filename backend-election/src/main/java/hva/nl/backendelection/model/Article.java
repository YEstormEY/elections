package hva.nl.backendelection.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

/**
 * The Article class is an entity model that represents an Article in the database.
 * It is used to interact with the database using the CRUDRepository.
 */
@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "articles")
public class Article {

    @Id
    @Column(name = "article_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long articleId;

    @Column
    private String title;

    @Column
    private String summary;

    @Column
    private String content;

    @Column
    private String image_path;

    @Column
    private Long author_id;

    @Column
    private Date created_at;

    @Column
    private Date updated_at;

    // Many-to-many relationship with Category
    @ManyToMany
    @JoinTable(
            name = "article_categories",
            joinColumns = @JoinColumn(name = "article_id"),
            inverseJoinColumns = @JoinColumn(name = "category_id")
    )

    private Set<Category> categories = new HashSet<>(); // Use Set instead of List
}
