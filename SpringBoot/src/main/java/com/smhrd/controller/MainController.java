package com.smhrd.controller;

import com.smhrd.entity.Li_Info;
import com.smhrd.entity.Member;
import com.smhrd.entity.Pay_Info;
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

	@GetMapping("/redirectByLicense")
	public String redirectByLicense(@RequestParam("liIdx") int liIdx, HttpSession session) {
	    Member loginUser = (Member) session.getAttribute("info");

	    // 로그인 X → 로그인 페이지로
	    if (loginUser == null) {
	        return "redirect:/login";
	    }

	    // 로그인 O → 유저+liIdx 기준 결제 내역 확인
	    List<Pay_Info> payList = payInfoRepository.findByUserIdAndLiIdx(loginUser.getId(), liIdx);

	    if (payList != null && !payList.isEmpty()) {
	        return "redirect:/license?liIdx=" + liIdx;
	    } else {
	        return "redirect:/plan?liIdx=" + liIdx;
	    }
	}

    @Autowired
    private LiInfoRepository liInfoRepository;

    
    @RequestMapping("/")
    public String mainRedirect() {
        return "redirect:/main"; // "/" 접근 시 /main 으로 리디렉션
    }

    @GetMapping("/main")
    public String mainPage(@RequestParam(value = "logout", required = false) String logout,
                           Model model,
                           HttpSession session) {

        // 로그아웃 메시지 처리
        if (logout != null) {
            model.addAttribute("logoutMessage", "성공적으로 로그아웃되었습니다.");
        }

        // ✅ 자격증 전체 목록 가져와서 모델에 전달
        List<Li_Info> licenseList = liInfoRepository.findAll();
        model.addAttribute("licenseList", licenseList);

        // ✅ 로그인 세션 정보도 모델에 전달 (login 상태 표시용)
        model.addAttribute("session", session.getAttribute("info"));

        return "main";
    }

    @GetMapping("/promote")
    public String promote() {
        return "promote";
    }
}
