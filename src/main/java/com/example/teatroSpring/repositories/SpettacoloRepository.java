package com.example.teatroSpring.repositories;

import com.example.teatroSpring.entities.Spettacolo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface SpettacoloRepository extends JpaRepository<Spettacolo, Long>, CrudRepository<Spettacolo, Long> ,JpaSpecificationExecutor<Spettacolo> {

    @Query(value = "SELECT * FROM spettacolo WHERE genere_id = :idGenere", nativeQuery = true)
    List<Spettacolo> getSpettacoliByGenere (@Param("idGenere") Long idGenere);

    @Query(value = "SELECT spettacolo.* FROM spettacolo " +
            "JOIN sala ON spettacolo.sala_id = sala.id " +
            "JOIN sede ON sala.sede_id = sede.id " +
            "JOIN comune ON sede.comune_id = comune.id " +
            "WHERE comune.id = :idComune", nativeQuery = true)
    List<Spettacolo> getSpettacoliByComune (@Param("idComune") Long idComune);

    @Query(value = "SELECT spettacolo.* FROM spettacolo " +
            "JOIN sala ON spettacolo.sala_id = sala.id " +
            "JOIN sede ON sala.sede_id = sede.id " +
            "WHERE sede.is_open = :isOpen", nativeQuery = true)
    List<Spettacolo> getSpettacoliByIsOpen (@Param("isOpen") Boolean isOpen);

    @Query(value = "SELECT * FROM spettacolo WHERE orario BETWEEN :data1 AND  :data2", nativeQuery = true)
    List<Spettacolo> getSpettacoliByIntervalloDate (@Param("data1") LocalDateTime data1, @Param("data2") LocalDateTime data2);

}
