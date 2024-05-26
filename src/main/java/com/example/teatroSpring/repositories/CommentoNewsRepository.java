package com.example.teatroSpring.repositories;

import com.example.teatroSpring.entities.CommentoNews;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;

@Repository
public interface CommentoNewsRepository extends JpaRepository<CommentoNews, Long> {

    @Query(value = "SELECT MAX(insert_time) FROM commenti_news WHERE utente_id =:id_utente", nativeQuery = true)
    LocalDateTime getLastCommentoTime(@Param("id_utente") Long id_utente);
}
