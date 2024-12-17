package hva.nl.backendelection.repository;

import hva.nl.backendelection.model.Questions;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository

public interface QuizRepository extends JpaRepository<Questions, Long> {



//    // Find all results for a specific user
//    <QuizRepository> findByUserId(Long userId);
//
//    // Delete all previous results for a specific user
////    void deleteByUserId(Long userId);
//
////   void getQuestion();
}
