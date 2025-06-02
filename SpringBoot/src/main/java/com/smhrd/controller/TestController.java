package com.smhrd.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import com.smhrd.service.TestService;

@Controller
public class TestController {

	@Autowired
	TestService service;
}
