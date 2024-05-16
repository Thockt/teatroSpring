package com.example.teatroSpring.controllers;

import com.example.teatroSpring.entities.Recensione;
import com.example.teatroSpring.exceptions.RecensioneNotFoundException;
import com.example.teatroSpring.responses.RecensioneResponse;
import com.example.teatroSpring.services.RecensioneService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/recensione")
public class RecensioneController {

    @Autowired
    RecensioneService recensioneService;

    @GetMapping("/get/{id}")
    public ResponseEntity<?> getRecensioneById (@PathVariable Long id) throws RecensioneNotFoundException {
        try {
            RecensioneResponse myRecensione = recensioneService.getRecensioneResponseById(id);
            return new ResponseEntity<>(myRecensione, HttpStatus.OK);
        }
        catch (RecensioneNotFoundException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/all")
    public ResponseEntity<List<RecensioneResponse>> getAllRecensioni () {
        return new ResponseEntity<>(recensioneService.getAll(), HttpStatus.OK);
    }

    @DeleteMapping("/delete/{id}")
    @Secured("USER")
    public ResponseEntity<String> deleteRecensioneById (@PathVariable Long id) throws RecensioneNotFoundException{
        try {
            recensioneService.deleteById(id);
            return new ResponseEntity<>("Recensione con id " + id + " eliminato con successo", HttpStatus.OK);
        } catch (RecensioneNotFoundException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

}
