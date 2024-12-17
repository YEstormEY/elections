package hva.nl.backendelection.model.request;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;


@Getter
@Setter
@NoArgsConstructor
public class ArticleCategorieRequest {
    private Long articleId;
    private List<Long> categories;
}
