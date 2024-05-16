package com.example.teatroSpring.entities;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.Check;

import java.time.LocalDateTime;

@Entity
@Table
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Spettacolo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    private String nome;
    @ManyToOne
    private Genere genere;
    @Column(nullable = false)
    private LocalDateTime orario;
    @Column(nullable = false)
    @Check(constraints = "durata > 0")
    private Integer durata;
    @Column(nullable = false)
    @Check(constraints = "prezzo > 0")
    private Double prezzo;
    @ManyToOne
    private Sala sala;

}
