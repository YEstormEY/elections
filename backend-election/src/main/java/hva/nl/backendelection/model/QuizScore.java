package hva.nl.backendelection.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;


@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "user_quiz_results") // Database table name
public class QuizScore {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long quizresults_id; // Unique identifier for the record

    @Column(nullable = false)
    private Long userId; // Link the result to a specific user

    @Column(nullable = false)
    private Long partyId;

    @Column(nullable = false)
    private double scorePercentage; // Percentage score

}
