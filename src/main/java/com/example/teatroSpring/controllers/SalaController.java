package com.example.teatroSpring.controllers;

import com.example.teatroSpring.entities.Sala;
import com.example.teatroSpring.exceptions.SedeNotFoundException;
import com.example.teatroSpring.requests.SalaRequest;
import com.example.teatroSpring.services.SalaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/sala")
public class SalaController {

    @Autowired
    private SalaService salaService;

    @GetMapping("/get/{id}")
    public ResponseEntity<?> getSalaById (@PathVariable Long id) {
        try {
            Sala mySala = salaService.getSalaById(id);
            return new ResponseEntity<>(mySala, HttpStatus.OK);
        }
        catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/all")
    public ResponseEntity<List<Sala>> getAllSala () {
        return new ResponseEntity<>(salaService.getAll(), HttpStatus.OK);
    }

    @PostMapping("/create")
    @Secured("ROLE_ADMIN")
    public ResponseEntity<?> createSala (@RequestBody SalaRequest salaRequest) throws SedeNotFoundException {
        salaService.createSala(salaRequest);
        return new ResponseEntity<>(salaRequest, HttpStatus.OK);
    }

    @PutMapping("/update/{id}")
    @Secured("ROLE_ADMIN")
    public ResponseEntity<?> updateSala (@PathVariable Long id, @RequestBody Sala sala) {
        try {
            return new ResponseEntity<>(salaService.updateSala(id, sala), HttpStatus.OK);
        }
        catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @DeleteMapping("/delete/{id}")
    @Secured("ROLE_ADMIN")
    public ResponseEntity<String> deleteSalaById (@PathVariable Long id) {
        try {
            salaService.deleteSalaById(id);
            return new ResponseEntity<>("Sala con id " +id+ " eliminata con successo", HttpStatus.OK);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

}
