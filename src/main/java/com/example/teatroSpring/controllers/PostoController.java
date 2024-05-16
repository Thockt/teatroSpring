package com.example.teatroSpring.controllers;

import com.example.teatroSpring.entities.Posto;
import com.example.teatroSpring.requests.PostoRequest;
import com.example.teatroSpring.services.PostoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/posto")
public class PostoController {

    @Autowired
    private PostoService postoService;

    @GetMapping("/get/{id}")
    public ResponseEntity<?> getPostoById (@PathVariable Long id) {
        try {
            Posto myPosto = postoService.getPostoById(id);
            return new ResponseEntity<>(myPosto, HttpStatus.OK);
        }
        catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/all")
    public ResponseEntity<List<Posto>> getAllPosto () {
        return new ResponseEntity<>(postoService.getAll(), HttpStatus.OK);
    }

    @PostMapping("/create")
    @Secured("ROLE_ADMIN")
    public ResponseEntity<?> createPosto (@RequestBody PostoRequest postoRequest) {
        postoService.createPosto(postoRequest);
        return new ResponseEntity<>(postoRequest, HttpStatus.OK);
    }

    @PutMapping("/update/{id}")
    @Secured("ROLE_ADMIN")
    public ResponseEntity<?> updatePosto (@PathVariable Long id, @RequestBody Posto posto) {
        try {
            return new ResponseEntity<>(postoService.updatePosto(id, posto), HttpStatus.OK);
        }
        catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @DeleteMapping("/delete/{id}")
    @Secured("ROLE_ADMIN")
    public ResponseEntity<String> deletePostoById (@PathVariable Long id) {
        try {
            postoService.deletePostoById(id);
            return new ResponseEntity<>("Posto con id " +id+ " eliminato con successo", HttpStatus.OK);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }


}
