package hva.nl.backendelection.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "parties")
public class Party {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long party_id;

    @Column
    private String party_name;

    @Column
    private String party_description;

    @Column
    private String party_leader;

    @Column
    private String party_focus;

    @Column
    private String party_ideology;

    @Column
    private String party_logo;
}
