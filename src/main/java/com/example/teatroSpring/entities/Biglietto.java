package com.example.teatroSpring.entities;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Biglietto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    private Utente proprietario;
    @ManyToOne
    private Posto posto;
    @ManyToOne
    private Spettacolo spettacolo;
    @Column(nullable = false)
    private LocalDateTime timestamp;

}
