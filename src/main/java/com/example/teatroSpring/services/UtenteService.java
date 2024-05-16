package com.example.teatroSpring.services;

import com.example.teatroSpring.entities.Biglietto;
import com.example.teatroSpring.entities.Utente;
import com.example.teatroSpring.enums.Role;
import com.example.teatroSpring.exceptions.*;
import com.example.teatroSpring.repositories.UtenteRepository;
import com.example.teatroSpring.requests.RecensioneRequest;
import com.example.teatroSpring.responses.RecensioneResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UtenteService {

    @Autowired
    private UtenteRepository utenteRepository;
    @Autowired
    private RecensioneService recensioneService;
    @Autowired
    private BigliettoService bigliettoService;

    public Utente getUtenteById (Long id) throws UtenteNotFoundException {
        Optional<Utente> utenteOptional = utenteRepository.findById(id);
        if (utenteOptional.isEmpty()) throw new UtenteNotFoundException();
        return utenteOptional.get();
    }

    public List<Utente> getAll() { return utenteRepository.findAll(); }

    public Utente createUtente (Utente utente) {
        utenteRepository.saveAndFlush(utente);
        return utente;
    }

    public Utente updateUtente (Long id, Utente newUtente) throws UtenteNotFoundException {
        Optional<Utente> utenteOptional = utenteRepository.findById(id);
        if (utenteOptional.isEmpty()) throw new UtenteNotFoundException();
        Utente utente = Utente.builder()
                .id(id)
                .nome(newUtente.getNome())
                .cognome(newUtente.getCognome())
                .indirizzo(newUtente.getIndirizzo())
                .email(newUtente.getEmail())
                .telefono(newUtente.getTelefono())
                .dataNascita(newUtente.getDataNascita())
                .build();
        utenteRepository.saveAndFlush(utente);
        return utente;
    }

    public void deleteUtenteById (Long id) { utenteRepository.deleteById(id); }

    public boolean isEmail (String email) {
        String[] firstSplit = email.split("@");
        if(firstSplit.length != 2)
            return false;
        String[] secondSplit = firstSplit[1].split("\\.");
        if(secondSplit.length != 2)
            return false;
        if(secondSplit[1].equals("it") || secondSplit[1].equals("com"))
            return true;
        return false;
    }

    public RecensioneResponse scriviRecensione (RecensioneRequest recensioneRequest) throws BigliettoNotFoundException, UtenteNotFoundException, SpettacoloNotFoundException, TrunksHaUsatoLaMacchinaDelTempoException, BigliettoFasulloException {
        if (!bigliettoService.isRealBiglietto(bigliettoService.getBigliettoByIdStandard(recensioneRequest.getBiglietto())))
            throw new BigliettoFasulloException();
        return recensioneService.createRecensione(recensioneRequest);
    }

    public void updateRole (Long id, String new_role) {
        Utente utente = utenteRepository.getReferenceById(id);
        utente.setRole(Role.valueOf(new_role));
        utenteRepository.saveAndFlush(utente);
    }


}
