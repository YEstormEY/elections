package hva.nl.backendelection.repository;

import hva.nl.backendelection.model.NewsLetter;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface NewsLetterRepository extends JpaRepository<NewsLetter, Long> {
}
