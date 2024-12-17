package hva.nl.backendelection.api;

import hva.nl.backendelection.model.*;
import hva.nl.backendelection.service.PartyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


@RestController
@RequestMapping("/api")
public class PartyController {

    @Autowired
    private PartyService partyService;

    @GetMapping("/parties")
    public List<Party> getAllParties() {
        return partyService.getAllParties();
    }

    @GetMapping("/party/{partyId}")
    public Party getPartyById(@PathVariable Long partyId) {
        return partyService.getPartyById(partyId);
    }

    @GetMapping("/party-image/{partyId}")
    public Party getImageUrl(@PathVariable Long partyId) {
        return partyService.getImageUrl(partyId);
    }

    @GetMapping("/party-basic-info/{partyId}")
    public ResponseEntity<PartyBasicInfo> getPartyBasicInfo(@PathVariable Long partyId) {
        return partyService.getBasicInfoByPartyId(partyId)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/party-policies/{partyId}")
    public ResponseEntity<PartyPolicies> getPartyPolicies(@PathVariable Long partyId) {
        return partyService.getPoliciesByPartyId(partyId)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }


    @GetMapping("/party-engagement/{partyId}")
    public ResponseEntity<PartyEngagement> getPartyEngagement(@PathVariable Long partyId) {
        return partyService.getEngagementByPartyId(partyId)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

}
