package hva.nl.backendelection.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "party_engagement")
public class PartyEngagement {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "party_id", referencedColumnName = "party_id")
    private Party party;

    @Column(columnDefinition = "TEXT")
    private String leaderBio;

    @Column(columnDefinition = "TEXT")
    private String volunteeringInfo;

    @Column(columnDefinition = "TEXT")
    private String engagementOpportunities;
}
