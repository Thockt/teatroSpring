package com.example.teatroSpring.responses;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SpettacoloResponse {

    private String nome;
    private Long genere;
    private LocalDateTime orario;
    private Integer durata;
    private Double prezzo;
    private Long sala;

}
