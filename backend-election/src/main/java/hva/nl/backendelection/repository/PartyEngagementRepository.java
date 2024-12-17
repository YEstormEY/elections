package hva.nl.backendelection.repository;

import hva.nl.backendelection.model.PartyEngagement;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PartyEngagementRepository extends JpaRepository<PartyEngagement, Long> {
    Optional<PartyEngagement> findByPartyPartyId(Long partyId);
}

