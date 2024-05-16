package com.example.teatroSpring.services;

import com.example.teatroSpring.entities.Biglietto;
import com.example.teatroSpring.entities.Posto;
import com.example.teatroSpring.entities.Utente;
import com.example.teatroSpring.exceptions.SpettacoloNotFoundException;
import com.example.teatroSpring.exceptions.UtenteNotFoundException;
import com.example.teatroSpring.repositories.BigliettoRepository;
import com.example.teatroSpring.repositories.UtenteRepository;
import com.example.teatroSpring.requests.BigliettoRequest;
import com.example.teatroSpring.responses.BigliettoResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class BigliettoService {

    @Autowired
    private BigliettoRepository bigliettoRepository;
    @Autowired
    private UtenteRepository utenteRepository;
    @Autowired
    private PostoService postoService;
    @Autowired
    private SpettacoloService spettacoloService;

    public BigliettoResponse getBigliettoById (Long id){
        Optional<Biglietto> bigliettoOptional = bigliettoRepository.findById(id);
        if (bigliettoOptional.isEmpty()) throw new IllegalArgumentException("Il biglietto con id "+id+" non esiste!");
        return convertFromEntity(bigliettoOptional.get());
    }

    public Biglietto getBigliettoByIdStandard (Long id){
        Optional<Biglietto> bigliettoOptional = bigliettoRepository.findById(id);
        if (bigliettoOptional.isEmpty()) throw new IllegalArgumentException("Il biglietto con id "+id+" non esiste!");
        return bigliettoOptional.get();
    }

    public List<BigliettoResponse> getAll() {
        return bigliettoRepository.findAll().stream().map(this::convertFromEntity).toList();
    }

    public BigliettoResponse createBiglietto (BigliettoRequest biglietto) throws UtenteNotFoundException, SpettacoloNotFoundException {
        utenteRepository.findById(biglietto.getProprietario());
        spettacoloService.getSpettacoloById(biglietto.getSpettacolo());
        bigliettoRepository.saveAndFlush(convertFromDTO(biglietto));
        return convertFromEntity(convertFromDTO(biglietto));
    }



    public BigliettoResponse updateBiglietto (Long id, Biglietto newBiglietto) {
        Optional<Biglietto> bigliettoOptional = bigliettoRepository.findById(id);
        if (bigliettoOptional.isEmpty()) throw new IllegalArgumentException("Il biglietto con id "+id+" non esiste!");
        Biglietto biglietto = Biglietto.builder()
                .id(id)
                .proprietario(newBiglietto.getProprietario())
                .posto(newBiglietto.getPosto())
                .spettacolo(newBiglietto.getSpettacolo())
                .timestamp(bigliettoOptional.get().getTimestamp())
                .build();
        bigliettoRepository.saveAndFlush(biglietto);
        return convertFromEntity(biglietto);
    }

    public void deleteBigliettoById (Long id) {
        bigliettoRepository.deleteById(id);
    }

    public Boolean isRealBiglietto (Biglietto biglietto) throws UtenteNotFoundException, SpettacoloNotFoundException{
        Long u = biglietto.getProprietario().getId();
        Long s = biglietto.getSpettacolo().getId();
        Long p = biglietto.getPosto().getId();
        if(utenteRepository.findById(u).isEmpty() || spettacoloService.getSpettacoloById(s)== null || postoService.getPostoById(p) == null )
            return false;
        return true;
    }

    private Biglietto convertFromDTO (BigliettoRequest bigliettoRequest) throws UtenteNotFoundException, SpettacoloNotFoundException {
        return  Biglietto.builder()
                .proprietario(utenteRepository.findById(bigliettoRequest.getProprietario()).get())
                .posto(postoService.getPostoById(bigliettoRequest.getPosto()))
                .spettacolo(spettacoloService.getSpettacoloById(bigliettoRequest.getSpettacolo()))
                .timestamp(LocalDateTime.now())
                .build();

    }

    private BigliettoResponse convertFromEntity (Biglietto biglietto) {
        return BigliettoResponse.builder()
                .id(biglietto.getId())
                .proprietario(biglietto.getProprietario())
                .posto(biglietto.getPosto())
                .spettacolo(biglietto.getSpettacolo())
                .timestamp(biglietto.getTimestamp())
                .build();
    }





}
