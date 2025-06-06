package com.smhrd.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.smhrd.service.LicenseService;

@Controller
public class LicenseController {
	
	@Autowired
	LicenseService service;
	
	@RequestMapping("/goExam.do")
	public String goExam(@RequestParam("category") int category, Model model) {
		model.addAttribute("category", category);
		System.out.println("카테고리 값: "+category);
		return "past";
	}
	
	@PostMapping("/goTest.do")
	public String goTest(@RequestParam("category") String category, Model model) {
		model.addAttribute("category", category);
		System.out.println("카테고리 값: "+category);
		return "test";	
	}
	
	@RequestMapping("/goTypeList.do")
	public String goTypeList(@RequestParam("category") int category, Model model) {
		model.addAttribute("category", category);
		System.out.println("카테고리 값: "+category);
		return "topic";	
	}
	
	@PostMapping("/atd_check")
	@ResponseBody
	public boolean atd_check(@RequestParam("id") String id) {
	    return service.atd_check(id);
	}
	
	
}
