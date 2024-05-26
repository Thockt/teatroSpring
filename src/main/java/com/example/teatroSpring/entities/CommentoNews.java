package com.example.teatroSpring.entities;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CommentoNews {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne(optional = false)
    private Utente utente;
    @ManyToOne(optional = false)
    private ErmesNews ermesNews;
    @Column(nullable = false)
    private String testo;
    @Column(nullable = false)
    private LocalDateTime insertTime;
    @Column
    private LocalDateTime lastUpdate;

}
