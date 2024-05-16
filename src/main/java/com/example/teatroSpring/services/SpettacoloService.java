package com.example.teatroSpring.services;

import com.example.teatroSpring.entities.Comune;
import com.example.teatroSpring.entities.Genere;
import com.example.teatroSpring.entities.Spettacolo;
import com.example.teatroSpring.exceptions.ComuneNotFoundException;
import com.example.teatroSpring.exceptions.GenereNonEsisteException;
import com.example.teatroSpring.exceptions.SpettacoloNotFoundException;
import com.example.teatroSpring.repositories.SpettacoloRepository;
import com.example.teatroSpring.requests.SpettacoloRequest;
import com.example.teatroSpring.responses.SpettacoloResponse;
import com.example.teatroSpring.specifications.SpettacoloSpecification;
import jakarta.persistence.TypedQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class SpettacoloService {

    @Autowired
    private SpettacoloRepository spettacoloRepository;
    @Autowired
    private GenereService genereService;
    @Autowired
    private SalaService salaService;
    @Autowired
    private ComuneService comuneService;

    private SpettacoloSpecification spettacoloSpecification = new SpettacoloSpecification();

    public Spettacolo getSpettacoloById (Long id) throws SpettacoloNotFoundException {
        Optional<Spettacolo> spettacoloOptional = spettacoloRepository.findById(id);
        if (spettacoloOptional.isEmpty()) throw new SpettacoloNotFoundException();
        return spettacoloOptional.get();
    }

    public List<Spettacolo> getAll () { return spettacoloRepository.findAll(); }

    public SpettacoloResponse createSpettacolo (SpettacoloRequest spettacolo) throws GenereNonEsisteException{
        spettacoloRepository.saveAndFlush(convertFromDTO(spettacolo));
        return convertFromEntity(convertFromDTO(spettacolo));
    }

    public SpettacoloResponse updateSpettacolo (Long id, Spettacolo newSpettacolo) {
        Optional<Spettacolo> spettacoloOptional = spettacoloRepository.findById(id);
        if (spettacoloOptional.isEmpty()) throw new IllegalArgumentException("Lo spettacolo con id "+id+" non esiste!");
        Spettacolo spettacolo = Spettacolo.builder()
                .id(id)
                .nome(newSpettacolo.getNome())
                .genere(newSpettacolo.getGenere())
                .orario(newSpettacolo.getOrario())
                .durata(newSpettacolo.getDurata())
                .prezzo(newSpettacolo.getPrezzo())
                .sala(newSpettacolo.getSala())
                .build();
        spettacoloRepository.saveAndFlush(spettacolo);
        return convertFromEntity(spettacolo);
    }

    public void deleteSpettacoloById (Long id) { spettacoloRepository.deleteById(id); }

    public List<Spettacolo> getSpettacoliByGenere (Long id_genere) throws GenereNonEsisteException {
        genereService.getGenereById(id_genere);
        return spettacoloRepository.getSpettacoliByGenere(id_genere);
    }

    public List<Spettacolo> getSpettacoliByComune (Long id_comune) throws ComuneNotFoundException {
        comuneService.getComuneById(id_comune);
        return spettacoloRepository.getSpettacoliByComune(id_comune);
    }

    public List<Spettacolo> getSpettacoliByIsOpen(Boolean isOpen) {
        return spettacoloRepository.getSpettacoliByIsOpen(isOpen);
    }

    public List<Spettacolo> getSpettacoliByIntervalloDate (LocalDateTime data1, LocalDateTime data2) {
        /*if (data1.isAfter(data2)) throw new IllegalArgumentException("La prima data non può essere maggiore della seconda!");*/
        return spettacoloRepository.getSpettacoliByIntervalloDate(data1, data2);
    }

    private Spettacolo convertFromDTO (SpettacoloRequest spettacoloRequest) throws GenereNonEsisteException{
        return Spettacolo.builder()
                .nome(spettacoloRequest.getNome())
                .genere(genereService.getGenereById(spettacoloRequest.getGenere()))
                .orario(spettacoloRequest.getOrario())
                .durata(spettacoloRequest.getDurata())
                .prezzo(spettacoloRequest.getPrezzo())
                .sala(salaService.getSalaById(spettacoloRequest.getSala()))
                .build();
    }

    private SpettacoloResponse convertFromEntity (Spettacolo spettacolo) {
        return SpettacoloResponse.builder()
                .nome(spettacolo.getNome())
                .genere(spettacolo.getGenere().getId())
                .orario(spettacolo.getOrario())
                .durata(spettacolo.getDurata())
                .prezzo(spettacolo.getPrezzo())
                .sala(spettacolo.getSala().getId())
                .build();
    }

}
