package hva.nl.backendelection.repository;

import hva.nl.backendelection.model.FeedbackItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FeedbackItemRepository extends JpaRepository<FeedbackItem, Long> {
}

