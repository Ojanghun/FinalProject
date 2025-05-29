package com.smhrd.controller; // 실제 패키지 경로로 수정

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.smhrd.entity.Member;
import com.smhrd.service.MemberService;

import jakarta.servlet.http.HttpSession;

@Controller
public class MemberController {

	@Autowired
    private MemberService memberService;

	@GetMapping("/mypage")
    public String mypage(HttpSession session, Model model) {
        Member member = (Member) session.getAttribute("info");
        if (member != null) {
            model.addAttribute("member", member);
        }
        return "mypage"; // mypage.html or mypage.jsp
    }
	
	@GetMapping("/update")
	public String update() {
		return "update";
	}
	
	@GetMapping("/list")
	public String list(HttpSession session) {
		
		Member info = (Member) session.getAttribute("info");
		
		if(info != null && info.getId().equals("admin")) {
			return "list";
		} else {
			return "redirect:/";
		}
		
	}
	
}