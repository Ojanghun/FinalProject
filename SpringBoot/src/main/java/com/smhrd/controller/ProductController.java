package com.smhrd.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ProductController {

	@GetMapping("/license")
	public String license() {
		return "license";
	}
	
	@GetMapping("/topic")
	public String subject() {
		return "topic";
	}
	
}
