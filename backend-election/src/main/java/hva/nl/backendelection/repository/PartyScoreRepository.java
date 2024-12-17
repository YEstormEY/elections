package hva.nl.backendelection.repository;

import hva.nl.backendelection.model.Partyscore;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PartyScoreRepository extends JpaRepository<Partyscore, Long> {
}
