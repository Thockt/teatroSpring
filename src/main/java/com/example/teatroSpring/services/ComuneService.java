package com.example.teatroSpring.services;

import com.example.teatroSpring.entities.Biglietto;
import com.example.teatroSpring.entities.Comune;
import com.example.teatroSpring.exceptions.ComuneNotFoundException;
import com.example.teatroSpring.repositories.ComuneRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ComuneService {

    @Autowired
    private ComuneRepository comuneRepository;

    public Comune getComuneById (Long id) throws ComuneNotFoundException {
        Optional<Comune> comuneOptional = comuneRepository.findById(id);
        if (comuneOptional.isEmpty()) throw new ComuneNotFoundException();
        return comuneOptional.get();
    }

    public List<Comune> getAll () { return comuneRepository.findAll(); }

    public Comune createComune (Comune comune) {
        comuneRepository.saveAndFlush(comune);
        return comune;
    }

    public Comune updateComune (Long id, Comune newComune) {
        Optional<Comune> comuneOptional = comuneRepository.findById(id);
        if (comuneOptional.isEmpty()) throw new IllegalArgumentException("Il comune con id "+id+" non esiste!");
        Comune comune = Comune.builder()
                .id(id)
                .nome(newComune.getNome())
                .regione(newComune.getRegione())
                .build();
        comuneRepository.saveAndFlush(comune);
        return comune;
    }

    public void deleteComuneById (Long id) { comuneRepository.deleteById(id);}

}
