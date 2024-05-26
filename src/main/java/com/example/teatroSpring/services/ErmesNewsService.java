package com.example.teatroSpring.services;

import com.example.teatroSpring.entities.ErmesNews;
import com.example.teatroSpring.exceptions.ErmesNewsNotFoundException;
import com.example.teatroSpring.repositories.ErmesNewsRepository;
import com.example.teatroSpring.requests.ScheduledErmesNewsRequest;
import org.quartz.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class ErmesNewsService  {

    @Autowired
    private ErmesNewsRepository ermesNewsRepository;
    @Autowired
    private Scheduler scheduler;

    public ErmesNews getErmesNewsById (Long id) throws ErmesNewsNotFoundException {
        Optional<ErmesNews> ermesNewsOptional = ermesNewsRepository.findById(id);
        if(ermesNewsOptional.isEmpty()) throw new ErmesNewsNotFoundException();
        return ermesNewsOptional.get();
    }

    public List<ErmesNews> getAllNews() { return ermesNewsRepository.findAll(); }

    public ErmesNews createErmesNews (ErmesNews ermesNews) {
        ermesNewsRepository.saveAndFlush(ermesNews);
        return ermesNews;
    }





}
