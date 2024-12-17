package hva.nl.backendelection.service;

import hva.nl.backendelection.model.*;
import hva.nl.backendelection.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;


@Service
public class PartyService {

    @Autowired
    private PartyRepository partyRepository;

    @Autowired
    private PartyBasicInfoRepository basicInfoRepository;

    @Autowired
    private PartyPoliciesRepository policiesRepository;

    @Autowired
    private PartyEngagementRepository engagementRepository;


    public List<Party> getAllParties() {
        return (List<Party>) partyRepository.findAll();
    }

    public Party getPartyById(Long party_Id) {
        return partyRepository.findById(party_Id).orElse(null);
    }

    public Party getImageUrl(Long party_Id) {
        return partyRepository.findById(party_Id).orElse(null);
    }

    public Optional<PartyBasicInfo> getBasicInfoByPartyId(Long partyId) {
        return basicInfoRepository.findByPartyPartyId(partyId);
    }

    public Optional<PartyPolicies> getPoliciesByPartyId(Long partyId) {
        return policiesRepository.findByPartyId(partyId);
    }

    public Optional<PartyEngagement> getEngagementByPartyId(Long partyId) {
        return engagementRepository.findByPartyPartyId(partyId);
    }



}
