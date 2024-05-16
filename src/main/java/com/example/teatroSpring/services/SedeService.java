package com.example.teatroSpring.services;

import com.example.teatroSpring.entities.Comune;
import com.example.teatroSpring.entities.Sala;
import com.example.teatroSpring.entities.Sede;
import com.example.teatroSpring.exceptions.ComuneNotFoundException;
import com.example.teatroSpring.exceptions.SedeNotFoundException;
import com.example.teatroSpring.repositories.SedeRepository;
import com.example.teatroSpring.requests.SedeRequest;
import com.example.teatroSpring.responses.SedeResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class SedeService {

    @Autowired
    private SedeRepository sedeRepository;
    @Autowired
    private ComuneService comuneService;

    public Sede getSedeById (Long id) throws SedeNotFoundException {
        Optional<Sede> sedeOptional = sedeRepository.findById(id);
        if (sedeOptional.isEmpty()) throw new SedeNotFoundException();
        return sedeOptional.get();
    }

    public List<Sede> getAll() { return sedeRepository.findAll(); }

    public SedeResponse createSede (SedeRequest sede) throws ComuneNotFoundException{
        sedeRepository.saveAndFlush(convertFromDTO(sede));
        return convertFromEntity(convertFromDTO(sede));
    }

    public SedeResponse updateSede (Long id, Sede newSede) {
        Optional<Sede> sedeOptional = sedeRepository.findById(id);
        if (sedeOptional.isEmpty()) throw new IllegalArgumentException("La sede con id "+id+" non esiste!");
        Sede sede = Sede.builder()
                .id(id)
                .nome(newSede.getNome())
                .indirizzo(newSede.getIndirizzo())
                .isOpen(newSede.getIsOpen())
                .comune(newSede.getComune())
                .build();
        sedeRepository.saveAndFlush(sede);
        return convertFromEntity(sede);
    }

    public void deleteSedeById (Long id) { sedeRepository.deleteById(id); }

    public Comune accediAlComune (Long id_comune) throws ComuneNotFoundException {
        return comuneService.getComuneById(id_comune);
    }

    private Sede convertFromDTO (SedeRequest sedeRequest) throws ComuneNotFoundException{
        return Sede.builder()
                .nome(sedeRequest.getNome())
                .indirizzo(sedeRequest.getIndirizzo())
                .isOpen(sedeRequest.getIsOpen())
                .comune(comuneService.getComuneById(sedeRequest.getComune()))
                .build();
    }

    private SedeResponse convertFromEntity (Sede sede) {
        return SedeResponse.builder()
                .nome(sede.getNome())
                .indirizzo(sede.getIndirizzo())
                .isOpen(sede.getIsOpen())
                .comune(sede.getComune().getId())
                .build();
    }

}
