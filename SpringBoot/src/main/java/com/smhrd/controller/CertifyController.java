package com.smhrd.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.smhrd.entity.Member;
import com.smhrd.service.CertifyService;

import jakarta.servlet.http.HttpSession;

@Controller
public class CertifyController {

	@Autowired
	private CertifyService certifyService;
	
	@PostMapping("/certify")
	public String certify(@RequestParam("payIdx") Integer payIdx, @RequestParam("planIdx") Integer planIdx, @RequestParam("liName") String liName, HttpSession session, Model model) {
		Member member = (Member) session.getAttribute("info");
		Long atd = certifyService.countAtd(member.getId());
		Long pbs = certifyService.countPbs(member.getId());
		
		model.addAttribute("member", member);
		model.addAttribute("payIdx", payIdx);
		model.addAttribute("planIdx", planIdx);
		model.addAttribute("liName", liName);
		model.addAttribute("atd", atd);
		model.addAttribute("pbs", pbs);
		
		return "certify";
	}

}
