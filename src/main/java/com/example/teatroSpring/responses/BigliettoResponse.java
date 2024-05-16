package com.example.teatroSpring.responses;

import com.example.teatroSpring.entities.Posto;
import com.example.teatroSpring.entities.Spettacolo;
import com.example.teatroSpring.entities.Utente;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class BigliettoResponse {


    private Long id;
    private Utente proprietario;
    private Posto posto;
    private Spettacolo spettacolo;
    private LocalDateTime timestamp;
}
