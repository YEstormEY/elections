package hva.nl.backendelection.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "article_likes")
public class ArticleLikes {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "like_id")
    private Long likeId;

    @ManyToOne
    @JoinColumn(name = "article_id", nullable = false)
    private Article article; // The article this like is associated with

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user; // The user who liked the article

}
