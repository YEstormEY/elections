package hva.nl.backendelection.model.request;

import jakarta.persistence.Id;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class LoginRequest {
    @Id
    private Long user_id;

    private String username;
    private String password;
}
