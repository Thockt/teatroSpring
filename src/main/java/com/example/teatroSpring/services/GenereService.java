package com.example.teatroSpring.services;

import com.example.teatroSpring.entities.Comune;
import com.example.teatroSpring.entities.Genere;
import com.example.teatroSpring.exceptions.GenereNonEsisteException;
import com.example.teatroSpring.repositories.GenereRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class GenereService {

    @Autowired
    private GenereRepository genereRepository;

    public Genere getGenereById (Long id) throws GenereNonEsisteException {
        Optional<Genere> genereOptional = genereRepository.findById(id);
        if (genereOptional.isEmpty()) throw new GenereNonEsisteException();
        return genereOptional.get();
    }

    public List<Genere> getAll () { return genereRepository.findAll(); }

    public Genere createGenere (Genere genere) {
        genereRepository.saveAndFlush(genere);
        return genere;
    }
     public Genere updateGenere (Long id, Genere newGenere) {
         Optional<Genere> genereOptional = genereRepository.findById(id);
         if (genereOptional.isEmpty()) throw new IllegalArgumentException("Il genere con id "+id+" non esiste!");
         Genere genere = Genere.builder()
                 .id(id)
                 .nome(newGenere.getNome())
                 .build();
         genereRepository.saveAndFlush(genere);
         return genere;
     }

     public void deleteGenereById (Long id) { genereRepository.deleteById(id); }

}
