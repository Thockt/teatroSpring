package com.example.teatroSpring.responses;

import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class LikesRicevutiSullaNewsResponse {

    private Integer nLikes;
    private List<Long> utentiId;

}
