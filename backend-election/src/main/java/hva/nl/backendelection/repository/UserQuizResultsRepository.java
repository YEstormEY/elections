package hva.nl.backendelection.repository;

import hva.nl.backendelection.model.QuizScore;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserQuizResultsRepository extends JpaRepository<QuizScore, Long> {
}
