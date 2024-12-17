package hva.nl.backendelection.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CommentDTO {
    private Long commentId;
    private String content;
    private Long articleId;
    private Long userId;
    private String username;
    private LocalDateTime created_at;
}
