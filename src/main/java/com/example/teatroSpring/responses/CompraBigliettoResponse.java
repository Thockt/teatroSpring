package com.example.teatroSpring.responses;

import com.example.teatroSpring.entities.Posto;
import com.example.teatroSpring.entities.Spettacolo;
import com.example.teatroSpring.entities.Utente;
import lombok.*;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CompraBigliettoResponse {

    private Long id;
    private Long propietario;
    private Long posto;
    private Long spettacolo;
    private LocalDateTime timestamp;

}
