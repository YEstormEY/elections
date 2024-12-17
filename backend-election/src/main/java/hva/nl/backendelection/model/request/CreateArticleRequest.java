package hva.nl.backendelection.model.request;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;


@Getter
@Setter
@NoArgsConstructor
public class CreateArticleRequest {
    private String title;
    private String summary;
    private String content;
    private String imagePath;
    private List<Long> categoryId;  // Ensure this field matches frontend
}
