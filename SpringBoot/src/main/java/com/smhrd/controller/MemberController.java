package com.smhrd.controller;

import com.smhrd.entity.Member;
import com.smhrd.projection.PayWithLicenseDTO;
import com.smhrd.projection.PayWithLicenseDTOImpl;
import com.smhrd.repository.PayInfoRepository;
import com.smhrd.service.MemberService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class MemberController {

    @Autowired
    private MemberService memberService;

    @Autowired
    private PayInfoRepository payInfoRepository;

    @GetMapping("/mypage")
    public String mypage(HttpSession session, Model model) {
        Member member = (Member) session.getAttribute("info");
        if (member == null) {
            return "redirect:/login";
        }

        // 1. 사용자 정보 모델에 전달
        model.addAttribute("member", member);

        // 2. 자격증 이름(liName) 포함된 결제 DTO 조회
        List<PayWithLicenseDTOImpl> payList = payInfoRepository.findDetailedPaymentsByUserId(member.getId());
        model.addAttribute("payList", payList);


        return "mypage";
    }

    @GetMapping("/update")
    public String update() {
        return "update";
    }

    @GetMapping("/list")
    public String list(HttpSession session) {
        Member info = (Member) session.getAttribute("info");
        if (info != null && "admin".equals(info.getId())) {
            return "list";
        } else {
            return "redirect:/";
        }
    }
}
