package hva.nl.backendelection.api;

import hva.nl.backendelection.model.Party;
import hva.nl.backendelection.model.PartyDetail;
import hva.nl.backendelection.service.PartyDetailService;
import hva.nl.backendelection.service.PartyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api")
public class PartyController {

    @Autowired
    private PartyService partyService;

    @Autowired
    private PartyDetailService partyDetailService;

    @GetMapping("/parties")
    public List<Party> getAllParties() {
        return partyService.getAllParties();
    }

    @GetMapping("/party/{partyId}")
    public Party getPartyById(@PathVariable Long partyId) {
        return partyService.getPartyById(partyId);
    }

    @GetMapping("/party-details/{partyId}")
    public ResponseEntity<PartyDetail> getPartyDetail(@PathVariable Long partyId) {
        Optional<PartyDetail> partyDetail = partyDetailService.getPartyDetailByPartyId(partyId);
        return partyDetail.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }
}
