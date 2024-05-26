package com.example.teatroSpring.controllers;

import com.example.teatroSpring.requests.ScheduledErmesNewsRequest;
import com.example.teatroSpring.services.ScheduledErmesNewsService;
import org.quartz.SchedulerException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/scheduled_news")
public class ScheduledErmesNewsController {

    @Autowired
    private ScheduledErmesNewsService scheduledErmesNewsService;

    @PostMapping("/create")
    public ResponseEntity<ScheduledErmesNewsRequest> createScheduledNews (@RequestBody ScheduledErmesNewsRequest request) throws SchedulerException {
        return new ResponseEntity<>(scheduledErmesNewsService.createScheduledErmesNews(request), HttpStatus.CREATED);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<ScheduledErmesNewsRequest> updateScheduledNews (@PathVariable Long id, @RequestBody ScheduledErmesNewsRequest request) throws SchedulerException {
        return new ResponseEntity<>(scheduledErmesNewsService.updateScheduledNews(id, request), HttpStatus.CREATED);
    }

    @DeleteMapping("/delete/{id}")
    public void deleteScheduledNewsById (@PathVariable Long id) throws SchedulerException {
        scheduledErmesNewsService.deleteScheduledNewsById(id);
    }

}
