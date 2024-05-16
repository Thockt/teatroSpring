package com.example.teatroSpring.services;

import com.example.teatroSpring.entities.TokenBlackList;
import com.example.teatroSpring.repositories.TokenBlackListRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TokenBlackListService {

    @Autowired
    private TokenBlackListRepository tokenBlackListRepository;

    public List<String> tokenNotValidFromUtenteById (Long id_utente) {
        return tokenBlackListRepository.getTokenBlackListFromUtenteId(id_utente)
                .stream()
                .map(TokenBlackList::getToken)
                .toList();
    }

    public void createTokenBlackList (TokenBlackList tokenBlackList) {
        tokenBlackListRepository.saveAndFlush(tokenBlackList);
    }

    public Boolean isTokenPresent (String token){
        return tokenBlackListRepository.findAll().stream().map(TokenBlackList::getToken).toList().contains(token);
    }

}
