package hva.nl.backendelection.model.request;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class PostCommentRequest {
    private String content;
    private Long articleId;
}
