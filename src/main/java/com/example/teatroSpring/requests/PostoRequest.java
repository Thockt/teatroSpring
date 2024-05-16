package com.example.teatroSpring.requests;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PostoRequest {

    private Integer fila;
    private Integer numero;
    private Long sala;

}
