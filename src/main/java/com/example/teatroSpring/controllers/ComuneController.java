package com.example.teatroSpring.controllers;

import com.example.teatroSpring.entities.Comune;
import com.example.teatroSpring.exceptions.ComuneNotFoundException;
import com.example.teatroSpring.services.ComuneService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/comune")
public class ComuneController {

    @Autowired
    private ComuneService comuneService;

    @GetMapping("/get/{id}")
    public ResponseEntity<?> getComuneById (@PathVariable Long id) throws ComuneNotFoundException {
            Comune myComune = comuneService.getComuneById(id);
            return new ResponseEntity<>(myComune, HttpStatus.OK);
    }

    @GetMapping("/all")
    public ResponseEntity<List<Comune>> getAllComune () {
        return new ResponseEntity<>(comuneService.getAll(), HttpStatus.OK);
    }

    @PostMapping("/create")
    @Secured("ROLE_ADMIN")
    public ResponseEntity<?> createComune (@RequestBody Comune comune) {
        comuneService.createComune(comune);
        return new ResponseEntity<>(comune, HttpStatus.OK);
    }

    @PutMapping("/update/{id}")
    @Secured("ROLE_ADMIN")
    public ResponseEntity<?> updateComune (@PathVariable Long id, @RequestBody Comune comune) {
        try {
            return new ResponseEntity<>(comuneService.updateComune(id, comune), HttpStatus.OK);
        }
        catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @DeleteMapping("/delete/{id}")
    @Secured("ROLE_ADMIN")
    public ResponseEntity<String> deleteComuneById (@PathVariable Long id) {
        try {
            comuneService.deleteComuneById(id);
            return new ResponseEntity<>("Comune con id " +id+ " eliminato con successo", HttpStatus.OK);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }



}
