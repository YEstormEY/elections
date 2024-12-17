package hva.nl.backendelection.repository;

import hva.nl.backendelection.model.PartyBasicInfo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PartyBasicInfoRepository extends JpaRepository<PartyBasicInfo, Long> {
    Optional<PartyBasicInfo> findByPartyPartyId(Long partyId);
}

