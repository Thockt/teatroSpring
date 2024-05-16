package com.example.teatroSpring.services;

import com.example.teatroSpring.entities.Comune;
import com.example.teatroSpring.entities.Posto;
import com.example.teatroSpring.entities.Sala;
import com.example.teatroSpring.exceptions.ComuneNotFoundException;
import com.example.teatroSpring.exceptions.SedeNotFoundException;
import com.example.teatroSpring.repositories.SalaRepository;
import com.example.teatroSpring.requests.SalaRequest;
import com.example.teatroSpring.responses.SalaResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class SalaService {

    @Autowired
    private SalaRepository salaRepository;
    @Autowired
    private SedeService sedeService;

    public Sala getSalaById (Long id) {
        Optional<Sala> salaOptional = salaRepository.findById(id);
        if (salaOptional.isEmpty()) throw new IllegalArgumentException("La sala con id "+id+" non esiste!");
        return salaOptional.get();
    }

    public List<Sala> getAll () { return salaRepository.findAll(); }

    public SalaResponse createSala (SalaRequest sala) throws SedeNotFoundException{
        salaRepository.saveAndFlush(convertFromDTO(sala));
        return convertFromEntity(convertFromDTO(sala));
    }

    public SalaResponse updateSala (Long id, Sala newSala) {
        Optional<Sala> salaOptional = salaRepository.findById(id);
        if (salaOptional.isEmpty()) throw new IllegalArgumentException("La sala con id "+id+" non esiste!");
        Sala sala = Sala.builder()
                .id(id)
                .nome(newSala.getNome())
                .n_posti(newSala.getN_posti())
                .sede(newSala.getSede())
                .build();
        salaRepository.saveAndFlush(sala);
        return convertFromEntity(sala);
    }

    public void deleteSalaById (Long id) { salaRepository.deleteById(id); }

    public Comune accediAlComune (Long id_comune)throws ComuneNotFoundException {
        return sedeService.accediAlComune(id_comune);
    }

    private Sala convertFromDTO (SalaRequest salaRequest) throws SedeNotFoundException {
        return Sala.builder()
                .nome(salaRequest.getNome())
                .n_posti(salaRequest.getN_posti())
                .sede(sedeService.getSedeById(salaRequest.getSede()))
                .build();
    }

    private SalaResponse convertFromEntity (Sala sala) {
        return SalaResponse.builder()
                .nome(sala.getNome())
                .n_posti(sala.getN_posti())
                .sede(sala.getSede().getId())
                .build();
    }

}
