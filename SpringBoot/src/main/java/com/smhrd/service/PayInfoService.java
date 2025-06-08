package com.smhrd.service;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.smhrd.repository.PayInfoRepository;

@Service
public class PayInfoService {
    private final PayInfoRepository payInfoRepository;

    public PayInfoService(PayInfoRepository payInfoRepository) {
        this.payInfoRepository = payInfoRepository;
    }

    // 매일 자정에 실행 (cron = "0 0 0 * * *")
    @Scheduled(cron = "0 0 0 * * *")
    @Transactional
    public void deactivateExpiredPlans() {
        payInfoRepository.deactivateExpiredPlans();
    }
    
    @Scheduled(cron = "0 0 0 * * *")
    @Transactional
    public void updateRefundStatus() {
        payInfoRepository.updateRefundStatus();
    }
}
