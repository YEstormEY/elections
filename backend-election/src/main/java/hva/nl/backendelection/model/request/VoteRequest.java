package hva.nl.backendelection.model.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class VoteRequest {
    private Long userId;
    private Long partyId;
}
