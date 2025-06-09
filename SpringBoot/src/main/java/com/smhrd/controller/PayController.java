package com.smhrd.controller;

import com.smhrd.entity.Li_Info;
import com.smhrd.entity.Member;
import com.smhrd.entity.Pay_Info;
import com.smhrd.entity.Plan_Info;
import com.smhrd.repository.LiInfoRepository;
import com.smhrd.repository.PayInfoRepository;
import com.smhrd.repository.PlanInfoRepository;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@Controller
public class PayController {

    @Autowired
    private LiInfoRepository liInfoRepository;

    @Autowired
    private PlanInfoRepository planInfoRepository;

    @Autowired
    private PayInfoRepository payInfoRepository;

    @GetMapping("/pay")
    public String payPage(@RequestParam("liIdx") int liIdx,
                          @RequestParam("plan") String plan,
                          Model model,
                          HttpSession session) {

        model.addAttribute("session", session.getAttribute("info"));

        // 자격증 정보
        Li_Info license = liInfoRepository.findById(liIdx).orElse(null);
        if (license != null) {
            String fullName = license.getLiName();
            if (license.getLiType() != null) {
                fullName += license.getLiType() ? " 실기" : " 필기";
            }
            model.addAttribute("liName", fullName);
        }

        // 플랜 정보 및 사용자 수
        Plan_Info selectedPlan = null;
        int activeUserCount = 0;

        if ("탐구형".equals(plan)) {
            selectedPlan = planInfoRepository.findByLiIdxAndPlanType(liIdx, false);
            activeUserCount = payInfoRepository.countActiveUsersForFreePlan(liIdx);
        } else if ("필수형".equals(plan)) {
            selectedPlan = planInfoRepository.findByLiIdxAndPlanType(liIdx, true);
            activeUserCount = payInfoRepository.countActiveUsersForStrictPlan(liIdx);
        }

        if (selectedPlan != null) {
            model.addAttribute("planPrice", selectedPlan.getPlanPrice());
            model.addAttribute("planIdx", selectedPlan.getPlanIdx());
        }

        model.addAttribute("activeUserCount", activeUserCount);
        model.addAttribute("plan", plan); // 탐구형/필수형

        return "pay";
    }

    // 결제 완료 처리
    @PostMapping("/pay/submit")
    public String submitPayment(@RequestParam("planIdx") int planIdx,
                                 @RequestParam("liName") String liName,
                                 @RequestParam("plan") String plan,
                                 HttpSession session) {

        Object info = session.getAttribute("info");
        if (info == null) {
            return "redirect:/login";
        }

        // 세션에서 userId 추출
        String userId = ((Member) info).getId();

        LocalDateTime now = LocalDateTime.now();
        LocalDateTime end = now.plusDays(180); // 기본 30일 플랜

        Pay_Info payInfo = new Pay_Info();
        payInfo.setId(userId);
        payInfo.setPlanIdx(planIdx);
        payInfo.setPlanEd(end);
        payInfo.setPlanAct(1);

        payInfoRepository.save(payInfo);

        // ✅ 세션에 성공 메시지 저장 → main 페이지에서 보여줌
        session.setAttribute("paymentSuccess", "✅ 결제가 완료되었습니다!");

        return "redirect:/main";
    }
}
