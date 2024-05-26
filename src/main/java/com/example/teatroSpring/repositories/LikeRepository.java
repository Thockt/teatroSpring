package com.example.teatroSpring.repositories;

import com.example.teatroSpring.entities.ErmesNews;
import com.example.teatroSpring.entities.MiPiace;
import com.example.teatroSpring.entities.Utente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LikeRepository extends JpaRepository<MiPiace, Long> {

    @Query(value = "SELECT id FROM mi_piace WHERE utente_id = :idUtente AND ermes_news_id = :idErmesNews", nativeQuery = true)
    Long getIfLikeExist (@Param("idUtente") Long idUtente, @Param("idErmesNews") Long idErmesNews);

    @Query(value = "SELECT ermes_news_id FROM mi_piace WHERE ermes_news_id = :idErmesNews", nativeQuery = true)
    List<Long> getAllLikesRecived (@Param ("idErmesNews") Long idErmesNews);

    @Query(value = "SELECT utente_id FROM mi_piace WHERE ermes_news_id = :idErmesNews", nativeQuery = true)
    List<Long> getAllUtentiWhoLiked (@Param ("idErmesNews") Long idErmesNews);

    @Query(value = "SELECT utente_id FROM mi_piace WHERE utente_id = :idUtente", nativeQuery = true)
    List<Long> getAllUtenteLikes (@Param ("idUtente") Long idUtente);

    @Query(value = "SELECT ermes_news_id FROM mi_piace WHERE utente_id = :idUtente", nativeQuery = true)
    List<Long> getAllIdNewsLikedByUtente (@Param ("idUtente") Long idUtente);

}
