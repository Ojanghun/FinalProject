package com.smhrd.controller;

import com.smhrd.entity.Li_Info;
import com.smhrd.entity.Plan_Info;
import com.smhrd.repository.LiInfoRepository;
import com.smhrd.repository.PayInfoRepository;
import com.smhrd.repository.PlanInfoRepository;

import jakarta.servlet.http.HttpSession;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class PlanController {

    @Autowired
    private LiInfoRepository liInfoRepository;

    @Autowired
    private PlanInfoRepository planInfoRepository;

    @Autowired
    private PayInfoRepository payInfoRepository;

    @GetMapping("/plan")
    public String planPage(@RequestParam("liIdx") int liIdx, Model model, HttpSession session) {

        model.addAttribute("session", session.getAttribute("info"));

        Li_Info license = liInfoRepository.findById(liIdx).orElse(null);

        if (license != null) {
            String fullName = license.getLiName();
            if (license.getLiType() != null) {
                fullName += license.getLiType() ? " 실기" : " 필기";
            }

            model.addAttribute("liName", fullName);
            model.addAttribute("liIdx", liIdx);

            Plan_Info freePlan = planInfoRepository.findByLiIdxAndPlanType(liIdx, false);
            Plan_Info strictPlan = planInfoRepository.findByLiIdxAndPlanType(liIdx, true);

            model.addAttribute("freePlan", freePlan);
            model.addAttribute("strictPlan", strictPlan);

            // ✅ 탐구형과 필수형 각각의 활성 사용자 수 조회
            int freeUserCount = payInfoRepository.countActiveUsersForFreePlan(liIdx);
            int strictUserCount = payInfoRepository.countActiveUsersForStrictPlan(liIdx);

            model.addAttribute("freeUserCount", freeUserCount);
            model.addAttribute("strictUserCount", strictUserCount);
        }

        return "plan";
    }
    
    @GetMapping("/main/plan-usage")
    @ResponseBody
    public List<Map<String, Object>> getPlanUsageCount() {
        return planInfoRepository.findAllPlanUsageWithLicense();
    }
}
