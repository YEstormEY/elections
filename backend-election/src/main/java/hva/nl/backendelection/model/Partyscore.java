package hva.nl.backendelection.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table (name = "party_score")
public class Partyscore {
    @Id
    @GeneratedValue
    private long partyscore_id;

    @Column
    private String party_score;

    @Column
    private double score;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "question_id", nullable = false)
    private Questions questions; // Represents the foreign key to the "questions" table

    public double getScorePercentage() {
        return 0;
    }
}
