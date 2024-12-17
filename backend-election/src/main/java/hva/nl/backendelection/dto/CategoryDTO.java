package hva.nl.backendelection.dto;

import hva.nl.backendelection.model.Category;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * The CategoryDTO class is a Data Transfer Object (DTO) that is used to transfer Category data between the frontend and the backend.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CategoryDTO {
    private Long categoryId;
    private String name;

}
