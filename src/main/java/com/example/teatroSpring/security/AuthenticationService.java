package com.example.teatroSpring.security;

import com.example.teatroSpring.entities.TokenBlackList;
import com.example.teatroSpring.entities.Utente;
import com.example.teatroSpring.enums.Role;
import com.example.teatroSpring.exceptions.ComuneNotFoundException;
import com.example.teatroSpring.exceptions.UserNotConfirmedException;
import com.example.teatroSpring.repositories.ComuneRepository;
import com.example.teatroSpring.repositories.UtenteRepository;
import com.example.teatroSpring.requests.AuthenticationRequest;
import com.example.teatroSpring.requests.RegistrationRequest;
import com.example.teatroSpring.responses.AuthenticationResponse;
import com.example.teatroSpring.services.ComuneService;
import com.example.teatroSpring.services.TokenBlackListService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

@Service
public class AuthenticationService {

    @Autowired
    private JwtService jwtService;
    @Autowired
    private UtenteRepository utenteRepository;
    @Autowired
    private ComuneService comuneService;
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private TokenBlackListService tokenBlackListService;
    @Autowired
    private JavaMailSender javaMailSender;

    public AuthenticationResponse register (RegistrationRequest request) throws ComuneNotFoundException {
        var user = Utente.builder()
                .nome(request.getNome())
                .cognome(request.getCognome())
                .indirizzo(request.getIndirizzo())
                .email(request.getEmail())
                .telefono(request.getTelefono())
                .dataNascita(request.getDataNascita())
                .città(comuneService.getComuneById(request.getCittà()))
                .role(Role.TOCONFIRM)
                .password(passwordEncoder.encode(request.getPassword()))
                .build();
        var jwtToken = jwtService.generateToken(user);
        user.setRegistratioToken(jwtToken);
        utenteRepository.saveAndFlush(user);
        javaMailSender.send(createConfirmationMail(user));
        return AuthenticationResponse.builder().token(jwtToken).build();
    }

    public AuthenticationResponse authenticate (AuthenticationRequest authenticationRequest) throws UserNotConfirmedException {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                authenticationRequest.getEmail(),
                authenticationRequest.getPassword()
        ));
        var user = utenteRepository.findUtenteByEmail(authenticationRequest.getEmail());
        if (user.getRole().equals(Role.TOCONFIRM)) {
            throw new UserNotConfirmedException();
        }
        var jwtToken = jwtService.generateToken(user);
        if(tokenBlackListService.tokenNotValidFromUtenteById(user.getId()).contains(jwtToken)) {
            String email = jwtService.extractUsername(jwtToken);
            UserDetails userDetails = utenteRepository.findUtenteByEmail(email);

            String newToken = jwtService.generateToken(userDetails);
            return AuthenticationResponse.builder().token(newToken).build();
        }
        return AuthenticationResponse.builder().token(jwtToken).build();
    }

    public void logout (HttpServletRequest httpRequest, Long id) {
        String token = extractTokenFromRequest(httpRequest);
        TokenBlackList tokenBlackList = TokenBlackList.builder()
                .utente(utenteRepository.getReferenceById(id))
                .token(token)
                .build();
        tokenBlackListService.createTokenBlackList(tokenBlackList);
    }

    public String extractTokenFromRequest (HttpServletRequest request) {
        String authorizationHeader = request.getHeader("Authorization");
        if(StringUtils.hasText(authorizationHeader) && authorizationHeader.startsWith("Bearer ")) {
            return authorizationHeader.substring(7);
        }
        int x=0;
        return null;
    }

    public boolean confirmRegistration (Long id, String token) {
        Utente utente = utenteRepository.getReferenceById(id);
        if (utente.getRegistratioToken().equals(token)) {
            utente.setRole(Role.USER);
            utenteRepository.saveAndFlush(utente);
            return true;
        }
        return false;
    }

    public SimpleMailMessage createConfirmationMail (Utente utente) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(utente.getEmail());
        message.setSubject("CONFERMA REGISTRAZIONE");
        String url = "http://localhost:8080/auth/confirm?id=" +utente.getId()+ "&token=" +utente.getRegistratioToken();
        message.setText("Clicca qui per confermare la registrazione: " +url);
        return message;
    }

}
