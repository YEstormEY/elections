package hva.nl.backendelection.service;

import hva.nl.backendelection.model.Party;
import hva.nl.backendelection.repository.PartyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class PartyService {

    @Autowired
    private PartyRepository partyRepository;

    public List<Party> getAllParties() {
        return (List<Party>) partyRepository.findAll();
    }

    public Party getPartyById(Long party_Id) {
        return partyRepository.findById(party_Id).orElse(null);
    }

}
