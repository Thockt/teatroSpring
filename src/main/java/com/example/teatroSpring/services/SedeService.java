package com.example.teatroSpring.services;

import com.example.teatroSpring.entities.Comune;
import com.example.teatroSpring.entities.Sede;
import com.example.teatroSpring.entities.Utente;
import com.example.teatroSpring.exceptions.ComuneNotFoundException;
import com.example.teatroSpring.exceptions.SedeNotFoundException;
import com.example.teatroSpring.repositories.SedeRepository;
import com.example.teatroSpring.repositories.UtenteRepository;
import com.example.teatroSpring.requests.ScheduledErmesNewsRequest;
import com.example.teatroSpring.requests.SedeNuovaApertaRequest;
import com.example.teatroSpring.requests.SedeRequest;
import com.example.teatroSpring.responses.SedeResponse;
import org.quartz.SchedulerException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class SedeService {

    @Autowired
    private SedeRepository sedeRepository;
    @Autowired
    private UtenteRepository utenteRepository;
    @Autowired
    private ComuneService comuneService;
    @Autowired
    private JavaMailSender javaMailSender;
    @Autowired
    private ScheduledErmesNewsService scheduledErmesNewsService;


    public Sede getSedeById (Long id) throws SedeNotFoundException {
        Optional<Sede> sedeOptional = sedeRepository.findById(id);
        if (sedeOptional.isEmpty()) throw new SedeNotFoundException();
        return sedeOptional.get();
    }

    public List<Sede> getAll() { return sedeRepository.findAll(); }

    public SedeResponse createSede (SedeRequest sede) throws ComuneNotFoundException{
        sedeRepository.saveAndFlush(convertFromDTO(sede));
        return convertFromEntity(convertFromDTO(sede));
    }

    public SedeResponse createSedeAndNews (SedeNuovaApertaRequest sedeNuovaRequest) throws ComuneNotFoundException, SchedulerException {
        SedeRequest sedeRequest = new SedeRequest(sedeNuovaRequest.getNome(), sedeNuovaRequest.getIndirizzo(), sedeNuovaRequest.getIsOpen(), sedeNuovaRequest.getComune());
        sedeRepository.saveAndFlush(convertFromDTO(sedeRequest));
        ScheduledErmesNewsRequest request = new ScheduledErmesNewsRequest();
        request.setTitle("Nuova sede aperta a " +convertFromDTO(sedeRequest).getComune().getNome()+"!");
        request.setBody("Una nuova sede è stata aperta nel comune di "
                +convertFromDTO(sedeRequest).getComune().getNome()+".\n"
                +"Vi aspettiamo con a " +convertFromDTO(sedeRequest).getIndirizzo()+ " presso la sede "
                +convertFromDTO(sedeRequest).getNome()+"!"
                );
        request.setTargetDate(sedeNuovaRequest.getTargetDate());
        scheduledErmesNewsService.createScheduledErmesNews(request);
        List<Utente> utenti = utenteRepository.getAllByCittà(sedeRequest.getComune());
        if(!utenti.isEmpty()){
            for (Utente u: utenti){
                javaMailSender.send(createNewSedeMail(u, convertFromDTO(sedeRequest)));
            }
        }
        return convertFromEntity(convertFromDTO(sedeRequest));
    }

    public SedeResponse updateSede (Long id, Sede newSede) {
        Optional<Sede> sedeOptional = sedeRepository.findById(id);
        if (sedeOptional.isEmpty()) throw new IllegalArgumentException("La sede con id "+id+" non esiste!");
        Sede sede = Sede.builder()
                .id(id)
                .nome(newSede.getNome())
                .indirizzo(newSede.getIndirizzo())
                .isOpen(newSede.getIsOpen())
                .comune(newSede.getComune())
                .build();
        sedeRepository.saveAndFlush(sede);
        return convertFromEntity(sede);
    }

    public void deleteSedeById (Long id) { sedeRepository.deleteById(id); }

    public Comune accediAlComune (Long id_comune) throws ComuneNotFoundException {
        return comuneService.getComuneById(id_comune);
    }

    public SimpleMailMessage createNewSedeMail (Utente u, Sede s) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(u.getEmail());
        message.setSubject("NUOVA SEDE APERTA");
        message.setText("Salve "+u.getNome()+"!\n"
        +"Nella città da te specificata è stata aperta una nuova sede a "
        +s.getIndirizzo()+ " . Ti aspettiamo a "+s.getNome()+"!");
        return message;
    }



    private Sede convertFromDTO (SedeRequest sedeRequest) throws ComuneNotFoundException{
        return Sede.builder()
                .nome(sedeRequest.getNome())
                .indirizzo(sedeRequest.getIndirizzo())
                .isOpen(sedeRequest.getIsOpen())
                .comune(comuneService.getComuneById(sedeRequest.getComune()))
                .build();
    }

    private SedeResponse convertFromEntity (Sede sede) {
        return SedeResponse.builder()
                .nome(sede.getNome())
                .indirizzo(sede.getIndirizzo())
                .isOpen(sede.getIsOpen())
                .comune(sede.getComune().getId())
                .build();
    }

}
