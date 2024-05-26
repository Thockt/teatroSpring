package com.example.teatroSpring.controllers;

import com.example.teatroSpring.entities.CommentoNews;
import com.example.teatroSpring.exceptions.CommentoNotFoundException;
import com.example.teatroSpring.responses.GenericResponse;
import com.example.teatroSpring.services.CommentoNewsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/comments")
public class CommentoNewsController {

    @Autowired
    private CommentoNewsService commentoNewsService;

    @GetMapping("/get/{id}")
    public ResponseEntity<CommentoNews> getCommentoNewsById (@PathVariable Long id) throws CommentoNotFoundException {
        return new ResponseEntity<>(commentoNewsService.getCommentoById(id), HttpStatus.OK);
    }

    @GetMapping("/all")
    public ResponseEntity<List<CommentoNews>> getAllCommentiNews () {
        return new ResponseEntity<>(commentoNewsService.getAllCommenti(), HttpStatus.OK);
    }

    @PostMapping("/create")
    public ResponseEntity<?> createCommentoNews (@RequestBody CommentoNews commentoNews) {
        return new ResponseEntity<>(commentoNewsService.createCommento(commentoNews), HttpStatus.CREATED);
    }

    @PostMapping("/update/{id}")
    public ResponseEntity<CommentoNews> updateCommento(@PathVariable Long id, @RequestParam String newTesto) throws CommentoNotFoundException {
        return new ResponseEntity<>(commentoNewsService.updateCommento(id, newTesto), HttpStatus.CREATED);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<GenericResponse> deleteCommentoNews(@PathVariable Long id) throws CommentoNotFoundException {
        commentoNewsService.deleteCommentoById(id);
        return new ResponseEntity<>(new GenericResponse("Commento con id " + id + " eliminato con successo"), HttpStatus.OK);
    }

}
