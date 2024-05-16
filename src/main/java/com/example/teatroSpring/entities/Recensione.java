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
public class Recensione {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    @Check(constraints = "voto > 0 and voto <= 5")
    private Double voto;
    @Column(nullable = false)
    private String commento;
    @Column(nullable = false)
    private LocalDateTime timeStamp;
    @OneToOne
    private Biglietto biglietto;


}
