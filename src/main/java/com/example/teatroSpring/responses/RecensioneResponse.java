package com.example.teatroSpring.responses;

import com.example.teatroSpring.entities.Sede;
import com.example.teatroSpring.entities.Spettacolo;
import com.example.teatroSpring.entities.Utente;
import jakarta.persistence.Column;
import jakarta.persistence.OneToMany;
import lombok.*;
import org.hibernate.annotations.Check;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RecensioneResponse {

    private Long id;
    private Double voto;
    private String commento;
    private LocalDateTime timeStamp;
    private Long biglietto;

}
