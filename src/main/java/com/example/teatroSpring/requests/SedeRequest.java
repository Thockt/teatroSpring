package com.example.teatroSpring.requests;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class SedeRequest {

    private String nome;
    private String indirizzo;
    private Boolean isOpen;
    private Long comune;

}
