package com.example.teatroSpring.controllers;

import com.example.teatroSpring.entities.Utente;
import com.example.teatroSpring.exceptions.*;
import com.example.teatroSpring.requests.BigliettoRequest;
import com.example.teatroSpring.requests.LikeRequest;
import com.example.teatroSpring.requests.RecensioneRequest;
import com.example.teatroSpring.responses.CompraBigliettoResponse;
import com.example.teatroSpring.responses.GenericResponse;
import com.example.teatroSpring.responses.LikesLasciatiDaUtenteResponse;
import com.example.teatroSpring.responses.RecensioneResponse;
import com.example.teatroSpring.services.BigliettoService;
import com.example.teatroSpring.services.CommentoNewsService;
import com.example.teatroSpring.services.LikeService;
import com.example.teatroSpring.services.UtenteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/utente")
public class UtenteController {

    @Autowired
    private UtenteService utenteService;
    @Autowired
    private BigliettoService bigliettoService;
    @Autowired
    private LikeService likeService;
    @Autowired
    private CommentoNewsService commentoNewsService;

    @GetMapping("/get/{id}")
    @Secured("USER")
    public ResponseEntity<?> getUtenteById (@PathVariable Long id) throws UtenteNotFoundException {
            Utente myUtente = utenteService.getUtenteById(id);
            return new ResponseEntity<>(myUtente, HttpStatus.OK);
    }

    @GetMapping("/all")
    @Secured("ADMIN")
    public ResponseEntity<List<Utente>> getAll() {
        return new ResponseEntity<>(utenteService.getAll(), HttpStatus.OK);
    }

    @PostMapping("/create")
    public ResponseEntity<?> createUtente (@RequestBody Utente utente) {
        if(!utenteService.isEmail(utente.getEmail())) {
            throw new IllegalArgumentException("Email inserita non valida");
        }
        utenteService.createUtente(utente);
        return new ResponseEntity<>(utente, HttpStatus.OK);
    }

    @PutMapping("/update/{id}")
    @Secured("USER")
    public ResponseEntity<?> updateUtente (@PathVariable Long id, @RequestBody Utente utente) {
        try {
            return new ResponseEntity<>(utenteService.updateUtente(id, utente), HttpStatus.OK);
        } catch (UtenteNotFoundException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @DeleteMapping("/delete/{id}")
    @Secured("ADMIN")
    public ResponseEntity<String> deleteById(@PathVariable Long id) {
        try {
            utenteService.deleteUtenteById(id);
            return new ResponseEntity<>("Utente con id " +id+ " eliminato con successo", HttpStatus.OK);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping("/compraBiglietto")
    @Secured("USER")
    public ResponseEntity<?> compraBigietto (@RequestBody BigliettoRequest request) throws UtenteNotFoundException, SpettacoloNotFoundException {
        return new ResponseEntity<>(bigliettoService.createBiglietto(request), HttpStatus.CREATED);
    }

   @PostMapping("/scriviRecensione")
   @Secured("USER")
    public ResponseEntity<?> scrviRecensione (@RequestBody RecensioneRequest recensioneRequest) throws BigliettoNotFoundException, UtenteNotFoundException, SpettacoloNotFoundException, TrunksHaUsatoLaMacchinaDelTempoException, BigliettoFasulloException {
            RecensioneResponse recensioneResponse = utenteService.scriviRecensione(recensioneRequest);
            return new ResponseEntity<>(recensioneResponse, HttpStatus.OK);
   }

   @PostMapping("/addLike")
   @Secured("USER")
   public ResponseEntity<?> lasciaMiPiace (@RequestBody LikeRequest request) throws ErmesNewsNotFoundException, UtenteNotFoundException, NewsAlreadyLikedException {
        if (!likeService.isLiked(request)) throw new NewsAlreadyLikedException();
        return new ResponseEntity<>(likeService.createLike(request), HttpStatus.CREATED);
   }

   @DeleteMapping("/unlike/{id}")
   @Secured("USER")
   public ResponseEntity<GenericResponse> rimuoviLike (@PathVariable Long id) throws LikeNotFoundException {
        likeService.deleteLikeById(id);
        return new ResponseEntity<>(new GenericResponse("Mi piace rimosso con successo"), HttpStatus.OK);
   }

   @GetMapping("miPiaceLasciati/{id}")
   public ResponseEntity<LikesLasciatiDaUtenteResponse> getUtenteLikes (@PathVariable Long id) {
        return new ResponseEntity<>(likeService.getQuantiLikeLasciati(id), HttpStatus.OK);
   }

   @PostMapping("/update/role")
   @Secured("SUPERADMIN")
    public ResponseEntity<String> updateRole (@RequestParam Long id, @RequestParam String new_role) throws UtenteNotFoundException {
      utenteService.updateRole(id, new_role);
      return new ResponseEntity<>("Ruolo aggiornato con sucesso", HttpStatus.CREATED);
   }






}
