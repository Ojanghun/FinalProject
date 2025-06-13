package com.smhrd.controller;

import com.smhrd.entity.Li_Info;
import com.smhrd.entity.Member;
import com.smhrd.entity.Pay_Info;
import com.smhrd.projection.PayWithLicenseDTO;
import com.smhrd.projection.PayWithLicenseDTOImpl;
import com.smhrd.repository.LiInfoRepository;
import com.smhrd.repository.PayInfoRepository;

import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public class MainController {

    @Autowired
    private PayInfoRepository payInfoRepository;

    @Autowired
    private LiInfoRepository liInfoRepository;

    @GetMapping("/redirectByLicense")
    public String redirectByLicense(@RequestParam("liIdx") int liIdx, HttpSession session) {
        Member loginUser = (Member) session.getAttribute("info");

        if (loginUser == null) {
            return "redirect:/login";
        }

        List<Pay_Info> payList = payInfoRepository.findByUserIdAndLiIdx(loginUser.getId(), liIdx);

        if (payList != null && !payList.isEmpty()) {
            return "redirect:/license?liIdx=" + liIdx;
        } else {
            return "redirect:/plan?liIdx=" + liIdx;
        }
    }

    @RequestMapping("/")
    public String mainRedirect() {
        return "redirect:/main";
    }

    @GetMapping("/main")
    public String mainPage(@RequestParam(value = "logout", required = false) String logout,
                           Model model,
                           HttpSession session) {

        if (logout != null) {
            model.addAttribute("logoutMessage", "성공적으로 로그아웃되었습니다.");
        }

        // ✅ 결제 완료 메시지가 세션에 있으면 모델로 전달하고 세션에서는 삭제
        String paymentSuccess = (String) session.getAttribute("paymentSuccess");
        if (paymentSuccess != null) {
            model.addAttribute("paymentSuccess", paymentSuccess);
            session.removeAttribute("paymentSuccess");
        }

        // 자격증 리스트
        List<Li_Info> licenseList = liInfoRepository.findAll();
        model.addAttribute("licenseList", licenseList);

        // 로그인한 사용자 정보
        Member loginUser = (Member) session.getAttribute("info");
        model.addAttribute("session", loginUser);

        // ✅ 사용자 결제 리스트 payList 모델에 전달
        if (loginUser != null) {
        	List<PayWithLicenseDTOImpl> payList = payInfoRepository.findDetailedPaymentsByUserId(loginUser.getId());
        	model.addAttribute("payList", payList);
        }

        return "main";
    }

    @GetMapping("/promote")
    public String promote() { 
        return "promote";
    }
}
