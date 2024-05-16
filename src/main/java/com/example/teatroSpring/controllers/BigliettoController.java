package com.example.teatroSpring.controllers;

import com.example.teatroSpring.entities.Biglietto;
import com.example.teatroSpring.enums.Role;
import com.example.teatroSpring.exceptions.SpettacoloNotFoundException;
import com.example.teatroSpring.exceptions.UtenteNotFoundException;
import com.example.teatroSpring.requests.BigliettoRequest;
import com.example.teatroSpring.responses.BigliettoResponse;
import com.example.teatroSpring.services.BigliettoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/biglietto")
public class BigliettoController {

    @Autowired
    private BigliettoService bigliettoService;

    @GetMapping("/get/{id}")
    @Secured("ROLE_USER")
    public ResponseEntity<?> getBigliettoById (@PathVariable Long id) {
        try{
            BigliettoResponse myBiglietto = bigliettoService.getBigliettoById(id);
            return new ResponseEntity<>(myBiglietto, HttpStatus.OK);
        }catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/all")
    @Secured("ROLE_ADMIN")
    public ResponseEntity<List<BigliettoResponse>> getAllBiglietto () {
        return new ResponseEntity<>(bigliettoService.getAll(), HttpStatus.OK);
    }

    @PostMapping("/create")
    public ResponseEntity<?> creaBiglietto (@RequestBody BigliettoRequest bigliettoRequest) throws UtenteNotFoundException, SpettacoloNotFoundException {
        bigliettoService.createBiglietto(bigliettoRequest);
        return new ResponseEntity<>(bigliettoRequest, HttpStatus.OK);
    }

    @PutMapping("/update/{id}")
    @Secured("ROLE_ADMIN")
    public ResponseEntity<?> updateBiglietto(@PathVariable Long id, @RequestBody Biglietto biglietto) {
        try {
            return new ResponseEntity<>(bigliettoService.updateBiglietto(id, biglietto), HttpStatus.OK);
        } catch (IllegalArgumentException e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @DeleteMapping("/delete/{id}")
    @Secured("ROLE_ADMIN")
    public ResponseEntity<String> deleteBigliettoById (@PathVariable Long id) {
        try {
            bigliettoService.deleteBigliettoById(id);
            return new ResponseEntity<>("Biglietto con id " +id+ " eliminato con successo", HttpStatus.OK);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

}
