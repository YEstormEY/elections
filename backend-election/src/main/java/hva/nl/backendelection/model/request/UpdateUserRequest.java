package hva.nl.backendelection.model.request;

import jakarta.persistence.Id;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class UpdateUserRequest {
    @Id
    private Long userId;
    private String username;
    private String email;
    private String role;
}
