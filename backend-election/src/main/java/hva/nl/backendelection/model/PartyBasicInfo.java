package hva.nl.backendelection.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "party_basic_info")
public class PartyBasicInfo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "party_id", referencedColumnName = "party_id")
    private Party party;

    @Column(nullable = false)
    private String partyName; // New field for the party name

    private String slogan;
    @Column(columnDefinition = "TEXT")
    private String introduction;
    @Column(columnDefinition = "TEXT")
    private String funFacts;
    private String socialMediaInstagram;
    private String socialMediaTwitter;
    private String socialMediaTiktok;
    private String website;
}
