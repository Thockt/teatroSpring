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
public class ErmesNewsService implements Job {

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

    public ScheduledErmesNewsRequest createScheduledErmesNews (ScheduledErmesNewsRequest request) throws SchedulerException {
        ErmesNews ermesNews = ErmesNews.builder().title(request.getTitle()).body(request.getBody()).build();
        JobDetail jobDetail = buildJobDetail(ermesNews);
        Trigger trigger = buildJobTrigger(jobDetail, request.getTargetDate());
        scheduler.scheduleJob(jobDetail, trigger);
        return request;
    }

    private JobDetail buildJobDetail (ErmesNews ermesNews) {
        JobDataMap jobDataMap = new JobDataMap();
        jobDataMap.put("entity", ermesNews);
        return JobBuilder.newJob(ErmesNewsService.class)
                .withIdentity("ermesNews")
                .storeDurably()
                .setJobData(jobDataMap)
                .build();
    }

    private Trigger buildJobTrigger (JobDetail jobDetail, Date targetDate) {
        return TriggerBuilder.newTrigger()
                .forJob(jobDetail)
                .startAt(targetDate)
                .withSchedule(SimpleScheduleBuilder.simpleSchedule())
                .build();
    }

    @Override
    public void execute (JobExecutionContext jobExecutionContext) throws JobExecutionException {
        JobDataMap jobDataMap = jobExecutionContext.getMergedJobDataMap();
        ErmesNews ermesNews = (ErmesNews) jobDataMap.get("entity");
        createErmesNews(ermesNews);
        System.out.println("News inserita con successo!");
    }

}
