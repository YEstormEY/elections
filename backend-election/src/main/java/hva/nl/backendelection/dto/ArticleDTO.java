package hva.nl.backendelection.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

/**
 * The ArticleDTO class is a Data Transfer Object (DTO) that is used to transfer Article data between the frontend and the backend.
 */

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ArticleDTO {
        private Long articleId;
        private String title;
        private String summary;
        private String content;
        private String imagePath;
        private Long authorId;
        private Date created_at;
        private List<CategoryDTO> categories;
}
