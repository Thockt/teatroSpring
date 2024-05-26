package com.example.teatroSpring.controllers;

import com.example.teatroSpring.entities.MiPiace;
import com.example.teatroSpring.exceptions.ErmesNewsNotFoundException;
import com.example.teatroSpring.exceptions.LikeNotFoundException;
import com.example.teatroSpring.exceptions.UtenteNotFoundException;
import com.example.teatroSpring.requests.LikeRequest;
import com.example.teatroSpring.responses.GenericResponse;
import com.example.teatroSpring.responses.LikeResponse;
import com.example.teatroSpring.services.LikeService;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/likes")
public class LikeController {

    @Autowired
    private LikeService likeService;

    @GetMapping("/get/{id}")
    public ResponseEntity<MiPiace> getLikeById (@PathVariable Long id) throws LikeNotFoundException {
        return new ResponseEntity<>(likeService.getLikeById(id), HttpStatus.OK);
    }

    @GetMapping("/all")
    public ResponseEntity<List<MiPiace>> getAllLikes () {
        return new ResponseEntity<>(likeService.getAllLike(), HttpStatus.OK);
    }

    @PostMapping("/create")
    @Secured("USER")
    public ResponseEntity<LikeResponse> createLike (@RequestBody LikeRequest request) throws ErmesNewsNotFoundException, UtenteNotFoundException {
        return new ResponseEntity<>(likeService.createLike(request), HttpStatus.CREATED);
    }

    @DeleteMapping("/delete/{id}")
    @Secured("USER")
    public ResponseEntity<GenericResponse> deleteLike (@PathVariable Long id) throws LikeNotFoundException {
        likeService.deleteLikeById(id);
        return new ResponseEntity<>(new GenericResponse("Like con id " + id + " rimossa con successo"), HttpStatus.OK);
    }

}
