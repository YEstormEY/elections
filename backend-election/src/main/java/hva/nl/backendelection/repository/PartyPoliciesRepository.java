package hva.nl.backendelection.repository;

import hva.nl.backendelection.model.PartyPolicies;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PartyPoliciesRepository extends JpaRepository<PartyPolicies, Long> {
    Optional<PartyPolicies> findByPartyId(Long partyId);

}

