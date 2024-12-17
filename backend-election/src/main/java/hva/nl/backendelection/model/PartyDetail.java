package hva.nl.backendelection.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "party_details")
public class PartyDetail {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long detail_id;  // Unique ID for party detail

    @OneToOne
    @JoinColumn(name = "party_id", referencedColumnName = "party_id")
    private Party party;  // Reference to the Party entity

    @Column(columnDefinition = "TEXT")
    private String party_slogan;

    @Column(columnDefinition = "TEXT")
    private String party_introduction;

    @Column(columnDefinition = "TEXT")
    private String core_beliefs;

    @Column(columnDefinition = "TEXT")
    private String goals_and_vision;

    @Column(columnDefinition = "TEXT")
    private String youth_policies;

    @Column(columnDefinition = "TEXT")
    private String climate_policies;

    @Column(columnDefinition = "TEXT")
    private String social_issues;

    @Column(columnDefinition = "TEXT")
    private String housing_policies;

    @Column(columnDefinition = "TEXT")
    private String freedom_rights;

    @Column(columnDefinition = "VARCHAR(255)")
    private String social_media_instagram;

    @Column(columnDefinition = "VARCHAR(255)")
    private String social_media_twitter;

    @Column(columnDefinition = "VARCHAR(255)")
    private String social_media_tiktok;

    @Column(columnDefinition = "TEXT")
    private String fun_facts;

    @Column(columnDefinition = "TEXT")
    private String engagement_opportunities;

    @Column(columnDefinition = "TEXT")
    private String leader_bio;

    @Column(columnDefinition = "TEXT")
    private String volunteering_info;
}
