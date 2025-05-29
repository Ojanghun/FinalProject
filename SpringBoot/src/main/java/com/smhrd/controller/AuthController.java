package com.smhrd.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import com.smhrd.entity.Member;
import com.smhrd.service.MemberService;

import jakarta.servlet.http.HttpSession;

@Controller
public class AuthController {

	@Autowired
	private MemberService service;
	
	@GetMapping("/login")
	public String login() {
		return "login";
	}

	@PostMapping("/login")
	public String login(Member vo, HttpSession session) {
		Member info = service.login(vo);

		if (info != null) {
			session.setAttribute("info", info);
		}

			return "redirect:/";
	}
	
	@GetMapping("/logout")
	public String logout(HttpSession session) {
		session.invalidate();
		return "redirect:/";
	}
	
	@GetMapping("/join")
	public String join() {
		return "join";
	}

	@PostMapping("/join")
	public String join(Member vo) {
		service.join(vo);
		return "redirect:/";
	}
	
}
