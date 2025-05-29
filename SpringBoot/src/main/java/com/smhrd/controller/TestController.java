package com.smhrd.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class TestController {

	@GetMapping("/test")
	public String promote() {
		return "test";
	}
	
}
