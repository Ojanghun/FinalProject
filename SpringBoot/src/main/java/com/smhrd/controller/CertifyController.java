package com.smhrd.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import com.smhrd.entity.Pay_Info;

@Controller
public class CertifyController {

	@GetMapping("/certify")
	public String certify() {
			return "certify";
	}
	
}
