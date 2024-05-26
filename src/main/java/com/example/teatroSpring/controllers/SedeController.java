package com.example.teatroSpring.controllers;

import com.example.teatroSpring.entities.Sede;
import com.example.teatroSpring.exceptions.ComuneNotFoundException;
import com.example.teatroSpring.exceptions.SedeNotFoundException;
import com.example.teatroSpring.requests.SedeNuovaApertaRequest;
import com.example.teatroSpring.requests.SedeRequest;
import com.example.teatroSpring.services.SedeService;
import org.quartz.SchedulerException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/sede")
public class SedeController {

    @Autowired
    private SedeService sedeService;

    @GetMapping("/get/{id}")
    public ResponseEntity<?> getSedeById (@PathVariable Long id) throws SedeNotFoundException {
        try {
            Sede mySede = sedeService.getSedeById(id);
            return new ResponseEntity<>(mySede, HttpStatus.OK);
        }
        catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/all")
    public ResponseEntity<List<Sede>> getAllSede () {
        return new ResponseEntity<>(sedeService.getAll(), HttpStatus.OK);
    }

    @PostMapping("/create")
    @Secured("ADMIN")
    public ResponseEntity<?> createSede (@RequestBody SedeRequest sedeRequest) throws ComuneNotFoundException {
        sedeService.createSede(sedeRequest);
        return new ResponseEntity<>(sedeRequest, HttpStatus.OK);
    }

    @PostMapping("/newsSede")
    @Secured("ADMIN")
    public ResponseEntity<?> createNewsSede (@RequestBody SedeNuovaApertaRequest sedeRequest) throws SchedulerException, ComuneNotFoundException {
        sedeService.createSedeAndNews(sedeRequest);
        return new ResponseEntity<>(sedeRequest, HttpStatus.CREATED);
    }

    @PutMapping("/update/{id}")
    @Secured("ADMIN")
    public ResponseEntity<?> updateSede (@PathVariable Long id, @RequestBody Sede sede) {
        try {
            return new ResponseEntity<>(sedeService.updateSede(id, sede), HttpStatus.OK);
        }
        catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @DeleteMapping("/delete/{id}")
    @Secured("ADMIN")
    public ResponseEntity<String> deleteSedeById (@PathVariable Long id) {
        try {
            sedeService.deleteSedeById(id);
            return new ResponseEntity<>("Sede con id " +id+ " eliminata con successo", HttpStatus.OK);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

}
