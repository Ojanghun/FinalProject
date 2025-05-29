package com.smhrd.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import com.smhrd.entity.Member;
import com.smhrd.service.MemberService;

import jakarta.servlet.http.HttpSession;

@Controller
public class MemberController {

	@Autowired
	private MemberService service;

	@GetMapping("/mypage")
	public String mypage() {
		return "mypage";
	}
	
	@GetMapping("/update")
	public String update() {
		return "update";
	}
	
	@PostMapping("/update")
	public String update(Member vo, HttpSession session) {
		service.update(vo);
		vo.setPw(null);
		session.setAttribute("info", vo);
		
		return "redirect:/";
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
