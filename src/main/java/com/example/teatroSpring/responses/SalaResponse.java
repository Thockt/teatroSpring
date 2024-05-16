package com.example.teatroSpring.responses;

import com.example.teatroSpring.entities.Sede;
import jakarta.persistence.Column;
import jakarta.persistence.ManyToOne;
import lombok.*;
import org.hibernate.annotations.Check;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SalaResponse {

    private String nome;
    private Integer n_posti;
    private Long sede;

}
