package hva.nl.backendelection.repository;

import hva.nl.backendelection.model.Party;
import hva.nl.backendelection.model.PartyDetail;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface PartyDetailRepository extends CrudRepository<PartyDetail, Long> {

    // Fix the query to reference the 'party' object directly
    Optional<PartyDetail> findByParty(Party party);
}


