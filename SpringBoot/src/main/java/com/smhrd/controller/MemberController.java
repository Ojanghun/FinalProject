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

	@GetMapping("/join")
	public String join() {
		return "join";
	}

	@PostMapping("/join")
	public String join(Member vo) {
		service.join(vo);
		return "redirect:/";
	}

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
	
	@GetMapping("/promote")
	public String promote() {
		return "promote";
	}
	
	@GetMapping("/license")
	public String license() {
		return "license";
	}
	
	@GetMapping("/exam")
	public String exam() {
		return "exam";
	}
	
	@GetMapping("/subject")
	public String subject() {
		return "subject";
	}
	
	@GetMapping("/certify")
	public String certify() {
		return "certify";
	}
	
}
