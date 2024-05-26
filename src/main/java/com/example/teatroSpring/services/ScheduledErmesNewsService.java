package com.example.teatroSpring.services;

import com.example.teatroSpring.entities.ErmesNews;
import com.example.teatroSpring.entities.ScheduledErmesNews;
import com.example.teatroSpring.exceptions.ScheduledErmesNewsNotFoundException;
import com.example.teatroSpring.repositories.ScheduledErmesNewsRepository;
import com.example.teatroSpring.requests.ScheduledErmesNewsRequest;
import org.quartz.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Optional;

@Service
public class ScheduledErmesNewsService implements Job {

    @Autowired
    private ScheduledErmesNewsRepository scheduledErmesNewsRepository;
    @Autowired
    private Scheduler scheduler;
    @Autowired
    private ErmesNewsService ermesNewsService;

    public ScheduledErmesNews getScheduledNewsById (Long id) throws ScheduledErmesNewsNotFoundException {
       Optional<ScheduledErmesNews> scheduledErmesNewsOptional = scheduledErmesNewsRepository.findById(id);
       if (scheduledErmesNewsOptional.isEmpty()) throw new ScheduledErmesNewsNotFoundException();
       return scheduledErmesNewsOptional.get();
    }

    public ScheduledErmesNewsRequest createScheduledErmesNews (ScheduledErmesNewsRequest request) throws SchedulerException {
        ScheduledErmesNews scheduledErmesNews = ScheduledErmesNews
                .builder()
                .title(request.getTitle())
                .body(request.getBody())
                .publishTime(request.getTargetDate())
                .build();
        scheduledErmesNewsRepository.saveAndFlush(scheduledErmesNews);
        JobDetail jobDetail = buildJobDetail(scheduledErmesNews);
        Trigger trigger = buildJobTrigger(jobDetail, request.getTargetDate());
        scheduler.scheduleJob(jobDetail, trigger);
        return request;
    }

    private JobDetail buildJobDetail (ScheduledErmesNews scheduledErmesNews) {
        JobDataMap jobDataMap = new JobDataMap();
        jobDataMap.put("entity", scheduledErmesNews);
        jobDataMap.put("id", scheduledErmesNews.getId());
        return JobBuilder.newJob(ScheduledErmesNewsService.class)
                .withIdentity(String.valueOf(scheduledErmesNews.getId()),"ermesNews")
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
        ScheduledErmesNews scheduledErmesNews = (ScheduledErmesNews) jobDataMap.get("entity");
        try {
            deleteScheduledNewsById(scheduledErmesNews.getId());
        } catch (SchedulerException e) {
            throw new RuntimeException();
        }
        ErmesNews ermesNews = ErmesNews
                .builder()
                .title(scheduledErmesNews.getTitle())
                .body(scheduledErmesNews.getBody())
                .build();
        ermesNewsService.createErmesNews(ermesNews);
        System.out.println("News inserita con successo!");
    }

    public ScheduledErmesNewsRequest updateScheduledNews (Long id, ScheduledErmesNewsRequest request) throws SchedulerException {
        deleteScheduledNewsById(id);
        return createScheduledErmesNews(request);
    }

    public void deleteScheduledNewsById (Long id) throws SchedulerException {
        JobKey jobKey = new JobKey(String.valueOf(id), "ermesNews");
        scheduler.deleteJob(jobKey);
        scheduledErmesNewsRepository.deleteById(id);
    }

}
