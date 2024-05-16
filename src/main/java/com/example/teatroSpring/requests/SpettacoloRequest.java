package com.example.teatroSpring.requests;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class SpettacoloRequest {

    private String nome;
    private Long genere;
    private LocalDateTime orario;
    private Integer durata;
    private Double prezzo;
    private Long sala;

}
