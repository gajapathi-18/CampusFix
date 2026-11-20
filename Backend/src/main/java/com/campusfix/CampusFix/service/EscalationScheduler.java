package com.campusfix.CampusFix.service;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class EscalationScheduler {

    private final EscalationService escalationService;

    public EscalationScheduler(
            EscalationService escalationService) {
        this.escalationService = escalationService;
    }

    @Scheduled(fixedDelay = 60000)
    public void runEscalationCheck() {

        escalationService.checkAndEscalateComplaints();
    }
}
