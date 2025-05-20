package com.smhrd.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.smhrd.entity.Member;
import com.smhrd.service.MemberService;

@RestController
public class MemberRestController {
	
	@Autowired
	private MemberService service;
	
	@GetMapping("/idCheck")
	public Member idCheck(@RequestParam("id") String id) {
		
		Member m = service.idCheck(id);
		
		return m;
	}
	
	@PostMapping("/goList")
	public List<Member> goList() {
		
		List<Member> list = service.goList();
		
		return list;
	}
	
	@PostMapping("/goDelete")
	public void goDelete(@RequestParam("id") String id) {
		service.goDelete(id);
	}
	
}
