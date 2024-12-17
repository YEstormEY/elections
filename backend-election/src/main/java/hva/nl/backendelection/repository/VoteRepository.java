package hva.nl.backendelection.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import hva.nl.backendelection.model.Vote; // Assuming you have a Vote entity

import java.util.Optional;

@Repository
public interface VoteRepository extends JpaRepository<Vote, Long> {

    // Find a vote by the userId field
    Optional<Vote> findByUserId(Long userId);
}
