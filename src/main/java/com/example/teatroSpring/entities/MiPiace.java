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
public class MiPiace {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    private LocalDateTime insertTime;
    @ManyToOne
    private Utente utente;
    @ManyToOne
    private ErmesNews ermesNews;
    @Column
    private LocalDateTime lastUpdate;

}
