package com.example.teatroSpring.repositories;

import com.example.teatroSpring.entities.Biglietto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BigliettoRepository extends JpaRepository<Biglietto, Long> {

    @Query(value = "SELECT proprietario_id FROM biglietto WHERE spettacolo_id = :id_spettacolo", nativeQuery = true)
    List<Long> bigliettiSpettacoloDaUtenti(@Param("id_spettacolo")Long id_spettacolo);

}
