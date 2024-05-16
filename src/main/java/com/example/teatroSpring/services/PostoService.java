package com.example.teatroSpring.services;

import com.example.teatroSpring.entities.Posto;
import com.example.teatroSpring.repositories.PostoRepository;
import com.example.teatroSpring.requests.PostoRequest;
import com.example.teatroSpring.responses.PostoResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PostoService {

    @Autowired
    private PostoRepository postoRepository;
    @Autowired
    private SalaService salaService;

    public Posto getPostoById (Long id) {
        Optional<Posto> postoOptional = postoRepository.findById(id);
        if (postoOptional.isEmpty()) throw new IllegalArgumentException("Il posto con id "+id+" non esiste!");
        return postoOptional.get();
    }

    public List<Posto> getAll () { return postoRepository.findAll(); }

    public PostoResponse createPosto (PostoRequest posto) {
        postoRepository.saveAndFlush(convertFromDTO(posto));
        return convertFromEntity(convertFromDTO(posto));
    }

    public PostoResponse updatePosto (Long id, Posto newPosto) {
        Optional<Posto> postoOptional = postoRepository.findById(id);
        if (postoOptional.isEmpty()) throw new IllegalArgumentException("Il posto con id "+id+" non esiste!");
        Posto posto = Posto.builder()
                .id(id)
                .fila(newPosto.getFila())
                .numero(newPosto.getNumero())
                .sala(newPosto.getSala())
                .build();
        postoRepository.saveAndFlush(posto);
        return convertFromEntity(posto);
    }

    public void deletePostoById (Long id) { postoRepository.deleteById(id); }

    private Posto convertFromDTO (PostoRequest postoRequest) {
        return Posto.builder()
                .fila(postoRequest.getFila())
                .numero(postoRequest.getNumero())
                .sala(salaService.getSalaById(postoRequest.getSala()))
                .build();
    }

    private PostoResponse convertFromEntity (Posto posto) {
        return PostoResponse.builder()
                .fila(posto.getFila())
                .numero(posto.getNumero())
                .sala(posto.getSala().getId())
                .build();
    }

}
