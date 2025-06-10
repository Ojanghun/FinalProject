package com.smhrd.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.smhrd.projection.LiNameOnly;
import com.smhrd.repository.PayInfoRepository;
import com.smhrd.service.SearchService;


@RestController
@RequestMapping("/api/plan")
public class PlanRestController {

    @Autowired
    private PayInfoRepository payInfoRepository;

    @GetMapping("/userCount")
    public Map<String, Integer> getUserCounts(@RequestParam("liIdx") int liIdx) {
        int free = payInfoRepository.countActiveUsersForFreePlan(liIdx);
        int strict = payInfoRepository.countActiveUsersForStrictPlan(liIdx);

        Map<String, Integer> map = new HashMap<String, Integer>();
        map.put("free", free);
        map.put("strict", strict);
        return map;
    }
}

