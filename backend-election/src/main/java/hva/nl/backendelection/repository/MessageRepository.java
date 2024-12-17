package hva.nl.backendelection.repository;

import hva.nl.backendelection.model.Message;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MessageRepository extends JpaRepository<Message, Long> {
    Iterable<Message> findByTheme(String theme);
}
