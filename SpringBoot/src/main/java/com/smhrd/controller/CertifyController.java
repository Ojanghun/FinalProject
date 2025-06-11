package com.smhrd.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.smhrd.entity.Member;

import jakarta.servlet.http.HttpSession;

@Controller
public class CertifyController {

	@GetMapping("/certify")
	public String certify(@RequestParam("liName") String liName, HttpSession session, Model model) {
		Member member = (Member) session.getAttribute("info");
		model.addAttribute("member", member);
		model.addAttribute("liName", liName);
		
		// 전에 쓰던 Model 객체의 데이터를 다른 페이지로 넘길 순 없나?
		// URL 파라미터로 넘기거나 Session에 담거나 아예 DB에서부터 조회
		
		return "certify";
	}

}
