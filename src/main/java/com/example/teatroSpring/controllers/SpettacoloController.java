package com.example.teatroSpring.controllers;

import com.example.teatroSpring.entities.Sede;
import com.example.teatroSpring.entities.Spettacolo;
import com.example.teatroSpring.exceptions.ComuneNotFoundException;
import com.example.teatroSpring.exceptions.GenereNonEsisteException;
import com.example.teatroSpring.exceptions.SpettacoloNotFoundException;
import com.example.teatroSpring.requests.SpettacoloRequest;
import com.example.teatroSpring.services.SpettacoloService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("spettacolo")
public class SpettacoloController {

    @Autowired
    private SpettacoloService spettacoloService;

    @GetMapping("/get/{id}")
    public ResponseEntity<?> getSpettacoloById (@PathVariable Long id) throws SpettacoloNotFoundException {
        try {
            Spettacolo mySpettacolo = spettacoloService.getSpettacoloById(id);
            return new ResponseEntity<>(mySpettacolo, HttpStatus.OK);
        }
        catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/all")
    public ResponseEntity<List<Spettacolo>> getAllSpettacolo () {
        return new ResponseEntity<>(spettacoloService.getAll(), HttpStatus.OK);
    }

    @PostMapping("/create")
    @Secured("ROLE_ADMIN")
    public ResponseEntity<?> createSpettacolo (@RequestBody SpettacoloRequest spettacoloRequest)throws GenereNonEsisteException {
        spettacoloService.createSpettacolo(spettacoloRequest);
        return new ResponseEntity<>(spettacoloRequest, HttpStatus.OK);
    }

    @PutMapping("/update/{id}")
    @Secured("ROLE_ADMIN")
    public ResponseEntity<?> updateSpettacolo (@PathVariable Long id, @RequestBody Spettacolo spettacolo) {
        try {
            return new ResponseEntity<>(spettacoloService.updateSpettacolo(id, spettacolo), HttpStatus.OK);
        }
        catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @DeleteMapping("/delete/{id}")
    @Secured("ROLE_ADMIN")
    public ResponseEntity<String> deleteSpettacoloById (@PathVariable Long id) {
        try {
            spettacoloService.deleteSpettacoloById(id);
            return new ResponseEntity<>("Spettacolo con id " +id+ " eliminato con successo", HttpStatus.OK);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/getByGenere/{id_genere}")
    public ResponseEntity<List<Spettacolo>> getSpettacoliByGenere (@PathVariable Long id_genere) throws GenereNonEsisteException {
        return new ResponseEntity<>(spettacoloService.getSpettacoliByGenere(id_genere), HttpStatus.OK);
    }

    @GetMapping("/getByComune/{id_comune}")
    public ResponseEntity<List<Spettacolo>> getSpettacoliByComune (@PathVariable Long id_comune) throws ComuneNotFoundException {
        return new ResponseEntity<>(spettacoloService.getSpettacoliByComune(id_comune), HttpStatus.OK);
    }

    @GetMapping("/getByIsOpen/{isOpen}")
    public ResponseEntity<List<Spettacolo>> getSpettacoliByIsOpen (@PathVariable Boolean isOpen) {
        return new ResponseEntity<>(spettacoloService.getSpettacoliByIsOpen(isOpen), HttpStatus.OK);
    }

    @GetMapping("/getByIntervalloDate/{data1}/{data2}")
    public ResponseEntity<List<Spettacolo>> getSpettacoliByIntervalloDate (@PathVariable LocalDateTime data1, @PathVariable LocalDateTime data2) {
        return new ResponseEntity<>(spettacoloService.getSpettacoliByIntervalloDate(data1, data2), HttpStatus.OK);
    }

}
