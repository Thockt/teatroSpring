package com.example.teatroSpring.specifications;

import com.example.teatroSpring.entities.Comune;
import com.example.teatroSpring.entities.Genere;
import com.example.teatroSpring.entities.Sala;
import com.example.teatroSpring.entities.Spettacolo;
import jakarta.persistence.criteria.Join;
import org.springframework.data.jpa.domain.Specification;

public class SpettacoloSpecification {

    public Specification<Spettacolo> isGenere (Genere genere) {
        return (spettacolo, cq, cb) -> cb.equal(spettacolo.get("genere"), genere);
    }



}
