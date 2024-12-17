package hva.nl.backendelection.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "party_policies")
public class PartyPolicies {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "party_id", nullable = false)
    private Long partyId;

    @Column(columnDefinition = "TEXT")
    private String socialIssues;

    @Column(columnDefinition = "TEXT")
    private String economicIssues;

    @Column(columnDefinition = "TEXT")
    private String climatePolicies;

    @Column(columnDefinition = "TEXT")
    private String socialPolicies;

    @Column(columnDefinition = "TEXT")
    private String housingPolicies;

    @Column(columnDefinition = "TEXT")
    private String economicPolicies;

    @Column(columnDefinition = "TEXT")
    private String freedomRights;

    private String linkSocialIssues;
    private String linkEconomicIssues;
    private String linkClimatePolicies;
    private String linkSocialPolicies;
    private String linkHousingPolicies;
    private String linkEconomicPolicies;
    private String linkFreedomRights;
}