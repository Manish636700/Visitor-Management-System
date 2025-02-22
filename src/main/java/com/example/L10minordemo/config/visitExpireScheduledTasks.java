package com.example.L10minordemo.config;


import com.example.L10minordemo.entity.Visit;
import com.example.L10minordemo.enums.VisitStatus;
import com.example.L10minordemo.repo.VisitRepo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.Scheduled;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

@Configuration
public class visitExpireScheduledTasks {



    private static Logger LOGGER = LoggerFactory.getLogger(visitExpireScheduledTasks.class);

    @Autowired
    private VisitRepo visitRepo;

    //  @Scheduled(fixedDelay = 3000)
    public void markVisitExpire(){
        LOGGER.info("Marking visits as Expire");
        Date date = new Date();
        List<Visit> visitList = visitRepo.findByStatusAndCreatedDateLessThanEqual(VisitStatus.WAITING,date);
        for (Visit visit : visitList){
            visit.setStatus(VisitStatus.EXPIRE);
        }
        visitRepo.saveAll(visitList);
    }
}
