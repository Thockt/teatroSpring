package com.example.teatroSpring.responses;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class LikeResponse {

    private Long utente;
    private Long ermesNews;
    private LocalDateTime insertTime;
    private LocalDateTime lastUpdate;

}
