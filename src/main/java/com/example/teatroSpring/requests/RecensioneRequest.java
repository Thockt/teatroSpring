package com.example.teatroSpring.requests;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class RecensioneRequest {

    private Double voto;
    private String commento;
    private Long biglietto;

}
