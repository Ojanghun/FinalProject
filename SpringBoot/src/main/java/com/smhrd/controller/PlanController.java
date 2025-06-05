package com.smhrd.controller;

import com.smhrd.entity.Li_Info;
import com.smhrd.entity.Plan_Info;
import com.smhrd.repository.LiInfoRepository;
import com.smhrd.repository.PlanInfoRepository;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class PlanController {

    @Autowired
    private LiInfoRepository liInfoRepository;

    @Autowired
    private PlanInfoRepository planInfoRepository;

    @GetMapping("/plan")
    public String planPage(@RequestParam("liIdx") int liIdx, Model model, HttpSession session) {

        // 로그인 세션 정보 전달
        model.addAttribute("session", session.getAttribute("info"));

        // li_info 테이블에서 자격증 정보 조회
        Li_Info license = liInfoRepository.findById(liIdx).orElse(null);

        if (license != null) {
            // 자격증 이름 + 필기/실기 태그 조합
            String fullName = license.getLiName();
            if (license.getLiType() != null) {
                fullName += license.getLiType() ? " 실기" : " 필기";
            }

            model.addAttribute("liName", fullName);
            model.addAttribute("liIdx", liIdx);

            // 자유형 플랜 정보 (plan_type = 0)
            Plan_Info freePlan = planInfoRepository.findByLiIdxAndPlanType(liIdx, false);
            // 계획형 플랜 정보 (plan_type = 1)
            Plan_Info strictPlan = planInfoRepository.findByLiIdxAndPlanType(liIdx, true);

            model.addAttribute("freePlan", freePlan);
            model.addAttribute("strictPlan", strictPlan);
        }

        return "plan";
    }
}
