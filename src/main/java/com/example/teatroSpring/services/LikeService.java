package com.example.teatroSpring.services;

import com.example.teatroSpring.entities.MiPiace;
import com.example.teatroSpring.entities.Utente;
import com.example.teatroSpring.exceptions.ErmesNewsNotFoundException;
import com.example.teatroSpring.exceptions.LikeNotFoundException;
import com.example.teatroSpring.exceptions.UtenteNotFoundException;
import com.example.teatroSpring.repositories.LikeRepository;
import com.example.teatroSpring.requests.LikeRequest;
import com.example.teatroSpring.responses.LikeResponse;
import com.example.teatroSpring.responses.LikesLasciatiDaUtenteResponse;
import com.example.teatroSpring.responses.LikesRicevutiSullaNewsResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class LikeService {

    @Autowired
    private LikeRepository likeRepository;
    @Autowired
    private UtenteService utenteService;
    @Autowired
    private ErmesNewsService ermesNewsService;

    public MiPiace getLikeById (Long id) throws LikeNotFoundException {
        Optional<MiPiace> likeOptional = likeRepository.findById(id);
        if (likeOptional.isEmpty()) throw new LikeNotFoundException();
        return likeOptional.get();
    }

    public List<MiPiace> getAllLike () {
        return likeRepository.findAll();
    }

    public LikeResponse createLike (LikeRequest request) throws ErmesNewsNotFoundException, UtenteNotFoundException {
        MiPiace miPiace = convertFromDTO(request);
        miPiace.setInsertTime(LocalDateTime.now());
        likeRepository.saveAndFlush(miPiace);
        return convertFromEntity(miPiace);
    }

    public void deleteLikeById (Long id) throws LikeNotFoundException {
        getLikeById(id);
        likeRepository.deleteById(id);
    }

    public boolean isLiked (LikeRequest request) {
        Optional<MiPiace> optional = likeRepository.findById(likeRepository.getIfLikeExist(request.getUtente(), request.getErmesNews()));
        return optional.isEmpty();
    }

    public LikesRicevutiSullaNewsResponse getQuantiLikeRicevuti (Long idNews) {
        List<Long> idsNews = likeRepository.getAllLikesRecived(idNews);
        List<Long> utentiId = likeRepository.getAllUtentiWhoLiked(idNews);
        return LikesRicevutiSullaNewsResponse
                .builder()
                .nLikes(idsNews.size())
                .utentiId(utentiId)
                .build();
    }

    public LikesLasciatiDaUtenteResponse getQuantiLikeLasciati (Long idUtente) {
        List<Long> utenti = likeRepository.getAllUtenteLikes(idUtente);
        List<Long> idsNews = likeRepository.getAllIdNewsLikedByUtente(idUtente);
        return LikesLasciatiDaUtenteResponse
                .builder()
                .nLikes(utenti.size())
                .idNews(idsNews)
                .build();
    }


    public MiPiace convertFromDTO (LikeRequest request) throws UtenteNotFoundException, ErmesNewsNotFoundException {
        return MiPiace.builder()
                .utente(utenteService.getUtenteById(request.getUtente()))
                .ermesNews(ermesNewsService.getErmesNewsById(request.getErmesNews()))
                .insertTime(LocalDateTime.now())
                .build();
    }

    public LikeResponse convertFromEntity (MiPiace miPiace) {
        return LikeResponse
                .builder()
                .utente(miPiace.getId())
                .ermesNews(miPiace.getErmesNews().getId())
                .insertTime(miPiace.getInsertTime())
                .lastUpdate(miPiace.getLastUpdate())
                .build();
    }

}
