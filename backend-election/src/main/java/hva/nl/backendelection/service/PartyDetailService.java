package hva.nl.backendelection.service;

import hva.nl.backendelection.model.Party;
import hva.nl.backendelection.model.PartyDetail;
import hva.nl.backendelection.repository.PartyDetailRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class PartyDetailService {

    @Autowired
    private PartyDetailRepository partyDetailRepository;

    @Autowired
    private PartyService partyService;  // Inject PartyService to fetch Party

    public Optional<PartyDetail> getPartyDetailByPartyId(Long partyId) {
        // Fetch the Party object by ID
        Party party = partyService.getPartyById(partyId);
        if (party == null) {
            return Optional.empty();  // Return empty if no Party is found
        }
        // Pass the Party object to the repository method
        return partyDetailRepository.findByParty(party);
    }
}



