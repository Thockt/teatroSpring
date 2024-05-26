package com.example.teatroSpring.services;

import com.example.teatroSpring.entities.CommentoNews;
import com.example.teatroSpring.exceptions.CommentoNotFoundException;
import com.example.teatroSpring.exceptions.SpamCommentiException;
import com.example.teatroSpring.repositories.CommentoNewsRepository;
import com.example.teatroSpring.responses.ErrorResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class CommentoNewsService {

    @Autowired
    private CommentoNewsRepository commentoNewsRepository;

    public CommentoNews getCommentoById (Long id) throws CommentoNotFoundException {
        Optional<CommentoNews> optionalCommentoNews = commentoNewsRepository.findById(id);
        return optionalCommentoNews.orElseThrow(()-> new CommentoNotFoundException());
    }

    public List<CommentoNews> getAllCommenti () {
        return commentoNewsRepository.findAll();
    }

    public Object createCommento (CommentoNews commentoNews) {
        LocalDateTime lastCommentoTime = commentoNewsRepository.getLastCommentoTime(commentoNews.getUtente().getId());
        Duration duration = Duration.between(lastCommentoTime, LocalDateTime.now());
        try {
            if (duration.getSeconds() >= 30) {
                commentoNews.setInsertTime(LocalDateTime.now());
                return commentoNewsRepository.saveAndFlush(commentoNews);
            }
            throw new SpamCommentiException();
        } catch (SpamCommentiException e) {
            return new ErrorResponse("SpamCommentiException", e.getMessage() + " " + (30 - duration.getSeconds()) + " secondi");
        }
    }

    public CommentoNews updateCommento (Long id, String testo) throws CommentoNotFoundException {
        CommentoNews commentoNews = getCommentoById(id);
        commentoNews.setTesto(testo);
        commentoNews.setLastUpdate(LocalDateTime.now());
        return commentoNewsRepository.saveAndFlush(commentoNews);
    }

    public void deleteCommentoById(Long id) throws CommentoNotFoundException {
        getCommentoById(id);
        commentoNewsRepository.deleteById(id);
    }

}
