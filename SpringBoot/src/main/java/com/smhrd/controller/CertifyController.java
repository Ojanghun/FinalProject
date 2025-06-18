package com.smhrd.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.smhrd.entity.Member;

import jakarta.servlet.http.HttpSession;

@Controller
public class CertifyController {

	@PostMapping("/certify")
	public String certify(@RequestParam("payIdx") Integer payIdx, @RequestParam("planIdx") Integer planIdx, @RequestParam("liName") String liName, HttpSession session, Model model) {
		Member member = (Member) session.getAttribute("info");
		model.addAttribute("member", member);
		model.addAttribute("payIdx", payIdx);
		model.addAttribute("planIdx", planIdx);
		model.addAttribute("liName", liName);
		
		return "certify";
	}

}
