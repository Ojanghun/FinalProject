package com.smhrd.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class LicenseController {
	
	@RequestMapping("/goExam.do")
	public String goExam(@RequestParam("category") int category, Model model) {
		model.addAttribute("category", category);
		System.out.println("카테고리 값: "+category);
		return "pastPage";
	}
	
	@PostMapping("/goTest.do")
	public String goTest(@RequestParam("category") String category, Model model) {
		model.addAttribute("category", category);
		System.out.println("카테고리 값: "+category);
		return "testPage";	
	}
	
	@RequestMapping("/goTypeList.do")
	public String goTypeList(@RequestParam("category") int category, Model model) {
		model.addAttribute("category", category);
		System.out.println("카테고리 값: "+category);
		return "topicPage";	
	}
	
}
