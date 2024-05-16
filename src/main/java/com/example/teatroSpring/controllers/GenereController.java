package com.example.teatroSpring.controllers;

import com.example.teatroSpring.entities.Genere;
import com.example.teatroSpring.exceptions.GenereNonEsisteException;
import com.example.teatroSpring.services.GenereService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/genere")
public class GenereController {

    @Autowired
    private GenereService genereService;

    @GetMapping("/get/{id}")
    public ResponseEntity<?> getGenereById (@PathVariable Long id) throws GenereNonEsisteException {
        try{
            Genere myGenere = genereService.getGenereById(id);
            return new ResponseEntity<>(myGenere, HttpStatus.OK);
        }catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/all")
    public ResponseEntity<List<Genere>> getAllGenere () {
        return new ResponseEntity<>(genereService.getAll(), HttpStatus.OK);
    }

    @PostMapping("/create")
    @Secured("ROLE_ADMIN")
    public ResponseEntity<?> createGenere (@RequestBody Genere genere) {
        genereService.createGenere(genere);
        return new ResponseEntity<>(genere, HttpStatus.OK);
    }

    @PutMapping("/update/{id}")
    @Secured("ROLE_ADMIN")
    public ResponseEntity<?> updateGenere (@PathVariable Long id, @RequestBody Genere genere) {
        try {
            return new ResponseEntity<>(genereService.updateGenere(id, genere), HttpStatus.OK);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @DeleteMapping("/delete/{id}")
    @Secured("ROLE_ADMIN")
    public ResponseEntity<String> deleteGenereById(@PathVariable Long id) {
        try {
            genereService.deleteGenereById(id);
            return new ResponseEntity<>("Genere con id " +id+ " eliminato con successo", HttpStatus.OK);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
