package hva.nl.backendelection.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

    @Entity
    @Data
    @NoArgsConstructor
    public class Contact {
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Long id;

        private String name;
        private String email;
        private String message;
    }
