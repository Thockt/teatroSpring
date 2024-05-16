package com.example.teatroSpring.services;

import com.example.teatroSpring.entities.Recensione;
import com.example.teatroSpring.entities.Spettacolo;
import com.example.teatroSpring.exceptions.*;
import com.example.teatroSpring.repositories.RecensioneRepository;
import com.example.teatroSpring.requests.RecensioneRequest;
import com.example.teatroSpring.responses.RecensioneResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class RecensioneService {

    @Autowired
    RecensioneRepository recensioneRepository;
    @Autowired
    BigliettoService bigliettoService;
    @Autowired
    SpettacoloService spettacoloService;

    public RecensioneResponse getRecensioneResponseById (Long id) throws RecensioneNotFoundException {
        Optional<Recensione> recensioneOptional = recensioneRepository.findById(id);
        if(recensioneOptional.isEmpty()) throw new RecensioneNotFoundException();
        return convertFromEntity(recensioneOptional.get());
    }

    public List<RecensioneResponse> getAll () {
        return recensioneRepository.findAll().stream().map(this::convertFromEntity).toList();
    }

    public RecensioneResponse createRecensione (RecensioneRequest recensioneRequest) throws BigliettoNotFoundException, UtenteNotFoundException, SpettacoloNotFoundException, TrunksHaUsatoLaMacchinaDelTempoException {
        Spettacolo spettacolo = spettacoloService.getSpettacoloById(convertFromDTO(recensioneRequest).getBiglietto().getSpettacolo().getId());
        if(spettacolo.getOrario().isAfter(LocalDateTime.now())) throw new TrunksHaUsatoLaMacchinaDelTempoException();
        recensioneRepository.saveAndFlush(convertFromDTO(recensioneRequest));
        return convertFromEntity(convertFromDTO(recensioneRequest));
    }

    public void deleteById (Long id) throws RecensioneNotFoundException {
        getRecensioneResponseById(id);
        recensioneRepository.deleteById(id);
    }

    private Recensione convertFromDTO (RecensioneRequest recensioneRequest) throws BigliettoNotFoundException {
        return Recensione.builder()
                .voto(recensioneRequest.getVoto())
                .commento(recensioneRequest.getCommento())
                .timeStamp(LocalDateTime.now())
                .biglietto(bigliettoService.getBigliettoByIdStandard(recensioneRequest.getBiglietto()))
                .build();
    }

    private RecensioneResponse convertFromEntity (Recensione recensione)  {
        return RecensioneResponse.builder()
                .id(recensione.getId())
                .voto(recensione.getVoto())
                .commento(recensione.getCommento())
                .timeStamp(recensione.getTimeStamp())
                .biglietto(recensione.getBiglietto().getId())
                .build();
    }

}
