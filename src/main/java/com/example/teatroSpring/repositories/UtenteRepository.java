package com.example.teatroSpring.repositories;

import com.example.teatroSpring.entities.Utente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UtenteRepository extends JpaRepository<Utente, Long> {

    @Query(value = "SELECT * FROM utente u WHERE u.email = :email", nativeQuery = true)
    Utente findUtenteByEmail (@Param("email") String email);

    @Query(value = "SELECT * FROM utente u WHERE città_id = :id_città", nativeQuery = true)
    List<Utente> getAllByCittà (@Param("id_città") Long id_città);

}
