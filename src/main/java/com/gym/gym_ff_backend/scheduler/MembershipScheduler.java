package com.gym.gym_ff_backend.scheduler;

import com.gym.gym_ff_backend.service.MembershipService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class MembershipScheduler {

    private static final Logger logger =
            LoggerFactory.getLogger(MembershipScheduler.class);

    private final MembershipService membershipService;

    public MembershipScheduler(MembershipService membershipService) {
        this.membershipService = membershipService;
    }
//    every 00:00 it gets restarted
//    0 seconds
//    0 minutes
//    0 hours
//    every day
//    every month
//    every day of week
    @Scheduled(cron = "0 0 0 * * *")
//    every 30 minutes it restarts
//    @Scheduled(fixedRate = 30000)
    public void updateMembershipStatuses() {

        logger.info("Running membership status scheduler");

        membershipService.updateMembershipStatuses();
    }
}