package com.example.teatroSpring.responses;

import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class LikesLasciatiDaUtenteResponse {

    private Integer nLikes;
    private List<Long> idNews;

}
