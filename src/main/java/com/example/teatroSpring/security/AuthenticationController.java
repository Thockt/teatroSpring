package com.example.teatroSpring.security;

import com.example.teatroSpring.exceptions.UserNotConfirmedException;
import com.example.teatroSpring.requests.AuthenticationRequest;
import com.example.teatroSpring.requests.RegistrationRequest;
import com.example.teatroSpring.responses.AuthenticationResponse;
import com.example.teatroSpring.responses.ErrorResponse;
import com.example.teatroSpring.responses.GenericResponse;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthenticationController {

    @Autowired
    private AuthenticationService authenticationService;

    @PostMapping("/register")
    public ResponseEntity<AuthenticationResponse> register (@RequestBody RegistrationRequest request){
        return ResponseEntity.ok(authenticationService.register(request));
    }

    @PostMapping("/login")
    public ResponseEntity<?> login (@RequestBody AuthenticationRequest authenticationRequest) {
        try {
            return ResponseEntity.ok(authenticationService.authenticate(authenticationRequest));
        } catch (UserNotConfirmedException e) {
            return new ResponseEntity<>(new ErrorResponse("UserNotConfirmedException", e.getMessage()), HttpStatus.FORBIDDEN);
        }
    }

    @PostMapping("/logout/{id}")
    public void logout (HttpServletRequest httpRequest, @PathVariable Long id) {
        authenticationService.logout(httpRequest,id);
    }

    @GetMapping("/confirm")
    public ResponseEntity<?> confirmRegistration (@RequestParam Long id, @RequestParam String token) {
        if (authenticationService.confirmRegistration(id, token)) {
            return new ResponseEntity<>(new GenericResponse("Conferma avvenuta con successo!"), HttpStatus.OK);
        }
        return new ResponseEntity<>(new ErrorResponse("NotConfirmedException", "OPS! Qualcosa è andato storto con la conferma del tuo account!"), HttpStatus.BAD_REQUEST);
    }

}
