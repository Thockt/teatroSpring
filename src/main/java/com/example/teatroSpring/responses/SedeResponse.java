package com.example.teatroSpring.responses;

import com.example.teatroSpring.entities.Comune;
import jakarta.persistence.Column;
import jakarta.persistence.ManyToOne;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SedeResponse {

    private String nome;
    private String indirizzo;
    private Boolean isOpen;
    private Long comune;

}
