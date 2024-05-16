package com.example.teatroSpring.responses;

import com.example.teatroSpring.entities.Sala;
import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PostoResponse {



    private Long id;
    private Integer fila;
    private Integer numero;
    private Long sala;
}
