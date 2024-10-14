package com.example.ParcialMutantes.entities;

import jakarta.persistence.*;
import lombok.*;
import java.io.Serializable;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder

public class Adn implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    protected Long id;

    @Column(nullable = false, columnDefinition = "TEXT") // Cambiar a TEXT para permitir cadenas de adn muy largas
    private String adn;

    private boolean isMutant;
}