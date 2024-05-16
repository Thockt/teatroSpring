package com.example.teatroSpring.controllers;

import com.example.teatroSpring.entities.ErmesNews;
import com.example.teatroSpring.exceptions.ErmesNewsNotFoundException;
import com.example.teatroSpring.requests.ScheduledErmesNewsRequest;
import com.example.teatroSpring.services.ErmesNewsService;
import org.quartz.SchedulerException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/news")
public class ErmesNewsController {

    @Autowired
    private ErmesNewsService ermesNewsService;

    @GetMapping("/get/{id}")
    public ResponseEntity<ErmesNews> getErmesNewsById (@RequestParam Long id) throws ErmesNewsNotFoundException {
        ErmesNews ermesNews = ermesNewsService.getErmesNewsById(id);
        return new ResponseEntity<>(ermesNews, HttpStatus.OK);
    }

    @GetMapping("/all")
    public ResponseEntity<List<ErmesNews>> getAllErmesNews () {
        return new ResponseEntity<>(ermesNewsService.getAllNews(), HttpStatus.OK);
    }

    @PostMapping("/create")
    @Secured("SUPERADMIN, ADMIN")
    public ResponseEntity<ErmesNews> createNews (@RequestBody ErmesNews ermesNews) {
        return new ResponseEntity<>(ermesNewsService.createErmesNews(ermesNews), HttpStatus.CREATED);
    }

    @PostMapping("/schedule")
    @Secured("SUPERADMIN, ADMIN")
    public ResponseEntity<ScheduledErmesNewsRequest> createScheduledNews (@RequestBody ScheduledErmesNewsRequest request) throws SchedulerException {
        return new ResponseEntity<>(ermesNewsService.createScheduledErmesNews(request), HttpStatus.CREATED);
    }

}
