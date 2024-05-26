package com.example.teatroSpring.requests;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SedeNuovaApertaRequest {

    private String nome;
    private String indirizzo;
    private Boolean isOpen;
    private Long comune;
    private Date targetDate;

}
