package com.smhrd.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.smhrd.repository.PayInfoRepository;

@RestController
@RequestMapping("/api/plan")
public class PlanRestController {

    @Autowired
    private PayInfoRepository payInfoRepository;

    @GetMapping("/userCount")
    public Map<String, Integer> getUserCounts(@RequestParam("liIdx") int liIdx) {
        int free = payInfoRepository.countActiveUsersForFreePlan(liIdx);
        int strict = payInfoRepository.countActiveUsersForStrictPlan(liIdx);

        Map<String, Integer> map = new HashMap<>();
        map.put("free", free);
        map.put("strict", strict);
        return map;
    }
}
