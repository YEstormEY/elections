package hva.nl.backendelection.model.request;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class UpdateArticleRequest {
    private Long articleId;
    private String title;
    private String summary;
    private String content;
    private String imagePath;
    private int[] categoryId;
}
