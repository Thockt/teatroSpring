package com.example.teatroSpring.requests;

import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RegistrationRequest {

    private String nome;
    private String cognome;
    private String indirizzo;
    private String email;
    private String telefono;
    private LocalDate dataNascita;
    private Long città;
    private String password;

}
