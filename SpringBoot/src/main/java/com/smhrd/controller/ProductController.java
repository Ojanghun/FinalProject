package com.smhrd.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.smhrd.entity.Member;

import jakarta.servlet.http.HttpSession;

@Controller
public class ProductController {

	@GetMapping("/license")
	public String license(HttpSession session, Model model) {
		Member member = (Member) session.getAttribute("info");
		if (member != null) {
			model.addAttribute("member", member);
		}
		return "license";
	}
	
	@GetMapping("/topic")
	public String subject() {
		return "topic";
	}
	
}
