package hva.nl.backendelection.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MessageDTO {
        private Long messageId;
        private Long userId;
        private String username;
        private String content;
        private LocalDateTime createdAt;
        private String theme;
}
