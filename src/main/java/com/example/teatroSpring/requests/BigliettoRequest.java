package com.example.teatroSpring.requests;

import com.example.teatroSpring.entities.Posto;
import com.example.teatroSpring.entities.Spettacolo;
import com.example.teatroSpring.entities.Utente;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class BigliettoRequest {

    private Long proprietario;
    private Long posto;
    private Long spettacolo;

}
