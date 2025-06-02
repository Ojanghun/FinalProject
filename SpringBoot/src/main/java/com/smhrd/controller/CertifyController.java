package com.smhrd.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class CertifyController {

	@GetMapping("/certify")
	public String certify() {
		return "certify";
	}
	
}
