package com.example.teatroSpring.responses;

import com.example.teatroSpring.entities.Posto;
import com.example.teatroSpring.entities.Spettacolo;
import com.example.teatroSpring.entities.Utente;
import lombok.*;

import java.sql.Timestamp;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CompraBigliettoResponse {

    private Long id;
    private Utente propietario;
    private Posto posto;
    private Spettacolo spettacolo;
    private Timestamp timestamp;

}
