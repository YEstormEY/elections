package hva.nl.backendelection.model.request;


import jakarta.persistence.Id;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class UpdatePasswordRequest {
    @Id
    private Long userId;

    private String password;

    private String newPassword;
}
